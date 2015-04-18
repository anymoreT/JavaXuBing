package com.hcwins.vehicle.ta.acp.sampler.config.gui;

import com.hcwins.vehicle.ta.acp.sampler.sampler.ACPSampler;
import org.apache.jmeter.config.ConfigTestElement;
import org.apache.jmeter.config.gui.AbstractConfigGui;
import org.apache.jmeter.gui.ServerPanel;
import org.apache.jmeter.gui.util.*;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.gui.JLabeledTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class ACPConfigGui extends AbstractConfigGui {
    private ServerPanel serverPanel;

    private JCheckBox reUseConnection;
    private TristateCheckBox closeConnection;
    private TristateCheckBox setNoDelay;
    private JTextField soLinger;

    private JLabeledTextField classname;

    private JSyntaxTextArea requestData;

    private boolean displayName = true;

    public ACPConfigGui() {
        this(true);
    }

    public ACPConfigGui(boolean displayName) {
        this.displayName = displayName;
        init();
    }

    @Override
    public String getStaticLabel() {
        return "ACP Sampler Config";
    }

    @Override
    public String getLabelResource() {
        return "ACP Sampler Config";
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);

        serverPanel.setServer(element.getPropertyAsString(ACPSampler.SERVER));
        serverPanel.setPort(element.getPropertyAsString(ACPSampler.PORT));
        serverPanel.setConnectTimeout(element.getPropertyAsString(ACPSampler.TIMEOUT_CONNECT));
        serverPanel.setResponseTimeout(element.getPropertyAsString(ACPSampler.TIMEOUT));

        reUseConnection.setSelected(element.getPropertyAsBoolean(ACPSampler.RE_USE_CONNECTION, ACPSampler.RE_USE_CONNECTION_DEFAULT));
        closeConnection.setTristateFromProperty(element, ACPSampler.CLOSE_CONNECTION);
        setNoDelay.setTristateFromProperty(element, ACPSampler.NODELAY);
        soLinger.setText(element.getPropertyAsString(ACPSampler.SO_LINGER));

        classname.setText(element.getPropertyAsString(ACPSampler.CLASSNAME));

        requestData.setInitialText(element.getPropertyAsString(ACPSampler.REQUEST));
        requestData.setCaretPosition(0);
    }

    @Override
    public TestElement createTestElement() {
        ConfigTestElement element = new ConfigTestElement();
        modifyTestElement(element);
        return element;
    }

    @Override
    public void modifyTestElement(TestElement element) {
        configureTestElement(element);

        element.setProperty(ACPSampler.SERVER, serverPanel.getServer());
        element.setProperty(ACPSampler.PORT, serverPanel.getPort());
        element.setProperty(ACPSampler.TIMEOUT_CONNECT, serverPanel.getConnectTimeout(), "");
        element.setProperty(ACPSampler.TIMEOUT, serverPanel.getResponseTimeout(), "");

        element.setProperty(ACPSampler.RE_USE_CONNECTION, reUseConnection.isSelected());
        closeConnection.setPropertyFromTristate(element, ACPSampler.CLOSE_CONNECTION);
        setNoDelay.setPropertyFromTristate(element, ACPSampler.NODELAY);
        element.setProperty(ACPSampler.SO_LINGER, soLinger.getText(), "");

        element.setProperty(ACPSampler.CLASSNAME, classname.getText(), "");

        element.setProperty(ACPSampler.REQUEST, requestData.getText(), "");
    }

    @Override
    public void clearGui() {
        super.clearGui();

        serverPanel.clear();

        reUseConnection.setSelected(ACPSampler.RE_USE_CONNECTION_DEFAULT);
        closeConnection.setSelected(ACPSampler.CLOSE_CONNECTION_DEFAULT);
        setNoDelay.setSelected(ACPSampler.NODELAY_DEFAULT);
        soLinger.setText("");

        classname.setText("ACPClientLocationReceiveMessage");

        requestData.setInitialText("");
    }

    private JPanel createReuseConnectionPanel() {
        JLabel label = new JLabel(JMeterUtils.getResString("reuseconnection"));

        reUseConnection = new JCheckBox("", true);
        reUseConnection.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    closeConnection.setEnabled(true);
                } else {
                    closeConnection.setEnabled(false);
                }
            }
        });
        label.setLabelFor(reUseConnection);

        JPanel closePortPanel = new JPanel(new FlowLayout());
        closePortPanel.add(label);
        closePortPanel.add(reUseConnection);
        return closePortPanel;
    }

    private JPanel createCloseConnectionPanel() {
        JLabel label = new JLabel(JMeterUtils.getResString("closeconnection"));

        closeConnection = new TristateCheckBox("", ACPSampler.CLOSE_CONNECTION_DEFAULT);
        label.setLabelFor(closeConnection);

        JPanel closeConnectionPanel = new JPanel(new FlowLayout());
        closeConnectionPanel.add(label);
        closeConnectionPanel.add(closeConnection);
        return closeConnectionPanel;
    }

    private JPanel createNoDelayPanel() {
        JLabel label = new JLabel(JMeterUtils.getResString("tcp_nodelay"));

        setNoDelay = new TristateCheckBox("", ACPSampler.NODELAY_DEFAULT);
        label.setLabelFor(setNoDelay);

        JPanel noDelayPanel = new JPanel(new FlowLayout());
        noDelayPanel.add(label);
        noDelayPanel.add(setNoDelay);
        return noDelayPanel;
    }

    private JPanel createSoLingerOption() {
        JLabel label = new JLabel(JMeterUtils.getResString("solinger"));

        soLinger = new JTextField(5);
        soLinger.setMaximumSize(new Dimension(soLinger.getPreferredSize()));
        label.setLabelFor(soLinger);

        JPanel soLingerPanel = new JPanel(new FlowLayout());
        soLingerPanel.add(label);
        soLingerPanel.add(soLinger);
        return soLingerPanel;
    }

    private JPanel createClientPanel() {
        classname = new JLabeledTextField("ACPClient classname");
        JButton defaultButton = new JButton("Load Default");
        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                requestData.setText(ACPSampler.getDefaultRequestData(classname.getText()));
            }
        });

        HorizontalPanel clientPanel = new HorizontalPanel();
        clientPanel.add(classname, "Center");
        clientPanel.add(defaultButton, "East");
        return clientPanel;
    }

    private JPanel createRequestPanel() {
        JLabel reqLabel = new JLabel(JMeterUtils.getResString("tcp_request_data"));

        requestData = new JSyntaxTextArea(30, 80);
        requestData.setLanguage("text");
        reqLabel.setLabelFor(requestData);

        JPanel reqDataPanel = new JPanel(new BorderLayout(5, 0));
        reqDataPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
        reqDataPanel.add(reqLabel, BorderLayout.WEST);
        reqDataPanel.add(new JTextScrollPane(requestData), BorderLayout.CENTER);
        return reqDataPanel;
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        if (displayName) {
            setBorder(makeBorder());
            add(makeTitlePanel(), BorderLayout.NORTH);
        }

        VerticalPanel mainPanel = new VerticalPanel();

        serverPanel = new ServerPanel();
        mainPanel.add(serverPanel);

        HorizontalPanel optionsPanel = new HorizontalPanel();
        optionsPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
        optionsPanel.add(createReuseConnectionPanel());
        optionsPanel.add(createCloseConnectionPanel());
        optionsPanel.add(createNoDelayPanel());
        optionsPanel.add(createSoLingerOption());
        mainPanel.add(optionsPanel);

        mainPanel.add(createClientPanel());

        mainPanel.add(createRequestPanel());

        add(mainPanel, BorderLayout.CENTER);
    }
}
