package com.gui.common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CommonLabel extends JLabel {
    public final static String FOREGROUND = "0x222222";
    public final static int BIG_FONT_SIZE = 30;
    public final static int SMALL_FONT_SIZE = 20;

    public CommonLabel() {
        // TODO: small font is default
        this.setSmallFont();
        this.setForeground(Color.decode(CommonLabel.FOREGROUND));

        // TODO: add events when mouse entered or exited
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                tabLabelMouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                tabLabelMouseExited(e);
            }
        });
    }

    public void setSmallFont() {
        this.setFont(new Font("Inter", Font.BOLD, CommonLabel.SMALL_FONT_SIZE));
    }

    public void setBigFont() {
        this.setFont(new Font("Inter", Font.BOLD, CommonLabel.BIG_FONT_SIZE));
    }

    public void resetFont() {
        int font_size = this.getFont().getSize();
        this.setFont(new Font("Inter", Font.BOLD, font_size));
    }

    private void tabLabelMouseEntered(MouseEvent e) {
        if(this.isEnabled()) {
            this.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    private void tabLabelMouseExited(MouseEvent e) {
        if(this.isEnabled()) {
            this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }
}
