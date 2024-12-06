/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ipt.eda21606.gui;
import javax.swing.*;
import java.awt.*;

public class LoadingDialog  extends JDialog {
    public LoadingDialog(Frame parent, String message) {
        super(parent, "Loading", true);

        JLabel loadingLabel = new JLabel(message, SwingConstants.CENTER);
        loadingLabel.setPreferredSize(new Dimension(200, 100));

        add(loadingLabel);
        pack();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }
}
