import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public class PlayerBullet implements Serializable {
    private double x, y;

    public PlayerBullet(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void move(String face) {
        if (face.equals("right"))
            x += 6;
        else if (face.equals("left"))
            x -= 6;
        else if (face.equals("up"))
            y -= 6;
        else
            y += 6;
    }

    public void customSetColor(Graphics g, Color c) {
        g.setColor(c);
    }

    public void draw(Graphics g, Color c) {
        customSetColor(g, c);
        g.fillOval((int) x, (int) y, 10, 10);
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }

}