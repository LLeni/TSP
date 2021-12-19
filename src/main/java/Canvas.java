import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Canvas extends JFrame {

    private ArrayList<City> cities;

    public Canvas(String s, ArrayList<City> cities) {
        super(s);
        setLayout(null);
        setSize(600, 600);
        setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.cities = cities;
    }

    public void paint(Graphics g) {
        g.setColor(Color.BLUE);
     //   g.drawLine(x1, y1, x2, y2);

    }
}