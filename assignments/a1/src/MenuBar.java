import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedWriter;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Field;
import java.lang.Object;

public class MenuBar extends JMenuBar {
    public JMenu file, About;
    public JMenuItem quit, newFile, openFile, saveFile, aboutinfo, savetext, loadtext, copyimage;
    public JFileChooser fileChooser = null;
    public boolean png_or_txt = false;


    public JFileChooser getFileChooser()
    {
        if (fileChooser ==null)
        {
            fileChooser = new JFileChooser();                        //create file chooser
            fileChooser.setFileFilter(new PNGFileFilter());         //set file extension to .png
        }
        return fileChooser;
    }

    public static BufferedImage getScreenShot(Component component)    //used to get the current image drawn on the screen
    {
        BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_RGB);
        component.paint(image.getGraphics());   // paints into image's Graphics
        return image;
    }

    private class MenuOptionsHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            if (event.getSource() == quit)          //if Exit application
            {
                System.exit(0);
            }
            if (event.getSource() == newFile)       //if New File
            {
                BufferedImage BufferImg = new BufferedImage(1024, 768, BufferedImage.TYPE_INT_ARGB);
                //create new BufferedImage
                A_one.DemoBorderLayout.drawingPanel.clearscreen(BufferImg);
                A_one.DemoBorderLayout.drawingPanel.setImage(BufferImg);
                A_one.DemoBorderLayout.drawingPanel.selected = false;
                A_one.DemoBorderLayout.drawingPanel.focused_shape = null;
                A_one.DemoBorderLayout.drawingPanel.isfill = false;
                A_one.DemoBorderLayout.drawingPanel.isremoving = false;
                A_one.DemoBorderLayout.drawingPanel.shape_list.clear();
                A_one.DemoBorderLayout.drawingPanel.repaintall();

            }
            if (event.getSource() == saveFile)      //if Save file
            {
                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showSaveDialog(A_one.DemoBorderLayout.drawingPanel);
                if (result==JFileChooser.APPROVE_OPTION )
                {
                    try
                    {
                        File selectedFile = jFileChooser.getSelectedFile();
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".png");      //get isSelected file
                        BufferedImage img = getScreenShot(A_one.DemoBorderLayout.drawingPanel);            //get current image screenshot
                        ImageIO.write(img, "png", selectedFile);                               //write the image to the isSelected file
                    } catch (IOException ioe)
                    {
                        JOptionPane.showMessageDialog(null, "Could not save the file");
                    }
                }
            }
            if (event.getSource() == openFile)
            {
                JFileChooser ch = getFileChooser();
                int result = ch.showOpenDialog(A_one.DemoBorderLayout.drawingPanel);
                if (result==JFileChooser.APPROVE_OPTION )
                {
                    try
                    {
                        A_one.DemoBorderLayout.drawingPanel.OSI = (ImageIO.read(ch.getSelectedFile()));
                        A_one.DemoBorderLayout.drawingPanel.repaint();
                    } catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Could not open file");
                    }
                }
            }
            if(event.getSource() == savetext){
                png_or_txt = true;
                JFileChooser jFileChooser = getFileChooser();                                            //open file chooser
                int result = jFileChooser.showSaveDialog(A_one.DemoBorderLayout.drawingPanel);
                if (result==JFileChooser.APPROVE_OPTION )
                {
                    try
                    {
                        File selectedFile = jFileChooser.getSelectedFile();
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                        FileOutputStream fos = new FileOutputStream(selectedFile);
                        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                        for(int i = 0; i < A_one.DemoBorderLayout.drawingPanel.shape_list.size();i++){
                            My_shape sp = A_one.DemoBorderLayout.drawingPanel.shape_list.get(i);
                            String tout = sp.getColour().getRed() + " " + sp.getColour().getGreen() + " "+
                                sp.getColour().getBlue() +" "+ sp.minX + " " + sp.minY + " " +
                                    sp.maxX + " " + sp.maxY + " " + sp.width + " " + sp.height + " " +
                                            sp.thinkness + " " + sp.Tooltype;
                            bw.write(tout);
                            bw.newLine();
                        }

                        bw.close();
                    } catch (IOException ioe)
                    {
                        JOptionPane.showMessageDialog(null, "Could not save the file");
                    }
                }
            }
            if (event.getSource() == loadtext)
            {
                png_or_txt = true;
                JFileChooser ch = getFileChooser();
                int result = ch.showOpenDialog(A_one.DemoBorderLayout.drawingPanel);
                if (result==JFileChooser.APPROVE_OPTION )
                {
                    try
                    {
                        BufferedReader br = new BufferedReader(new FileReader(ch.getSelectedFile()));
                        String line = br.readLine();
                        ArrayList<My_shape> my_shape_list = new ArrayList<>();
                        while(line != null){
                            My_shape ms = new My_shape();
                            String [] items = line.split(" ");
                            int r_val = Integer.parseInt(items[0]);
                            int g_val = Integer.parseInt(items[1]);
                            int b_val = Integer.parseInt(items[2]);
                            ms.color = new Color(r_val,g_val,b_val);
                            ms.minX = Integer.parseInt(items[3]);
                            ms.minY = Integer.parseInt(items[4]);
                            ms.maxX = Integer.parseInt(items[5]);
                            ms.maxY = Integer.parseInt(items[6]);
                            ms.width = Integer.parseInt(items[7]);
                            ms.height = Integer.parseInt(items[8]);
                            ms.thinkness = Integer.parseInt(items[9]);
                            ms.Tooltype = Integer.parseInt(items[10]);
                            my_shape_list.add(ms);
                            line = br.readLine();
                        }
                        br.close();
                        A_one.DemoBorderLayout.drawingPanel.shape_list = my_shape_list;
                        A_one.DemoBorderLayout.drawingPanel.repaintall();
                    } catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(null, "Could not open file");
                    }
                }
            }
            if (event.getSource() == aboutinfo)         //if About
            {
                JOptionPane.showMessageDialog(null, "Created by Winston Zhu(20601160) \n on June 9th 2019");
            }
            if (event.getSource() == copyimage){
                BufferedImage img = getScreenShot(A_one.DemoBorderLayout.drawingPanel);
                CopyImagetoClipBoard ccb = new CopyImagetoClipBoard(img);
            }
        }
    }


    public void WriteObjectToFile(Object serObj, String filepath) {
        try {

            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(serObj);
            objectOut.close();
            System.out.println("The Object was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static class PNGFileFilter extends FileFilter
    {
        public boolean accept(File file)             //filer files to display
        {
            return file.getName().toLowerCase().endsWith(".png") ||
                    file.getName().toLowerCase().endsWith(".txt") ||
                    file.isDirectory();
        }

        public String getDescription()
        {
            if(!A_one.DemoBorderLayout.menuBar.png_or_txt) {
                return "PNG image  (*.png) ";
            } else {
                return "TEXT image  (*.txt) ";
            }
        }
    }


    public MenuBar() {
        MenuOptionsHandler itemHandler = new MenuOptionsHandler();
        file = new JMenu("File");                                    //create the menu tabs and options
        About = new JMenu("About this App");
        newFile = new JMenuItem("New File");
        openFile = new JMenuItem("Load PNG File");
        saveFile = new JMenuItem("Save PNG File");
        savetext = new JMenuItem("Save TXT File");
        loadtext = new JMenuItem("Load TXT File");
        copyimage = new JMenuItem("Copy Image to Clipboard");
        quit = new JMenuItem("Exit");
        newFile.addActionListener(itemHandler);
        openFile.addActionListener(itemHandler);
        saveFile.addActionListener(itemHandler);
        quit.addActionListener(itemHandler);
        savetext.addActionListener(itemHandler);
        loadtext.addActionListener(itemHandler);
        copyimage.addActionListener(itemHandler);

        file.add(newFile);
        file.addSeparator();
        file.add(openFile);
        file.add(saveFile);
        file.addSeparator();
        file.add(savetext);
        file.add(loadtext);
        file.addSeparator();
        file.add(copyimage);
        file.add(quit);
        aboutinfo = new JMenuItem("About");
        aboutinfo.addActionListener(itemHandler);
        About.add(aboutinfo);
        add(file);
        add(About);
    }


}