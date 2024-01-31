import javax.swing.border.AbstractBorder;
import java.awt.*;

public class GUIRoundedBorder extends AbstractBorder {
    private int radius;
    private Color color;

    public GUIRoundedBorder(int radius, Color color) {
        this.radius = radius;
        this.color = color;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);

        //setze die Linienbreite auf 6
        g2d.setStroke(new BasicStroke(6));

        g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}

