package com.core.cafe_shop_maven.CustomFunctions;

import java.awt.LayoutManager;
import javax.swing.JPanel;

public class TransparentPanel extends JPanel {

    public TransparentPanel() {
        this.setOpaque(false);
    }

    public TransparentPanel(LayoutManager layout) {
        this.setLayout(layout);
    }

}
