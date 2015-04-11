package com.hcwins.vehicle.ta.acp.sampler.control.gui;

import com.hcwins.vehicle.ta.acp.sampler.config.gui.ACPConfigGui;
import com.hcwins.vehicle.ta.acp.sampler.sampler.ACPSampler;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;

import java.awt.*;

public class ACPSamplerGui extends AbstractSamplerGui {
    private ACPConfigGui acpDefaultPanel;

    public ACPSamplerGui() {
        init();
    }

    @Override
    public void configure(TestElement element) {
        super.configure(element);

        acpDefaultPanel.configure(element);
    }

    @Override
    public TestElement createTestElement() {
        ACPSampler sampler = new ACPSampler();
        modifyTestElement(sampler);
        return sampler;
    }

    @Override
    public void modifyTestElement(TestElement sampler) {
        sampler.clear();
        sampler.addTestElement(acpDefaultPanel.createTestElement());
        this.configureTestElement(sampler);
    }

    @Override
    public void clearGui() {
        super.clearGui();

        acpDefaultPanel.clearGui();
    }

    @Override
    public String getStaticLabel() {
        return "ACP Sampler";
    }

    @Override
    public String getLabelResource() {
        return "ACP Sampler";
    }

    private void init() {
        setLayout(new BorderLayout(0, 5));

        setBorder(makeBorder());
        add(makeTitlePanel(), BorderLayout.NORTH);

        VerticalPanel mainPanel = new VerticalPanel();

        acpDefaultPanel = new ACPConfigGui(false);
        mainPanel.add(acpDefaultPanel);

        add(mainPanel, BorderLayout.CENTER);
    }
}
