import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Font;

import java.awt.Toolkit;

public class Main {
    private JFrame obj;
    private JFrame enterServerIPFrame;
    private JFrame enterServerPortFrame;
    private JLabel lbTitle;
    // private JLabel notification;
    private JButton btTwoPlayerOffline;
    private JButton btClient;
    private JButton btServer;
    private JButton btQuit;
    private JButton btTitle;
    private JButton btOk;
    private JLabel lbCopyright;
    private JTextField textIP;
    private JTextField textPort;
    public static Sound sound = new Sound("BlueBoyAdventure.wav");
    public static Sound palyer1ShootSound = new Sound("player1_shoot_sound.wav");
    public static Sound palyer2ShootSound = new Sound("player2_shoot_sound.wav");
    
    private class ClientConnectServer implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btTitle) {
                String IP = textIP.getText();
                int port = Integer.parseInt(textPort.getText());
                JFrame gameClient = new JFrame();
                Client gamePlayClient = new Client(IP, port);
                sound.playMusic();
                // gamePlayOffline.setBackground(Color.red);
                gameClient.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        sound.stopMusic();
                    }
                });
                gameClient.setBounds(10, 10, 820, 630);
                gameClient.setTitle("Tank 2D");
                gameClient.setBackground(Color.gray);
                gameClient.setResizable(false);
                gameClient.setLocationRelativeTo(null);
                gameClient.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                URL URL_IconTankClient = Client.class.getResource("tank.jpg");
                Image iconTankClient = Toolkit.getDefaultToolkit().createImage(URL_IconTankClient);
                gameClient.setIconImage(iconTankClient);
                gameClient.add(gamePlayClient);
                gameClient.setVisible(true);
                obj.setVisible(true);
                enterServerIPFrame.setVisible(false);
                System.out.println("The End");
            }
            else {
                enterServerPortFrame.setVisible(false);
                JOptionPane.showMessageDialog(obj, "Server is already...", "Notification",
                JOptionPane.INFORMATION_MESSAGE);

                JFrame gameServer = new JFrame();
                int port = Integer.parseInt(textPort.getText());
                Server gamePlayServer = new Server(port);
                sound.playMusic();
                // gamePlayOffline.setBackground(Color.red);
                gameServer.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        sound.stopMusic();
                    }
                });
                gameServer.setBounds(10, 10, 820, 630);
                gameServer.setTitle("Tank 2D");
                gameServer.setBackground(Color.gray);
                gameServer.setResizable(false);
                gameServer.setLocationRelativeTo(null);
                gameServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                URL URL_IconTankServer = Server.class.getResource("tank.jpg");
                Image iconTankServer = Toolkit.getDefaultToolkit().createImage(URL_IconTankServer);
                gameServer.setIconImage(iconTankServer);
                gameServer.add(gamePlayServer);
                gameServer.setVisible(true);
                obj.setVisible(true);
                System.out.println("The End");
            }
        }
    }
    
    private class CustomActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btQuit) {
                obj.dispatchEvent(new WindowEvent(obj, WindowEvent.WINDOW_CLOSING));
            }
            else if (e.getSource() == btTwoPlayerOffline) {
                JFrame gameOfflineFrame = new JFrame();
                Gameplay gamePlayOffline = new Gameplay();
                sound.playMusic();
                // gamePlayOffline.setBackground(Color.red);
                gameOfflineFrame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent we) {
                        sound.stopMusic();
                    }
                });
                gameOfflineFrame.setBounds(10, 10, 820, 630);
                gameOfflineFrame.setTitle("Tank 2D");
                gameOfflineFrame.setBackground(Color.gray);
                gameOfflineFrame.setResizable(false);
                gameOfflineFrame.setLocationRelativeTo(null);
                gameOfflineFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                URL URL_IconTankOffline = Gameplay.class.getResource("tank.jpg");
                Image iconTankOffline = Toolkit.getDefaultToolkit().createImage(URL_IconTankOffline);
                gameOfflineFrame.setIconImage(iconTankOffline);
                gameOfflineFrame.add(gamePlayOffline);
                gameOfflineFrame.setVisible(true);
                obj.setVisible(true);
                System.out.println("The End");
            }
            else if (e.getSource() == btServer) {
                enterServerPortFrame = new JFrame();

                JLabel portjlb = new JLabel("Enter Server Port");
                portjlb.setFont(new Font("Sans-serif", Font.BOLD, 20));
                portjlb.setForeground(Color.RED);
                portjlb.setBounds(170, 20, 200, 30);
                enterServerPortFrame.add(portjlb);

                textPort = new JTextField();
                textPort.setFont(new Font("Consolas", Font.BOLD, 18));
                textPort.setBounds(100, 70, 300, 30);
                enterServerPortFrame.add(textPort);

                btOk = new JButton("OK");
                btOk.setFont(new Font("Sans-serif", Font.BOLD, 10));
                btOk.setBackground(Color.decode("#7cbf8e"));
                btOk.setBounds(225, 110, 50, 50);
                enterServerPortFrame.add(btOk);
                btOk.addActionListener(new ClientConnectServer());

                enterServerPortFrame.setBounds(10, 10, 500, 210);
                enterServerPortFrame.setTitle("Enter Server Port");
                enterServerPortFrame.setBackground(Color.gray);
                enterServerPortFrame.setResizable(false);
                enterServerPortFrame.setLocationRelativeTo(null);
                enterServerPortFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                URL URL_IconTank = Main.class.getResource("tank.jpg");
                Image iconTank = Toolkit.getDefaultToolkit().createImage(URL_IconTank);
                enterServerPortFrame.setIconImage(iconTank);
                enterServerPortFrame.setLayout(null);
                enterServerPortFrame.setVisible(true);
            }
            else {
                enterServerIPFrame = new JFrame();
                
                JLabel IP = new JLabel("Enter Server IP");
                IP.setFont(new Font("Sans-serif", Font.BOLD, 20));
                IP.setForeground(Color.RED);
                IP.setBounds(180, 20, 200, 30);
                enterServerIPFrame.add(IP);
                
                textIP = new JTextField();
                textIP.setFont(new Font("Consolas", Font.BOLD, 18));
                textIP.setBounds(100, 70, 300, 30);
                enterServerIPFrame.add(textIP);
                
                JLabel port = new JLabel("Enter Server Port");
                port.setFont(new Font("Sans-serif", Font.BOLD, 20));
                port.setForeground(Color.RED);
                port.setBounds(170, 120, 200, 30);
                enterServerIPFrame.add(port);

                textPort = new JTextField();
                textPort.setFont(new Font("Consolas", Font.BOLD, 18));
                textPort.setBounds(100, 170, 300, 30);
                enterServerIPFrame.add(textPort);
                
                btTitle = new JButton("OK");
                btTitle.setFont(new Font("Sans-serif", Font.BOLD, 10));
                btTitle.setBackground(Color.decode("#7cbf8e"));
                btTitle.setBounds(225, 230, 50, 50);
                enterServerIPFrame.add(btTitle);
                btTitle.addActionListener(new ClientConnectServer());

                enterServerIPFrame.setBounds(10, 10, 500, 350);
                enterServerIPFrame.setTitle("Enter Server IP and Port");
                enterServerIPFrame.setBackground(Color.gray);
                enterServerIPFrame.setResizable(false);
                enterServerIPFrame.setLocationRelativeTo(null);
                enterServerIPFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                URL URL_IconTank = Main.class.getResource("tank.jpg");
                Image iconTank = Toolkit.getDefaultToolkit().createImage(URL_IconTank);
                enterServerIPFrame.setIconImage(iconTank);
                enterServerIPFrame.setLayout(null);
                enterServerIPFrame.setVisible(true);
            }
        }
    }

    public void run() {
        obj = new JFrame();
        CustomActionListener ctl = new CustomActionListener();
        // obj.setSize(820, 630);

        lbTitle = new JLabel("Menu");
        lbTitle.setFont(new Font("Sans-serif", Font.BOLD, 60));
        lbTitle.setForeground(Color.RED);
        lbTitle.setBounds(325, 0, 200, 200);
        obj.add(lbTitle);

        btTwoPlayerOffline = new JButton("Two Player Offline");
        btTwoPlayerOffline.setFont(new Font("Sans-serif", Font.BOLD, 30));
        btTwoPlayerOffline.setBounds(260, 180, 300, 50);
        obj.add(btTwoPlayerOffline);
        
        btClient = new JButton("Client");
        btClient.setFont(new Font("Sans-serif", Font.BOLD, 30));
        btClient.setBounds(260, 250, 300, 50);
        obj.add(btClient);
        
        btServer = new JButton("Server");
        btServer.setFont(new Font("Sans-serif", Font.BOLD, 30));
        btServer.setBounds(260, 320, 300, 50);
        obj.add(btServer);
        
        btQuit = new JButton("Quit");
        btQuit.setFont(new Font("Sans-serif", Font.BOLD, 30));
        btQuit.setBounds(260, 390, 300, 50);
        obj.add(btQuit);
        
        lbCopyright = new JLabel("© Copyright by  __17-B3__Legend__ & Do not Reup");
        lbCopyright.setFont(new Font("Sans-serif", Font.BOLD, 20));
        lbCopyright.setForeground(Color.BLUE);
        lbCopyright.setBounds(160, 500, 600, 50);
        obj.add(lbCopyright);
        
        btTwoPlayerOffline.addActionListener(ctl);
        btClient.addActionListener(ctl);
        btServer.addActionListener(ctl);
        btQuit.addActionListener(ctl);
        
        obj.setBounds(10, 10, 820, 630);
        obj.setTitle("Tank 2D");
        obj.setBackground(Color.gray);
        obj.setResizable(false);
        obj.setLocationRelativeTo(null);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        URL URL_IconTank = Main.class.getResource("tank.jpg");
        Image iconTank = Toolkit.getDefaultToolkit().createImage(URL_IconTank);
        obj.setIconImage(iconTank);
        obj.setLayout(null);
        obj.setVisible(true);
    }

    public static void main(String[] args) {
        Main game = new Main();
        game.run();
    }
}
