import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToolPanel extends JPanel
{
    /**************************************************************************************************************
     *****************************************************VARIABLES************************************************
     **************************************************************************************************************/
    protected ToolButton toolButtons[];
    private Icon pencil = new ImageIcon(getClass().getResource("./pencil.png"));
    private Icon oval = new ImageIcon(getClass().getResource("oval.png"));
    private Icon rectangle = new ImageIcon(getClass().getResource("rectangle.png"));
    private Icon lineTool = new ImageIcon(getClass().getResource("line-tool.png"));
    private Icon paintBrush = new ImageIcon(getClass().getResource("paint-brush.png"));
    private Icon eraser = new ImageIcon(getClass().getResource("eraser.png"));
    private JPanel toolPanel = new JPanel();
    private ToolButton colorPickerButton;


    /**************************************************************************************************************
     ***************************************************CONSTRUCTOR************************************************
     **************************************************************************************************************/
    public ToolPanel(/*StrokeToolPanel strokeToolPanel*/)
    {
        setBackground(Color.darkGray);                          //customize the panel
        setPreferredSize(new Dimension(200, 250));
        //setLayout(new BorderLayout(8, 8));

        toolPanel.setLayout(new GridLayout(3, 2));              //customize the tool panel
        toolPanel.setBackground(Color.darkGray);
        toolPanel.setPreferredSize(new Dimension(200, 250));
        //this.strokeToolPanel = strokeToolPanel;                 //set stroke panel
        toolButtons = new ToolButton[6];                        //create new array of buttons

        /**************************************************************************************************************
         ***************************************************Color Picker************************************************
         **************************************************************************************************************/
        Icon colorPicker = new ImageIcon(getClass().getResource("./picker.png"));
        colorPickerButton = new ToolButton(colorPicker, ToolButton.createTool(1));
        colorPickerButton.addMouseListener(new MouseAdapter() {      //add a mouse listener

            public void mousePressed(MouseEvent event) {
                {
                    //ColorPalette.selectedColorDisplay.setBackground(JColorChooser.showDialog(Main.paint, "Change Color", Main.paint.drawingPanel.brushColor));
                    ColorPalette.selectedColor = ColorPalette.selectedColorDisplay.getBackground();                                   //change the isSelected color
                    A_one.DemoBorderLayout.drawingPanel.setCurrentColor(ColorPalette.selectedColorDisplay.getBackground());     //change the ToolDetails color
                    A_one.DemoBorderLayout.drawingPanel.setCurrentColor(ColorPalette.selectedColor);                                   //change the DrawingPanel brushColor

                }
            }
        });

        addBasicToolButtons();
        for (ToolButton toolButton : toolButtons) toolPanel.add(toolButton);    //add buttons to tool panel

        add(toolPanel, "North");
    }

    private void addBasicToolButtons()
    {
        toolButtons[0] = new ToolButton(pencil, ToolButton.createTool(0));//Arrow
        toolButtons[1] = new ToolButton(eraser, ToolButton.createTool(2));//Erase
        toolButtons[2] = new ToolButton(lineTool, ToolButton.createTool(3));//line
        toolButtons[3] = new ToolButton(oval, ToolButton.createTool(4));//circle
        toolButtons[4] = new ToolButton(rectangle, ToolButton.createTool(5));//square
        toolButtons[5] = new ToolButton(paintBrush, ToolButton.createTool(1));//paint
    }
}