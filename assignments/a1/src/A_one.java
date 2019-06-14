import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import javax.swing.event.*;     // for ChangeListener and ChangeEvent
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoableEdit;
import java.awt.*;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.geom.*;
import java.text.NumberFormat;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.SpringLayout;
import java.awt.LayoutManager;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class A_one {
    public static void main(String[] args) {
        JFrame jj = new LayoutDemoFrame("BorderLayout", new DemoBorderLayout());
        jj.setSize(1024, 768);
        jj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jj.setVisible(true);
        jj.setResizable(false);
    }
    static class DemoBorderLayout extends JPanel {
        public static DrawingPanel drawingPanel;
        public static ColorPalette colorPalette;
        public static StatusbarView sbar;
        public static JPanel Chooser;
        public ToolPanel paintToolPanel;
        public static MenuBar menuBar;
        public static ThicknessPanel ThPanel;


        public DemoBorderLayout() {

            this.setBackground(Color.DARK_GRAY);

            // use BorderLayout
            this.setLayout(new BorderLayout());

            paintToolPanel = new ToolPanel();
            paintToolPanel.setPreferredSize(new Dimension(200,250));
            drawingPanel = new DrawingPanel();
            menuBar = new MenuBar();
            add(menuBar, "North");
            add(new JScrollPane(drawingPanel), "Center");

            sbar = new StatusbarView(drawingPanel.numstroke);
            sbar.repaint();
            add(sbar, "South");
            sbar.setMaximumSize(new Dimension(1800,50));

            Box box = new Box(BoxLayout.Y_AXIS);

            box.setPreferredSize(new Dimension(200, 250));
            box.add(paintToolPanel);
            box.add(Box.createVerticalStrut(20));

            colorPalette = new ColorPalette();
            colorPalette.setPreferredSize(new Dimension(200,300));
            box.add(colorPalette);

            Chooser = new JPanel();
            Chooser.setBackground(drawingPanel.brushColor);
            Chooser.setPreferredSize(new Dimension(200, 30));
            JLabel chooser_text = new JLabel("Chooser");
            chooser_text.setForeground(Color.WHITE);
            Font f = chooser_text.getFont();
            chooser_text.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            if(A_one.DemoBorderLayout.drawingPanel.shape_list.size() == 0){
                Chooser.setBackground(new Color(80, 37, 107));
            }
            Chooser.add(chooser_text);
            Chooser.addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent event) {
                    Chooser.setBackground(
                            JColorChooser.showDialog(A_one.DemoBorderLayout.drawingPanel, "Change Color",
                                    A_one.DemoBorderLayout.drawingPanel.brushColor));
                    Color selectedColor = Chooser.getBackground();
                    A_one.DemoBorderLayout.drawingPanel.currentTool.color = (Chooser.getBackground());     //change the ToolDetails color
                    A_one.DemoBorderLayout.drawingPanel.setCurrentColor(selectedColor);                                   //change the DrawingPanel brushColor
                    DemoBorderLayout.colorPalette.selectedColorDisplay.setBackground(selectedColor);
                }
            });


            Chooser.repaint();
            box.add(Chooser);

            ThPanel = new ThicknessPanel();
            box.add(ThPanel);

            box.add(Box.createVerticalStrut(10));
            this.add(box, BorderLayout.WEST);
            this.add(drawingPanel);
        }
    }
}


