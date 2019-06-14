import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

//Class used to display the stroke slider
public class ThicknessPanel extends JPanel{
    protected JSlider Slider;
    protected JPanel strokePanel;
    public JLabel label;
    public int val;

/**************************************************************************************************************
 ***************************************************CONSTRUCTOR************************************************
 **************************************************************************************************************/

    public ThicknessPanel() {

        this.setLayout(new FlowLayout());
        Slider = new JSlider(0, 0, 10, 2);
        Slider.setPreferredSize(new Dimension(200, 40));
        Slider.setMajorTickSpacing(2);
        Slider.setMinorTickSpacing(1);
        Slider.setValue(val);

        SliderListener slistener = new SliderListener();
        Slider.addChangeListener(slistener);
        Slider.revalidate();
        Slider.setBorder(BorderFactory.createLineBorder(Color.black));

        label = new JLabel("Current Thickness " + val, JLabel.CENTER);
        add(label);

        add(Slider);
        this.setPreferredSize(new Dimension(200, 100));
        repaint();

    }

    public class SliderListener implements ChangeListener
    {
        public void stateChanged(ChangeEvent event)
        {
            A_one.DemoBorderLayout.drawingPanel.currentTool.thickness = Slider.getValue();   //set new stroke
            A_one.DemoBorderLayout.drawingPanel.draw_thick = Slider.getValue();
            val = Slider.getValue();
            label.setText("Current Thickness " + val);
            repaint();
        }
    }
}