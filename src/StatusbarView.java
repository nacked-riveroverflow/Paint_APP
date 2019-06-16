import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.text.NumberFormat;

public class StatusbarView extends JPanel{

    public DrawingPanel model;
    public JLabel numStrokes = new JLabel("2");
    public JLabel Strokes = new JLabel("Strokes");

    private NumberFormat formatter = NumberFormat.getNumberInstance();
    private static final int MAX_FRACTION_DIGITS = 5;

    public StatusbarView(int num) {
        super();

        numStrokes.setText(formatter.format(num));
        this.layoutview();
        System.out.println(A_one.DemoBorderLayout.drawingPanel.numstroke + "part2");
        //Put an uodate view here
        if(A_one.DemoBorderLayout.drawingPanel.numstroke != 1) {
            Strokes.setText("Strokes");
        }else{
            Strokes.setText("Stroke");
        }

        this.repaint();
        A_one.DemoBorderLayout.drawingPanel.repaint();



    }

    private void layoutview() {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.add(this.numStrokes);
        this.add(this.Strokes);

    }

}
