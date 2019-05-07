import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Main extends JFrame {

    public Main() {
        // add a text field
        JTextArea text = new JTextArea("Hello Java!");
        text.setEditable(false);
        add(text);

        // make frame visible
        setTitle("Getting Started");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        JFrame frame = new Main();
        frame.setVisible(true);
    }
}