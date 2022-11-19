import java.awt.event.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
// import java.net.URL;

import javax.swing.*;
import java.awt.*;
import javax.swing.Timer;

public class Server extends JPanel implements ActionListener {
    private static boolean hasException = false;
    private Brick br;

    private ImageIcon player1;
    private int player1X = 200;
    private int player1Y = 550;
    private boolean player1right = false;
    private boolean player1left = false;
    private boolean player1down = false;
    private boolean player1up = true;
    private int player1score = 0;
    private int player1lives = 5;
    private boolean player1Shoot = false;
    private String bulletShootDir1 = "";
    private boolean soundSign1 = false;

    private int bricksXPos[] = { 50, 350, 450, 550, 50, 300, 350, 450, 550, 150, 150, 450, 550,
            250, 50, 100, 150, 550, 250, 350, 450, 550, 50, 250, 350, 550,
            50, 150, 250, 300, 350, 550, 50, 150, 250, 350, 450, 550, 50,
            250, 350, 550 };

    private int bricksYPos[] = { 50, 50, 50, 50, 100, 100, 100, 100, 100, 150, 200, 200, 200, 250,
            300, 300, 300, 300, 350, 350, 350, 350, 400, 400, 400, 400, 450,
            450, 450, 450, 450, 450, 500, 500, 500, 500, 500, 500, 550, 550,
            550, 550 };

    private int solidBricksXPos[] = { 150, 350, 150, 500, 450, 300, 600, 400, 350, 200, 0, 200, 500 };

    private int solidBricksYPos[] = { 0, 0, 50, 100, 150, 200, 200, 250, 300, 350, 400, 400, 450 };

    private int brickON[] = new int[42];
    private int brickOnTmp[] = new int[42];
    private ImageIcon breakBrickImage;
    private ImageIcon solidBrickImage;

    private ImageIcon player2;
    private int player2X = 400;
    private int player2Y = 550;
    private boolean player2right = false;
    private boolean player2left = false;
    private boolean player2down = false;
    private boolean player2up = true;
    private int player2score = 0;
    private int player2lives = 5;
    private boolean player2Shoot = false;
    private String bulletShootDir2 = "";
    private boolean soundSign2 = false;

    private boolean rpl1 = false;
    private boolean rpl2 = false;
    private int pause = 0;

    private Timer timer;
    private int delay = 1;

    private Player2Listener player2Listener;

    private PlayerBullet player1Bullet = null;
    private PlayerBullet player2Bullet = null;

    private static Socket socket;
    private static ServerSocket server;
    private Data dataSend;
    private Data dataReceive;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;

