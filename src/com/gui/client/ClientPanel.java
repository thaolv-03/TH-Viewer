package com.gui.client;

import com.bus.common.CommonBus;
import com.gui.MainFrame;
import com.gui.common.CommonLabel;
import com.gui.common.CommonPanel;
import com.gui.remote.RemoteFrame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ClientPanel extends JPanel {
    public final static String BACKGROUND = "0xE8E8E8";
    public final static String FOREGROUND = "0x1C1C1C";

    private CommonPanel main_panel;
    private CommonLabel connect_label;
    private ButtonGroup button_group;
    private JRadioButton low_radio;
    private JRadioButton high_radio;
    private JLabel loader_label;

    private CommonBus common_bus;

    public ClientPanel(CommonBus common_bus) {
        // TODO: style ClientPanel
        this.setLocation(0, MainFrame.HEIGHT_TASKBAR);
        this.setSize(MainFrame.WIDTH_FRAME, MainFrame.HEIGHT_FRAME - MainFrame.HEIGHT_TASKBAR);
        this.setBackground(Color.decode(ClientPanel.BACKGROUND));
        this.setLayout(null);

        // TODO: class for handle events
        this.common_bus = common_bus;

        // TODO: add components
        this.initComponents();
    }

    private void initComponents() {
        // TODO: constructor
        this.main_panel = new CommonPanel();
        this.connect_label = new CommonLabel();
        this.button_group = new ButtonGroup();
        this.low_radio = new JRadioButton();
        this.high_radio = new JRadioButton();
        this.loader_label = new JLabel();

        // TODO: style main panel
        this.main_panel.setBorder(BorderFactory.createTitledBorder(null, "Điều khiển máy tính khác",
            TitledBorder.DEFAULT_JUSTIFICATION,
            TitledBorder.DEFAULT_POSITION,
            new Font("Inter", Font.BOLD, 16),
            Color.decode(ClientPanel.FOREGROUND))
        );
        this.add(this.main_panel);

        // TODO: style help_label
        this.main_panel.getHelpLabel().setText("<html>Vui lòng nhập IP, Port và mật khẩu của máy tính mà bạn muốn điều khiển</html>");
        this.main_panel.getHelpLabel().setFont(new Font("Inter", Font.ITALIC, 15));
        this.main_panel.getHelpLabel().setLocation(26, 30);

        // TODO: style host_label
        this.main_panel.getHostLabel().setText("IP Đối tác:");
        this.main_panel.getHostLabel().setFont(new Font("Inter", Font.BOLD, 14));
        this.main_panel.getHostLabel().setLocation(26, 103);

        // TODO: style host_text
        this.main_panel.getHostCombo().setVisible(false);
        this.main_panel.getHostText().setVisible(true);
        this.main_panel.getHostText().setLocation(140, 110);

        // TODO: style port_label
        this.main_panel.getPortLabel().setText("Port đối tác:");
        this.main_panel.getPortLabel().setFont(new Font("Inter", Font.BOLD, 14));
        this.main_panel.getPortLabel().setLocation(26, 133);

        // TODO: style port_text
        this.main_panel.getPortText().setLocation(140, 140);

        // TODO: style pass_label
        this.main_panel.getPassLabel().setText("Mật khẩu:");
        this.main_panel.getPassLabel().setFont(new Font("Inter", Font.BOLD, 14));
        this.main_panel.getPassLabel().setLocation(26, 163);

        // TODO: style pass_field
        this.main_panel.getPassText().setVisible(false);
        this.main_panel.getPassField().setVisible(true);
        this.main_panel.getPassField().setLocation(140, 170);

        // TODO: style connect_label
        this.connect_label.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("connect_icon.png")));
        this.connect_label.setText("Bắt đầu điều khiển");
        this.connect_label.setBounds(100, 305, 300, 50);
        this.connect_label.setForeground(Color.decode(ClientPanel.FOREGROUND));
        this.connect_label.setFont(new Font("Tahoma", Font.BOLD, 16));
        this.connect_label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                connectLabelMousePressed(e);
            }
        });
        this.add(this.connect_label);

        // TODO: style low_radio
        this.low_radio.setText("Chất lượng thấp");
        this.low_radio.setBounds(55, 270, 150, 30);
        this.low_radio.setOpaque(false);
        this.low_radio.setSelected(true);
        this.low_radio.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.button_group.add(this.low_radio);
        this.add(this.low_radio);

        // TODO: style high_radio
        this.high_radio.setText("Chất lượng cao");
        this.high_radio.setBounds(195, 270, 150, 30);
        this.high_radio.setOpaque(false);
        this.high_radio.setFont(new Font("Tahoma", Font.BOLD, 14));
        this.button_group.add(this.high_radio);
        this.add(this.high_radio);

        // TODO: style loader_label
        this.loader_label.setIcon(new ImageIcon(this.getClass().getClassLoader().getResource("loader_icon.gif")));
        this.loader_label.setBounds(355, 323, 40, 40);
        this.loader_label.setVisible(false);
        this.add(this.loader_label);
    }

    @Override
    public void setEnabled(boolean b) {
        this.main_panel.setEnabled(b);
        this.low_radio.setEnabled(b);
        this.high_radio.setEnabled(b);
        this.connect_label.setEnabled(b);
    }

    private boolean isFormatIpv4(String host) {
        int count = 0;
        for(int i = 0; i < host.length(); ++i) {
            if(host.charAt(i) == '.') ++count;
        }
        // TODO: count = 3 for ipv4
        // TODO: count = 0 for host name
        return count == 3 || count == 0;
    }

    // TODO: handle events of connect_label
    private void connectLabelMousePressed(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1 && this.connect_label.isEnabled()) {
            this.setEnabled(false);
            this.loader_label.setVisible(true);

            Thread connect_thread = new Thread(() -> {
                try {
                    String host = this.main_panel.getHostText().getText().trim();
                    int port = Integer.parseInt(this.main_panel.getPortText().getText().trim());
                    String password = this.main_panel.getPassField().getText().trim();
                    // TODO: check format ipv4
                    if(!this.isFormatIpv4(host)) throw new Exception("Không đúng định dạng IPV4");

                    // TODO: start connect
                    this.common_bus.startConnectingToServer(host, port, password);

                    // TODO: show remote screen
                    EventQueue.invokeLater(() -> {
                        try {
                            if(this.low_radio.isSelected()) {
                                new RemoteFrame(this, this.common_bus, "jpg");
                            }
                            else if(this.high_radio.isSelected()) {
                                new RemoteFrame(this, this.common_bus, "png");
                            }
                        }
                        catch(Exception exception) {
                            JOptionPane.showMessageDialog(this, "Không thể bắt đầu điều khiển từ xa đến máy chủ:\n" + exception.getMessage());
                        }
                    });
                }
                catch(Exception exception) {
                    JOptionPane.showMessageDialog(this, "Không thể kết nối đến máy chủ:\n" + exception.getMessage());
                }
                this.setEnabled(true);
                this.loader_label.setVisible(false);
            });
            connect_thread.setDaemon(true);
            connect_thread.start();
        }
    }
}
