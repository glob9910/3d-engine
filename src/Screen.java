import javax.swing.*;
import java.awt.*;

public class Screen extends JPanel {

    private DrawingInterface d;

    public Screen(DrawingInterface d) {
        this.d = d;
        new Thread(() -> {
            while(true) {
                repaint();
                try {
                    Thread.sleep(1000/15);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    protected void paintComponent(Graphics g) {
        d.draw(g);
    }
}
