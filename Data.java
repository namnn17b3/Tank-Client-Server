import java.io.Serializable;

public class Data implements Serializable {
    private static final long seriaVersionUID = 1L;
    private int playerX;
    private int playerY;
    private boolean playerright;
    private boolean playerleft;
    private boolean playerdown;
    private boolean playerup;
    private int playerscore;
    private int playerlives;
    private boolean playerShoot;
    private String bulletShootDir;
    private PlayerBullet playerBullet;
    private Brick br;
    private boolean rpl;
    private int pause;
    private boolean soundSign;

    public Data() {
    }

    public Data(int playerX, int playerY, boolean playerright, boolean playerleft, boolean playerdown, boolean playerup,
            int playerscore, int playerlives, boolean playerShoot, String bulletShootDir, PlayerBullet playerBullet, Brick br,
            boolean rpl, int pause, boolean soundSign) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.playerright = playerright;
        this.playerleft = playerleft;
        this.playerdown = playerdown;
        this.playerup = playerup;
        this.playerscore = playerscore;
        this.playerlives = playerlives;
        this.playerShoot = playerShoot;
        this.bulletShootDir = bulletShootDir;
        this.playerBullet = playerBullet;
        this.br = br;
        this.rpl = rpl;
        this.pause = pause;
        this.soundSign = soundSign;
    }

    public static long getSeriaversionuid() {
        return seriaVersionUID;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public boolean getPlayerright() {
        return playerright;
    }

    public boolean getPlayerleft() {
        return playerleft;
    }

    public boolean getPlayerdown() {
        return playerdown;
    }

    public boolean getPlayerup() {
        return playerup;
    }

    public int getPlayerscore() {
        return playerscore;
    }

    public int getPlayerlives() {
        return playerlives;
    }

    public boolean getPlayerShoot() {
        return playerShoot;
    }

    public String getBulletShootDir() {
        return bulletShootDir;
    }

    public PlayerBullet getPlayerBullet() {
        return playerBullet;
    }

    public Brick getBrick() {
        return br;
    }

    public boolean getRpl() {
        return rpl;
    }

    public int getPause() {
        return pause;
    }

    public boolean getSoundSign() {
        return soundSign;
    }
}