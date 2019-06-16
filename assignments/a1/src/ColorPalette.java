import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorPalette extends JPanel {

    public static Color selectedColor;
    public static JPanel selectedColorDisplay;
    public static ColorButton color_arr[];

    
    public ColorPalette() {
        this.setBackground(Color.WHITE);
        this.setLayout(new GridLayout(4, 2));
        color_arr = new ColorButton[6];
        ColorButton first_colr = new ColorButton(Color.RED);
        this.add(first_colr);
        color_arr[0] = first_colr;
        ColorButton second_colr = new ColorButton(Color.BLUE);
        this.add(second_colr);
        color_arr[1] = second_colr;
        ColorButton third_colr = new ColorButton(Color.YELLOW);
        this.add(third_colr);
        color_arr[2] = third_colr;
        ColorButton fourth_colr = new ColorButton(Color.PINK);
        this.add(fourth_colr);
        color_arr[3] = fourth_colr;
        ColorButton fifth_colr = new ColorButton(Color.GREEN);
        this.add(fifth_colr);
        color_arr[4] = fifth_colr;
        ColorButton six_colr = new ColorButton(Color.BLACK);
        this.add(six_colr);
        color_arr[5] = six_colr;
        JButton text_b = new JButton ("Current Color");
        this.add(text_b);

        selectedColor = Color.black;
        selectedColorDisplay = new JPanel();
        selectedColorDisplay.setBackground(A_one.DemoBorderLayout.drawingPanel.currentTool.color);
        this.add(selectedColorDisplay);

        //Refereed to class example here
        selectedColorDisplay.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                selectedColorDisplay.setBackground(A_one.DemoBorderLayout.drawingPanel.brushColor);
                selectedColor = selectedColorDisplay.getBackground();                                   //change the isSelected color
                A_one.DemoBorderLayout.drawingPanel.setCurrentColor(selectedColor);                     //change the DrawingPanel brushColor
                }
        });
    }
    public void paintComponent(Graphics g)                      //overrides method in JComponent
    {
        super.paintComponent(g);
    }

}