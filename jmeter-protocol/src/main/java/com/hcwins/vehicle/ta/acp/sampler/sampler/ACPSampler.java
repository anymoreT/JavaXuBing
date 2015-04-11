package com.hcwins.vehicle.ta.acp.sampler.sampler;

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
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

public class ACPSampler extends AbstractSampler implements ThreadListener, Interruptible {
    private static final Logger log = LoggingManager.getLoggerForClass();

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
        if (log.isDebugEnabled()) {
            log.debug(this + " Created");
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
                String req = getRequestData();
                res.setSamplerData(req);
                acpClient.write(os, req);
                String resData = acpClient.read(is);
                res.setResponseCodeOK();
                res.setResponseMessageOK();
                res.setResponseData(resData, Charset.defaultCharset().name());

                isSuccessful = true;
            }
        } catch (ACPException ex) {
            log.error(this + " Error reading", ex);
            res.setResponseCode("500");
            res.setResponseMessage("Error reading " + ex.toString());
            closeSocket(socketKey);
        } catch (Exception ex) {
            log.error(this + " Error sampling", ex);
            res.setResponseCode("500");
            res.setResponseMessage("Error sampling " + ex.toString());
            closeSocket(socketKey);
        } finally {
            currentSocket = null;

            res.sampleEnd();
            res.setSuccessful(isSuccessful);

            if (!reUseConnection || closeConnection) {
                closeSocket(socketKey);
            }
        }

        return res;
    }

    private void initSampling() {
        acpClient = getACPClient();

        if (log.isDebugEnabled()) {
            log.debug(this + " Using Protocol Handler: " + (acpClient == null ? "NONE" : acpClient.getClass().getName()) + "@" + Integer.toHexString(acpClient.hashCode()));
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
            acpClient = (ACPClient) javaClass.newInstance();
        } catch (Exception e) {
            log.error(this + " Exception creating: " + getClassname(), e);
        }

        return acpClient;
    }

    private Class<?> getClass(String className) {
        Class<?> c = null;

        try {
            c = Class.forName(className, false, Thread.currentThread().getContextClassLoader());
        } catch (ClassNotFoundException e) {
            try {
                c = Class.forName(protoPrefix + className, false, Thread.currentThread().getContextClassLoader());
            } catch (ClassNotFoundException e1) {
                log.error(this + " Could not find protocol class '" + className + "'");
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
                if (log.isDebugEnabled()) {
                    log.debug(this + " Reusing connection " + socketKey + ": " + con);
                }
            }
        }
        if (con == null) {
            try {
                closeSocket(socketKey);

                if (log.isDebugEnabled()) {
                    log.debug(this + " Creating connection " + socketKey
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
                if (log.isDebugEnabled()) {
                    log.debug(this + " Created new connection " + socketKey + ": " + con);
                }
                cp.put(socketKey, con);
            } catch (UnknownHostException e) {
                log.warn("Unknown host for " + socketKey, e);
                cp.put(ERRKEY, e.toString());
                return null;
            } catch (IOException e) {
                log.warn("Could not create socket for " + socketKey, e);
                cp.put(ERRKEY, e.toString());
                return null;
            }
        }
        try {
            if (log.isDebugEnabled()) {
                log.debug(this + " Setting " + socketKey + ": " + con
                        + " Timeout " + getTimeout()
                        + " NoDelay " + isNoDelay());
            }
            con.setSoTimeout(getTimeout()); // timeout of zero is interpreted as an infinite timeout
            con.setTcpNoDelay(isNoDelay());
        } catch (SocketException se) {
            log.warn("Could not set timeout or nodelay for " + socketKey + ": " + con, se);
            cp.put(ERRKEY, se.toString());
        }

        return con;
    }

    private void closeSocket(String socketKey) {
        Map<String, Object> cp = tp.get();
        Socket con = (Socket) cp.remove(socketKey);
        if (con != null) {
            if (log.isDebugEnabled()) {
                log.debug(this + " Closing connection " + socketKey + ": " + con);
            }
            try {
                con.close();
            } catch (IOException e) {
                log.warn("Error closing socket " + socketKey + ": " + con + " " + e);
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
                } catch (IOException e) {
                    log.warn("Error closing socket " + element.getKey() + ": " + element.getValue() + " " + e);
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
        if (log.isDebugEnabled()) {
            log.debug(this + " Thread Started");
        }

        firstSample = true;
    }

    @Override
    public void threadFinished() {
        if (log.isDebugEnabled()) {
            log.debug(this + " Thread Finished");
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
            } catch (IOException e) {
                log.warn(this + " Error closing socket " + socket + " " + e);
            }
            return true;
        }
        return false;
    }
}
