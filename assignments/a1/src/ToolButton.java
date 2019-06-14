import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToolButton extends JButton implements ActionListener
{
    public static Tool currentTool;

    /**************************************************************************************************************
     *****************************************************VARIABLES************************************************
     **************************************************************************************************************/
    public JLabel label;
    public Tool tool;

    /**************************************************************************************************************
     ***************************************************CONSTRUCTOR************************************************
     **************************************************************************************************************/
    public ToolButton(Icon icon, Tool tool)
    {
        label = new JLabel(icon);
        setLayout(new BorderLayout());
        add(label);
        this.tool = tool;
        addActionListener(this);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
    }

    /**************************************************************************************************************
     **********************************************ACTION EVENT****************************************************
     **************************************************************************************************************/
    public void actionPerformed(ActionEvent event)
    {
        Color prev_clr =  A_one.DemoBorderLayout.drawingPanel.brushColor;
        A_one.DemoBorderLayout.drawingPanel.currentTool = tool;
        if(tool.toolType == 2){
            A_one.DemoBorderLayout.drawingPanel.isDrawing = false;
            A_one.DemoBorderLayout.drawingPanel.isremoving = true;
        } else if (tool.toolType == 1){
            A_one.DemoBorderLayout.drawingPanel.isfill = true;
        } else if (tool.toolType == 0){
            A_one.DemoBorderLayout.drawingPanel.isDrawing = false;
            A_one.DemoBorderLayout.drawingPanel.selected = true;
        }

        A_one.DemoBorderLayout.drawingPanel.brushColor = prev_clr;
        A_one.DemoBorderLayout.drawingPanel.repaint();
    }

    public static Tool createTool(int tool_num){
        switch (tool_num) {
            case 0 :
                Tool arrowTool = new Tool(0); //create new class instance
                currentTool = arrowTool;                       //set currentTool to that class instance
                break;
            case 1 :
                Tool brushTool = new Tool(1); //create new class instance
                currentTool = brushTool;
                break;
            case 2 :
                Tool eraserTool = new Tool(2); //create new class instance
                currentTool = eraserTool;
                break;
            case 3 :
                Tool LineTool = new Tool(3); //create new class instance
                currentTool = LineTool;
                break;
            case 4 :
                Tool CircleTool = new Tool(4); //create new class instance
                currentTool = CircleTool;
                break;
            case 5 :
                Tool RectangleTool = new Tool(5); //create new class instance
                currentTool = RectangleTool;
                break;
            case 6 :
                Tool FillTool = new Tool(6); //create new class instance
                currentTool = FillTool;
                break;
            case 7 :
                Tool Pencil = new Tool(7); //create new class instance
                currentTool = Pencil;
                break;
        }
        return currentTool;
    }
}