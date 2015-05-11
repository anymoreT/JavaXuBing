package com.hcwins.vehicle.ta.acp.sampler.sampler;

import org.apache.commons.lang3.StringUtils;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.Interruptible;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.ThreadListener;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.*;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ACPSampler extends AbstractSampler implements ThreadListener, Interruptible {
    private static final Logger logger = LoggingManager.getLoggerForClass();

    private static final Set<String> APPLIABLE_CONFIG_CLASSES = new HashSet<String>(
            Arrays.asList(new String[]{
                    "com.hcwins.vehicle.ta.acp.sampler.config.gui.ACPConfigGui",
                    "org.apache.jmeter.config.gui.SimpleConfigGui"}));

    public static final String SERVER = "ACPSampler.server";
    public static final String PORT = "ACPSampler.port";
    public static final String TIMEOUT_CONNECT = "ACPSampler.ctimeout";
    public static final String TIMEOUT = "ACPSampler.timeout";

    public static final String RE_USE_CONNECTION = "ACPSampler.reUseConnection";
    public static final String CLOSE_CONNECTION = "ACPSampler.closeConnection";
    public static final String NODELAY = "ACPSampler.nodelay";
    public static final String SO_LINGER = "ACPSampler.soLinger";

    public static final boolean RE_USE_CONNECTION_DEFAULT = true;
    public static final boolean CLOSE_CONNECTION_DEFAULT = false;
    public static final boolean NODELAY_DEFAULT = true;

    public static final String CLASSNAME = "ACPSampler.classname";

    public static final String REQUEST = "ACPSampler.request";

    private static final String protoPrefix = "com.hcwins.vehicle.ta.acp.sampler.sampler.";

    private static final String ACPKEY = "ACP";
    private static final String ERRKEY = "ERR";
    // KEY = ACPKEY or ERRKEY, Entry= Socket or String
    private static final ThreadLocal<Map<String, Object>> tp =
            new ThreadLocal<Map<String, Object>>() {
                @Override
                protected Map<String, Object> initialValue() {
                    return new HashMap<String, Object>();
                }
            };

    private transient boolean firstSample;
    private transient ACPClient acpClient;
    private transient volatile Socket currentSocket;

    public ACPSampler() {
        if (logger.isDebugEnabled()) {
            logger.debug(this + " Created");
        }
    }

    public void setServer(String newServer) {
        this.setProperty(SERVER, newServer);
    }

    public String getServer() {
        return getPropertyAsString(SERVER);
    }

    public void setPort(String newPort) {
        this.setProperty(PORT, newPort);
    }

    public int getPort() {
        return getPropertyAsInt(PORT);
    }

    public void setConnectTimeout(String newTimeout) {
        this.setProperty(TIMEOUT_CONNECT, newTimeout, "");
    }

    public int getConnectTimeout() {
        return getPropertyAsInt(TIMEOUT_CONNECT, 0);
    }

    public void setTimeout(String newTimeout) {
        this.setProperty(TIMEOUT, newTimeout, "");
    }

    public int getTimeout() {
        return getPropertyAsInt(TIMEOUT, 0);
    }

    public boolean isReUseConnection() {
        return getPropertyAsBoolean(RE_USE_CONNECTION, RE_USE_CONNECTION_DEFAULT);
    }

    public boolean isCloseConnection() {
        return getPropertyAsBoolean(CLOSE_CONNECTION, CLOSE_CONNECTION_DEFAULT);
    }

    public boolean isNoDelay() {
        return getPropertyAsBoolean(NODELAY, NODELAY_DEFAULT);
    }

    public void setSoLinger(String soLinger) {
        this.setProperty(SO_LINGER, soLinger, "");
    }

    public int getSoLinger() {
        return getPropertyAsInt(SO_LINGER, 0);
    }

    public void setClassname(String classname) {
        this.setProperty(CLASSNAME, classname, "");
    }

    public String getClassname() {
        return getPropertyAsString(CLASSNAME, "ACPClientGPS");
    }

    public void setRequestData(String newRequestData) {
        this.setProperty(REQUEST, newRequestData);
    }

    public String getRequestData() {
        return getPropertyAsString(REQUEST);
    }

    @Override
    public SampleResult sample(Entry e) {
        if (firstSample) {
            initSampling();
            firstSample = false;
        }

        SampleResult res = new SampleResult();
        boolean isSuccessful = false;

        final String socketKey = getSocketKey();
        final boolean reUseConnection = isReUseConnection();
        final boolean closeConnection = isCloseConnection();

        res.setSampleLabel(getName());

        StringBuilder sb = new StringBuilder();
        sb.append("Server: ").append(getServer());
        sb.append(" Port: ").append(getPort());
        sb.append("\n");
        sb.append("Connect Timeout: ").append(getConnectTimeout());
        sb.append(" Timeout: ").append(getTimeout());
        sb.append("\n");
        sb.append("Reuse: ").append(reUseConnection);
        sb.append(" Close: ").append(closeConnection);
        sb.append("\n");
        sb.append("NoDelay: ").append(isNoDelay());
        sb.append(" SoLinger: ").append(getSoLinger());
        sb.append("\n");
        res.setSamplerData(sb.toString());

        try {
            res.sampleStart();

            Socket sock = getSocket(socketKey);
            if (sock == null) {
                res.setResponseCode("500");
                res.setResponseMessage(getError());
            } else if (acpClient == null) {
                res.setResponseCode("500");
                res.setResponseMessage("ACP client not found");
            } else {
                currentSocket = sock;

                InputStream is = sock.getInputStream();
                OutputStream os = sock.getOutputStream();

                String reqData = acpClient.write(os);
                res.setSamplerData(reqData);

                String resData = acpClient.read(is);
                res.setResponseCodeOK();
                res.setResponseMessageOK();
                res.setResponseData(resData, acpClient.getCharset());

                isSuccessful = true;
            }
        } catch (IOException ex) {
            logger.error(this + " Error sampling", ex);
            res.setResponseCode("500");
            res.setResponseMessage("Error sampling " + ex.toString());
            closeSocket(socketKey);
        } catch (ACPException ex) {
            logger.error(this + " Error reading", ex);
            res.setResponseCode("500");
            res.setResponseMessage("Error reading " + ex.toString());
            closeSocket(socketKey);
        } finally {
            currentSocket = null;

            res.sampleEnd();
            res.setSuccessful(isSuccessful);

            if (!reUseConnection || closeConnection) {
                if (logger.isDebugEnabled()) {
                    logger.debug(this + " Not set re-use flag or has close flag");
                }
                closeSocket(socketKey);
            }
        }

        return res;
    }

    private void initSampling() {
        acpClient = getACPClient();

        if (logger.isDebugEnabled()) {
            logger.debug(this + " Using Protocol Handler: " + (acpClient == null ? "NONE" : acpClient.getClass().getName()) + "@" + Integer.toHexString(acpClient.hashCode()));
        }

        if (acpClient != null) {
            acpClient.setUp();
        }
    }

    private ACPClient getACPClient() {
        ACPClient acpClient = null;

        Class<?> javaClass = getClass(getClassname());
        if (javaClass == null) {
            return null;
        }

        try {
            Constructor<?> c = javaClass.getDeclaredConstructor(ACPSampler.class, String.class);
            c.setAccessible(true);
            acpClient = (ACPClient) c.newInstance(this, getRequestData());
        } catch (Exception ex) {
            logger.error(this + " Exception creating: " + getClassname(), ex);
        }

        return acpClient;
    }

    private Class<?> getClass(String className) {
        Class<?> c = null;

        try {
            c = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException ex) {
            try {
                c = Class.forName(protoPrefix + className, false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException ey) {
                logger.error(this + " Could not find protocol class '" + className + "'", ey);
            }
        }

        return c;
    }

    private final String getSocketKey() {
        return ACPKEY + "#" + getServer() + "#" + getPort();
    }

    private Socket getSocket(String socketKey) {
        Socket con = null;

        Map<String, Object> cp = tp.get();
        if (isReUseConnection()) {
            con = (Socket) cp.get(socketKey);
            if (con != null) {
                if (logger.isDebugEnabled()) {
                    logger.debug(this + " Reusing connection " + socketKey + ": " + con);
                }
            }
        }
        if (con == null) {
            try {
                closeSocket(socketKey);

                if (logger.isDebugEnabled()) {
                    logger.debug(this + " Creating connection " + socketKey
                            + ": " + getServer() + ":" + getPort()
                            + " SoLinger " + getPropertyAsString(SO_LINGER, "")
                            + " Connect Timeout " + getConnectTimeout());
                }
                SocketAddress sockAddr = new InetSocketAddress(getServer(), getPort());
                con = new Socket();
                if (getPropertyAsString(SO_LINGER, "").length() > 0) {
                    con.setSoLinger(true, getSoLinger());
                }
                con.connect(sockAddr, getConnectTimeout());
                if (logger.isDebugEnabled()) {
                    logger.debug(this + " Created new connection " + socketKey + ": " + con);
                }
                cp.put(socketKey, con);
            } catch (UnknownHostException ex) {
                logger.warn(this + " Unknown host for " + socketKey, ex);
                cp.put(ERRKEY, ex.toString());
                return null;
            } catch (IOException ex) {
                logger.warn(this + " Could not create socket for " + socketKey, ex);
                cp.put(ERRKEY, ex.toString());
                return null;
            }
        }
        try {
            if (logger.isDebugEnabled()) {
                logger.debug(this + " Setting " + socketKey + ": " + con
                        + " Timeout " + getTimeout()
                        + " NoDelay " + isNoDelay());
            }
            con.setSoTimeout(getTimeout()); // timeout of zero is interpreted as an infinite timeout
            con.setTcpNoDelay(isNoDelay());
        } catch (SocketException ex) {
            logger.warn(this + " Could not set timeout or nodelay for " + socketKey + ": " + con, ex);
            cp.put(ERRKEY, ex.toString());
        }

        return con;
    }

    private void closeSocket(String socketKey) {
        Map<String, Object> cp = tp.get();
        Socket con = (Socket) cp.remove(socketKey);
        if (con != null) {
            if (logger.isDebugEnabled()) {
                logger.debug(this + " Closing connection " + socketKey + ": " + con);
            }
            try {
                con.close();
            } catch (IOException ex) {
                logger.warn(this + " Error closing socket " + socketKey + ": " + con + " " + ex);
            }
        }
    }

    private String getError() {
        Map<String, Object> cp = tp.get();
        return (String) cp.get(ERRKEY);
    }

    private void tearDown() {
        Map<String, Object> cp = tp.get();
        for (Map.Entry<String, Object> element : cp.entrySet()) {
            if (element.getKey().startsWith(ACPKEY)) {
                try {
                    ((Socket) element.getValue()).close();
                } catch (IOException ex) {
                    logger.warn(this + " Error closing socket " + element.getKey() + ": " + element.getValue() + " " + ex);
                }
            }
        }
        cp.clear();
        tp.remove();
    }

    @Override
    public boolean applies(ConfigTestElement configElement) {
        String guiClass = configElement.getProperty(TestElement.GUI_CLASS).getStringValue();
        return APPLIABLE_CONFIG_CLASSES.contains(guiClass);
    }

    @Override
    public void threadStarted() {
        if (logger.isDebugEnabled()) {
            logger.debug(this + " Thread Started");
        }

        firstSample = true;
    }

    @Override
    public void threadFinished() {
        if (logger.isDebugEnabled()) {
            logger.debug(this + " Thread Finished");
        }

        if (acpClient != null) {
            acpClient.tearDown();
        }

        tearDown();
    }

    @Override
    public boolean interrupt() {
        Socket socket = currentSocket;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException ex) {
                logger.warn(this + " Error closing socket " + socket + " " + ex);
            }
            return true;
        }
        return false;
    }

    private static final TreeMap<String, ACPClient> acpClientImpls = new TreeMap<String, ACPClient>();

    static {
        try {
            String jarFileName = ACPSampler.class.getProtectionDomain().getCodeSource().getLocation().getFile();
            logger.info("Searching in ACPClient in " + jarFileName);
            JarFile jarFile = new JarFile(jarFileName);
            Enumeration<JarEntry> entires = jarFile.entries();
            Pattern pat = Pattern.compile(".*/(ACPClient[a-zA-Z]+Message)\\.class$");
            while (entires.hasMoreElements()) {
                Matcher mat = pat.matcher(entires.nextElement().getName());
                if (mat.find()) {
                    String className = mat.group(1);
                    logger.info("Found ACPClient " + className);
                    ACPClient acpClient = ((ACPClient) Class.forName(ACPSampler.class.getPackage().getName() + "." + className, false, Thread.currentThread().getContextClassLoader()).newInstance());
                    acpClientImpls.put(className, acpClient);
                }
            }
        } catch (Exception ex) {
            logger.error("Error getting ACPClient implementations", ex);
        }
    }

    public static String getDefaultRequestData(String className) {
        if ("".equals(className) || !acpClientImpls.containsKey(className)) {
            return StringUtils.join(acpClientImpls.keySet(), "\n");
        } else {
            return acpClientImpls.get(className).getDefaultRequestData();
        }
    }
}
