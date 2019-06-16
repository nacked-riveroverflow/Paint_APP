import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Class that represents one selectedColor button (box) located in the ColorPalette
public class ColorButton extends JButton
{
    Color color;
    boolean isSelected;

    public ColorButton(Color clr)
    {
        color = clr;                                //set selectedColor of the box
        isSelected = false;                         //set isSelected to false
        setBackground(color);                       //set background to the given selectedColor
        addMouseListener(new MouseHandler());       //add mouse event
        setContentAreaFilled(false);
        setOpaque(true);
    }

    /**************************************************************************************************************
     *******************************************************PAINT METHOD*******************************************
     **************************************************************************************************************/
    public void paintComponent(Graphics g)     //overrides method in JComponent
    {
        super.paintComponent(g);               //call base method
        g.setColor(Color.lightGray);           //set selectedColor of
        g.drawRect(0, 0, getWidth(), getHeight());   //draw rectangle representing the selectedColor box
        if(isSelected)
        {                                      //change the appearance if the selectedColor box is isSelected
            g.setColor(Color.white);
            g.drawRect(1, 1, getWidth(), getHeight());
            g.drawRect(-1, -1, getWidth(), getHeight());
        }
    }

    /**************************************************************************************************************
     *******************************************************MOUSE EVENTS*******************************************
     **************************************************************************************************************/
    private class MouseHandler extends MouseAdapter
    {
        public void mousePressed(MouseEvent event)          //when the mouse button is pressed
        {
            isSelected = true;                                //set the current selectedColor to isSelected
            A_one.DemoBorderLayout.drawingPanel.setCurrentColor(color);      //set the brush selectedColor of the draw panel to the isSelected selectedColor
            ColorPalette.selectedColorDisplay.setBackground(color);  //set the selectedColor of the selectedColorDisplay
            A_one.DemoBorderLayout.drawingPanel.repaint();                            //repaint the main application window
        }
    }
}
