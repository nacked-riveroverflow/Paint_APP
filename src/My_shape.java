import java.awt.*;
import java.awt.geom.*;
import javax.vecmath.*;
import java.util.*;

public class My_shape {

    ArrayList<Point2d> points;
    boolean isdragged = false;
    public boolean isfilled = false;
    Color color = Color.black;
    double scale = 1.0;
    public int thinkness;
    public int Tooltype;
    public int rotateAngle = 0;
    public int dragX = 0;
    public int dragY = 0;
    public int width = 0;
    public int height = 0;
    public AffineTransform ATF;
    int minX = 1000;            // smallest x coordinate of the stroke
    int minY = 1000;            // smallest y coordinate of the stroke
    int maxX = 0;                // largest x coordinate of the stroke
    int maxY = 0;

    public Color getColour() {
        return color;
    }

}
