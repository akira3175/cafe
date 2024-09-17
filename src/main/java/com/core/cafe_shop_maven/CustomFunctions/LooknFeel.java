/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.core.cafe_shop_maven.CustomFunctions;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import static javax.swing.JFrame.setDefaultLookAndFeelDecorated;

/**
 *
 * @author Admin
 */
public class LooknFeel {
    public static void init(JFrame f) {
        setDefaultLookAndFeelDecorated(true);
        f.getRootPane().putClientProperty("JRootPane.titleBarBackground", new Color(224, 145, 72));
        f.getRootPane().putClientProperty("JRootPane.titleBarForeground", Color.white);
        f.setIconImage(new ImageIcon("image/ManagerUI/avatar.png").getImage());
        f.setMinimumSize(f.getSize());
    }
}