    public Server(int port) {
        try {
            server = new ServerSocket(port);
            System.out.println("Server is already...");
            socket = server.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        breakBrickImage = new ImageIcon("break_brick.jpg");
        solidBrickImage = new ImageIcon("solid_brick.jpg");

        for (int i = 0; i < 42; i++) {
            brickON[i] = 1;
        }
        br = new Brick();
        player2Listener = new Player2Listener();
        setFocusable(true);
        // addKeyListener(this);
        addKeyListener(player2Listener);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
        repaint();
    }

    public void rePlay() {
        for (int i = 0; i < 42; i++) {
            brickON[i] = 1;
        }
        br = new Brick();
        player1X = 200;
        player1Y = 550;
        player1right = false;
        player1left = false;
        player1down = false;
        player1up = true;

        player2X = 400;
        player2Y = 550;
        player2right = false;
        player2left = false;
        player2down = false;
        player2up = true;

        player1score = 0;
        player1lives = 5;
        player2score = 0;
        player2lives = 5;
        rpl1 = false;
        rpl2 = false;
        soundSign1 = false;
        soundSign2 = false;
    }

    @Override
    public void paint(Graphics g) {
        // play background
        // g.setColor(Color.black);
        // g.fillRect(0, 0, 650, 600);
        ImageIcon backGroundIcon = new ImageIcon("BG.png");
        backGroundIcon.paintIcon(this, g, 0, 0);

        // right side background
        g.setColor(Color.DARK_GRAY);
        g.fillRect(660, 0, 160, 600);
        
        // draw solid Bricks
        for (int i = 0; i < solidBricksXPos.length; i++) {
            solidBrickImage.paintIcon(this, g, solidBricksXPos[i], solidBricksYPos[i]);
        }
        
        // draw Breakable Bricks
        for (int i = 0; i < 42; i++) {
            if (brickON[i] == 1) {
                breakBrickImage.paintIcon(this, g, bricksXPos[i], bricksYPos[i]);
            }
        }

        // draw player 1
        if (player1up)
            player1 = new ImageIcon("player1_tank_up.png");
        else if (player1down)
            player1 = new ImageIcon("player1_tank_down.png");
        else if (player1right)
            player1 = new ImageIcon("player1_tank_right.png");
        else if (player1left)
            player1 = new ImageIcon("player1_tank_left.png");

        player1.paintIcon(this, g, player1X, player1Y);
        
        // draw player 2
        if (player2up)
        player2 = new ImageIcon("player2_tank_up.png");
        else if (player2down)
        player2 = new ImageIcon("player2_tank_down.png");
        else if (player2right)
        player2 = new ImageIcon("player2_tank_right.png");
        else if (player2left)
        player2 = new ImageIcon("player2_tank_left.png");
        
        player2.paintIcon(this, g, player2X, player2Y);
        
        if (player1Bullet != null && player1Shoot) {
            if (bulletShootDir1.equals("")) {
                if (player1up) {
                    bulletShootDir1 = "up";
                } else if (player1down) {
                    bulletShootDir1 = "down";
                } else if (player1right) {
                    bulletShootDir1 = "right";
                } else if (player1left) {
                    bulletShootDir1 = "left";
                }
            } else {
                player1Bullet.move(bulletShootDir1);
                player1Bullet.draw(g, Color.yellow);
            }
            
            if (new Rectangle(player1Bullet.getX(), player1Bullet.getY(), 10, 10)
                    .intersects(new Rectangle(player2X, player2Y, 50, 50))) {
                player1score += 10;
                player2lives -= 1;
                player1Bullet = null;
                player1Shoot = false;
                bulletShootDir1 = "";
            }
            
            if (player1Bullet != null && (br.checkCollision(player1Bullet.getX(), player1Bullet.getY())
                    || br.checkSolidCollision(player1Bullet.getX(), player1Bullet.getY()))) {
                player1Bullet = null;
                player1Shoot = false;
                bulletShootDir1 = "";
                brickON = br.getBickOn();
            }
            
            if (player1Bullet != null && (player1Bullet.getY() < 1
            || player1Bullet.getY() > 580
            || player1Bullet.getX() < 1
            || player1Bullet.getX() > 630)) {
                player1Bullet = null;
                player1Shoot = false;
                bulletShootDir1 = "";
            }
        }
        
        if (player2Bullet != null && player2Shoot) {
            if (bulletShootDir2.equals("")) {
                if (player2up) {
                    bulletShootDir2 = "up";
                } else if (player2down) {
                    bulletShootDir2 = "down";
                } else if (player2right) {
                    bulletShootDir2 = "right";
                } else if (player2left) {
                    bulletShootDir2 = "left";
                }
            } else {
                player2Bullet.move(bulletShootDir2);
                player2Bullet.draw(g, Color.green);
            }
            
            if (new Rectangle(player2Bullet.getX(), player2Bullet.getY(), 10, 10)
                    .intersects(new Rectangle(player1X, player1Y, 50, 50))) {
                player2score += 10;
                player1lives -= 1;
                player2Bullet = null;
                player2Shoot = false;
                bulletShootDir2 = "";
            }

            if (player2Bullet != null && (br.checkCollision(player2Bullet.getX(), player2Bullet.getY())
            || br.checkSolidCollision(player2Bullet.getX(), player2Bullet.getY()))) {
                player2Bullet = null;
                player2Shoot = false;
                bulletShootDir2 = "";
                brickON = br.getBickOn();
            }

            if (player2Bullet != null && (player2Bullet.getY() < 1
            || player2Bullet.getY() > 580
            || player2Bullet.getX() < 1
            || player2Bullet.getX() > 630)) {
                player2Bullet = null;
                player2Shoot = false;
                bulletShootDir2 = "";
            }
        }

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            dataSend = new Data(player2X, player2Y, player2right, player2left, player2down, player2up,
                    player2score, player2lives, player2Shoot, bulletShootDir2, player2Bullet, br, rpl2, pause, soundSign2);

            oos.writeObject(dataSend);
            // System.out.println(player2lives);
        } catch (IOException e) {
            // e.printStackTrace();
            System.out.println("Client disconnected!");
            hasException = true;
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Client disconnected!", 670, 300);
        }
        try {
            ois = new ObjectInputStream(socket.getInputStream());
            dataReceive = (Data) ois.readObject();
            player1X = dataReceive.getPlayerX();
            player1Y = dataReceive.getPlayerY();
            player1right = dataReceive.getPlayerright();
            player1left = dataReceive.getPlayerleft();
            player1down = dataReceive.getPlayerdown();
            player1up = dataReceive.getPlayerup();
            player1score = dataReceive.getPlayerscore();
            player1lives = dataReceive.getPlayerlives();
            player1Shoot = dataReceive.getPlayerShoot();
            bulletShootDir1 = dataReceive.getBulletShootDir();
            player1Bullet = dataReceive.getPlayerBullet();
            brickOnTmp = dataReceive.getBrick().getBickOn();
            for (int i = 0; i < 42; i++) {
                brickON[i] = brickON[i] & brickOnTmp[i];
            }
            rpl1 = dataReceive.getRpl();
            pause = Math.max(dataReceive.getPause(), pause);
            soundSign1 = dataReceive.getSoundSign();
        } catch (IOException | ClassNotFoundException e) {
            // e.printStackTrace();
            System.out.println("Client disconnected!");
            hasException = true;
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 15));
            g.drawString("Client disconnected!", 670, 300);
        }
        // turn on player1_shoot_sound
        soundSign2 = false;
        if (soundSign1) {
            Main.palyer1ShootSound.playMusicEffect();
            soundSign1 = false;
        }
        // reset
        if (rpl1 || rpl2) {
            rePlay();
        }

        // the scores
        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 15));
        if (hasException == false) {
            g.drawString("Connected to Client!", 670, 300);
        }
        g.drawString("Scores", 700, 30);
        g.drawString("Player 1:  " + player1score, 670, 60);
        g.drawString("Player 2:  " + player2score, 670, 90);

        g.drawString("Lives", 700, 150);
        g.drawString("Player 1:  " + player1lives, 670, 180);
        g.drawString("Player 2:  " + player2lives, 670, 210);
        g.drawString("Pess P to pause", 670, 270);

        if (player1lives == 0) {
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("Game Over", 200, 300);
            g.drawString("Player 2 Won", 180, 380);
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("(Space to Restart)", 230, 430);
        } else if (player2lives == 0) {
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 60));
            g.drawString("Game Over", 200, 300);
            g.drawString("Player 1 Won", 180, 380);
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("(Space to Restart)", 230, 430);
        }

        if (pause % 2 == 1) {
            g.setColor(Color.white);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("(Press P to resume)", 230, 430);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (hasException == false) {
            timer.start();
            repaint();
        }
    }

    private class Player2Listener implements KeyListener {
        public void keyTyped(KeyEvent e) {
        }

        public void keyReleased(KeyEvent e) {
        }

        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE && (player1lives == 0 || player2lives == 0) && pause % 2 == 0) {
                rpl2 = true;
                if (rpl1 && rpl2) {
                    rePlay();
                    Main.sound.playMusic();
                    repaint();
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_M && pause % 2 == 0 && player1lives > 0 && player2lives > 0) {
                if (!player2Shoot) {
                    Main.palyer2ShootSound.playMusicEffect();
                    soundSign2 = true;
                    if (player2up) {
                        player2Bullet = new PlayerBullet(player2X + 20, player2Y);
                    } else if (player2down) {
                        player2Bullet = new PlayerBullet(player2X + 20, player2Y + 40);
                    } else if (player2right) {
                        player2Bullet = new PlayerBullet(player2X + 40, player2Y + 20);
                    } else if (player2left) {
                        player2Bullet = new PlayerBullet(player2X, player2Y + 20);
                    }

                    player2Shoot = true;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_UP && pause % 2 == 0 && player1lives > 0 && player2lives > 0) {
                player2right = false;
                player2left = false;
                player2down = false;
                player2up = true;

                if (!(player2Y < 10) && br.canMove(player2X, player2Y - 10) == true)
                    player2Y -= 10;

            }
            if (e.getKeyCode() == KeyEvent.VK_LEFT && pause % 2 == 0 && player1lives > 0 && player2lives > 0) {
                player2right = false;
                player2left = true;
                player2down = false;
                player2up = false;

                if (!(player2X < 10) && br.canMove(player2X - 10, player2Y) == true)
                    player2X -= 10;
            }
            if (e.getKeyCode() == KeyEvent.VK_DOWN && pause % 2 == 0 && player1lives > 0 && player2lives > 0) {
                player2right = false;
                player2left = false;
                player2down = true;
                player2up = false;
                
                if (!(player2Y > 540) && br.canMove(player2X, player2Y + 10) == true)
                player2Y += 10;
            }
            
            if (e.getKeyCode() == KeyEvent.VK_RIGHT && pause % 2 == 0 && player1lives > 0 && player2lives > 0) {
                player2right = true;
                player2left = false;
                player2down = false;
                player2up = false;

                if (!(player2X > 590) && br.canMove(player2X + 10, player2Y) == true)
                    player2X += 10;
            }

            if (e.getKeyCode() == KeyEvent.VK_P && player1lives > 0 && player2lives > 0) {
                pause += 1;
            }
        }
    }
}