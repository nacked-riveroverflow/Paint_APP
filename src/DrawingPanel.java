import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.vecmath.*;

public class DrawingPanel extends JPanel implements MouseListener, MouseMotionListener,KeyListener
{
    /**************************************************************************************************************
     *****************************************************VARIABLES************************************************
     **************************************************************************************************************/
    public Image OSI ;
    int OSIWidth, OSIHeight;
    int dragingX, dragingY;
    private int mouseX, mouseY;         // the location of the mouse
    private int prevX, prevY;           // the previous location of the mouse
    private int startX, startY;
    public int distX,distY, distX_2, distY_2; // These are for translating lines
    public boolean isDrawing;          // this is set to true when the user is isDrawing.
    public Graphics2D dragGraphics;
    public Color brushColor;
    public Boolean mousePressed;
    public Tool currentTool;
    public static int numstroke;
    public static int draw_thick;
    public ArrayList<My_shape> shape_list;
    public My_shape focused_shape;
    public boolean selected;
    public boolean isremoving = false;
    public boolean isfill = false;
    Stroke dashed = new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
            0, new float[]{9}, 0);



    /**************************************************************************************************************
     *****************************************************CONSTRUCTOR***********************************************
     **************************************************************************************************************/
    public DrawingPanel()
    {
        this.setBackground(Color.WHITE);
        setPreferredSize(new Dimension(1024, 768));     //set size
        addMouseListener(this);                         //add mouse listener
        addMouseMotionListener(this);                   //add mouse motion listener
        addKeyListener(this);
        requestFocus();
        mousePressed = false;                           //set mousePressed to false
        brushColor = A_one.DemoBorderLayout.colorPalette.selectedColor;                       //set initial brush color
        currentTool = ToolButton.createTool(0);        //set initial painting tool
        brushColor = Color.black;
        currentTool.color = brushColor;
        draw_thick = currentTool.thickness;
        shape_list = new ArrayList<My_shape>();

    }



    /**********************************************Array Function*******************************************/
    public void removeshape(My_shape shape) {
        this.shape_list.remove(shape);
        numstroke--;
        selected = false;
        this.repaint(); // update Views? More?
    }



    /**************************************************************************************************************
     **************************************************DRAWING METHODS*********************************************
     **************************************************************************************************************/
    private void drawGraphics(Graphics2D graphics2D, Tool currentTool, int pointX1, int pointY1, int pointX2, int pointY2)
    {
        graphics2D.setColor(brushColor);
        if (currentTool.toolType == 3){ //Draw Line
            if(!isremoving) {
                graphics2D.setColor(brushColor);
            }else{
                graphics2D.setColor(getBackground());
            }
            graphics2D.setStroke(new BasicStroke(draw_thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            graphics2D.drawLine(pointX1, pointY1, pointX2, pointY2);        // draw the line between the two brushPoints.
            repaint();
            return;
        }
        int positionX, positionY;   // Top left corner of rectangle that contains the figure.
        int weight, height;         // Width and height of rectangle that contains the figure.
        if (pointX1 >= pointX2)
        {  // pointX2 is left edge
            positionX = pointX2;
            weight = pointX1 - pointX2;
        }
        else
        {   // pointX1 is left edge
            positionX = pointX1;
            weight = pointX2 - pointX1;
        }
        if (pointY1 >= pointY2)
        {  // pointY2 is top edge
            positionY = pointY2;
            height = pointY1 - pointY2;
        }
        else
        {   // pointY1 is top edge
            positionY = pointY1;
            height = pointY2 - pointY1;
        }

        if (currentTool.toolType == 5)            //if isSelected tool is RECTANGLE
        {
            if(!isremoving) {
                graphics2D.setColor(brushColor);
            }else{
                graphics2D.setColor(brushColor);
            }
            if(!isfill) {
                graphics2D.setStroke(new BasicStroke(draw_thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics2D.fillRect(positionX, positionY, weight, height);
            } else {
                /*The following is for fill */
                graphics2D.setColor(brushColor);
                graphics2D.setStroke(new BasicStroke(currentTool.thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics2D.fillRect(positionX, positionY, weight, height);
                My_shape line = new My_shape();
                line.color = brushColor;
                line.thinkness = draw_thick;
                line.minX = positionX;
                line.minY = positionY;
                line.maxX = positionX + weight;
                line.maxY = positionY + height;
                line.width = weight;
                line.height = height;
                line.isfilled = true;
                line.Tooltype = currentTool.toolType;
                line.isdragged = false;
                shape_list.add(line);
            }
            repaint();
            return;
        }

        if (currentTool.toolType == 4)
        {
            if(!isremoving) {
                graphics2D.setColor(brushColor);
            }else{
                graphics2D.setColor(getBackground());
            }

            if(!isfill) {
                graphics2D.setStroke(new BasicStroke(draw_thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics2D.fillOval(positionX, positionY, weight, height);
            }else {
                graphics2D.setColor(brushColor);
                graphics2D.setStroke(new BasicStroke(currentTool.thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                graphics2D.fillOval(positionX, positionY, weight, height);
                My_shape line = new My_shape();
                line.color = brushColor;
                line.minX = positionX;
                line.minY = positionY;
                line.maxX = positionX + weight;
                line.maxY = positionY + height;
                line.width = weight;
                line.height = height;
                line.isfilled = true;
                line.Tooltype = currentTool.toolType;
                line.isdragged = false;
                shape_list.add(line);

            }
            repaint();
            return;
        }
        if (currentTool.toolType == 2){
            //graphics2D.setColor(getBackground());
            /*
            ...
             */
        }
    }

    private Color getCurrentColor()
    {

            return brushColor;


    }


    /**Wrapper function for drawing graphics
     * Overrides the method in JComponent
     * Copies the off screen image to the screen after checking it exists
     * This Override function is partially copied from class example
     */
    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        Graphics2D g2 = (Graphics2D) graphics;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        createOffScreenImage();
        Graphics2D graphics2D = (Graphics2D)graphics;
        graphics.drawImage(OSI, 0, 0, this);
        if (isDrawing &&
                currentTool.toolType != 0&&
                currentTool.toolType != 1 &&
                currentTool.toolType != 2)
        {
            graphics.setColor(brushColor);                                             //set color
            drawGraphics(graphics2D, currentTool, startX, startY, mouseX, mouseY);
        } else if (currentTool.toolType == 2){
            graphics.setColor(getBackground());
            drawGraphics(graphics2D, currentTool, startX, startY, mouseX, mouseY);
        }
    }

    public void setCurrentColor(Color clr)         //set the isSelected color to the toolDetails class
    {
        brushColor = clr;
        currentTool.color = clr;
    }

    /**
    Every time when adding a new shape(effective event) to the canvas, we will add the shape to the array and increase
    the counter
     We will want this to show up in the bottom bar
     */
    public void addStroke(Shape shape) {
        numstroke++;
    }


/**************************************************************************************************************
 **************************************************IMAGE IO FUNCTION************************************************
 **************************************************************************************************************/

    public void clearscreen(BufferedImage bfg){
        Graphics2D gr = bfg.createGraphics();
        gr.setColor(Color.DARK_GRAY);
        gr.dispose();
        repaint();
    }

    public void setImage(BufferedImage image)  //used by load file option to set the OSI to the given image
    {
        int w = image.getWidth();
        int h = image.getHeight();
        OSI = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
        OSI = createImage(w, h);
        OSIWidth = getSize().width;
        OSIHeight = getSize().height;
        repaint();
        Graphics graphics = OSI.getGraphics();  // Graphics context for isDrawing to OSI.
        graphics.setColor(getBackground());
        graphics.fillRect(0, 0, OSIWidth, OSIHeight);
        graphics.dispose();
    }

    /**
     * Method used to create the off-screen image
     * the method will create a new off-screen image if the size of the panel changes
     */
    private void createOffScreenImage()
    {
        if (OSI == null || OSIWidth != getSize().width || OSIHeight != getSize().height) {
            OSI = null;
            OSI = createImage(getSize().width, getSize().height);
            OSIWidth = getSize().width;
            OSIHeight = getSize().height;
            Graphics graphics = OSI.getGraphics();
            graphics.setColor(getBackground());
            graphics.fillRect(0, 0, OSIWidth, OSIHeight);
            graphics.dispose();
        }
    }

/**************************************************************************************************************
 **************************************************Graphe Refreshment Functions************************************************
 **************************************************************************************************************/

    public void repaintall() {
        // clear everything we have before
        Graphics2D dragGraphics = (Graphics2D) OSI.getGraphics();
        dragGraphics.setColor(getBackground());
        dragGraphics.fillRect(0, 0, OSIWidth, OSIHeight);
        for (My_shape ms: shape_list) {
            boolean flag = false;
            dragGraphics.setColor(ms.color);
            if(selected) {
                if (focused_shape.minX == ms.minX && focused_shape.maxX == ms.maxX) {
                    dragGraphics.setStroke(dashed);
                    flag = true;
                } else {
                    dragGraphics.setStroke(new BasicStroke(ms.thinkness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                }
            }else {
                dragGraphics.setStroke(new BasicStroke(ms.thinkness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            }
            switch(ms.Tooltype) {
                case 5:
                    //Rectangle2D rect = (Rectangle2D) ms;
                    dragGraphics.fillRect(ms.minX, ms.minY, ms.width, ms.height);
                    dragGraphics.setColor(Color.DARK_GRAY);
                    if(flag) {
                        dragGraphics.drawRect(ms.minX, ms.minY, ms.width, ms.height);
                    }
                    break;
                case 4:
                    dragGraphics.fillOval(ms.minX, ms.minY, ms.width, ms.height);
                    dragGraphics.setColor(Color.DARK_GRAY);
                    if(flag) {
                        dragGraphics.drawOval(ms.minX, ms.minY, ms.width, ms.height);
                    }
                    break;
                case 3:
                    dragGraphics.drawLine(ms.maxX,ms.maxY,ms.minX,ms.minY);
                    break;
            }
        }
        repaint();
    }

    /**
     This function is partially referred to the class example and stackoverflow
     */
    public Point2d closestPoint(double x, double y, Point2d P0, Point2d P1) {
        Point2d M = new Point2d(x, y);

        Vector2d v = new Vector2d();
        v.sub(P1,P0);
        if (v.lengthSquared() < 0.5)
            return P0;

        Vector2d u = new Vector2d();
        u.sub(M,P0); // u = M - P1
        double s = u.dot(v) / v.dot(v);

        if (s < 0)
            return P0;
        else if (s > 1)
            return P1;
        else {
            Point2d I = P0;
            Vector2d w = new Vector2d();
            w.scale(s, v); // w = s * v
            I.add(w); // I = P1 + w
            return I;
        }
    }

    private void repaintRectangle(int pointX1, int pointY1, int pointX2, int pointY2)
    {
        int x, y;  // top left corner of rectangle that contains the figure
        int w, h;  // width and height of rectangle that contains the figure
        if (pointX2 >= pointX1)
        {   // pointX1 is left edge
            x = pointX1;
            w = pointX2 - pointX1;
        }
        else
        {   // pointX2 is left edge
            x = pointX2;
            w = pointX1 - pointX2;
        }
        if (pointY2 >= pointY1)
        {   // pointY1 is top edge
            y = pointY1;
            h = pointY2 - pointY1;
        }
        else
        {   // pointY2 is top edge.
            y = pointY2;
            h = pointY1 - pointY2;
        }
        repaint(x, y, w+1, h+1);      //add 1 pixel border along the right and bottom edges to allow for a pen overhang when isDrawing a line
    }


    public void moveshape(int x, int y){
        focused_shape.isdragged = true;

        if(selected == false){
            return;
        }
        focused_shape.minX = x;
        focused_shape.minY = y;
        if(focused_shape.Tooltype == 3) {
            focused_shape.maxX = x + distX + distX_2;
            focused_shape.maxY = y + distY + distY_2;
        }
        System.out.println("The ACTUALLLL x coord is " + focused_shape.minX + ". The y coord is " + focused_shape.minY);
        repaintall();
    }

/**************************************************************************************************************
 **************************************************MOUSE EVENTS************************************************
 **************************************************************************************************************/

    public void mousePressed(MouseEvent evt)
    {
        if (isDrawing)
            return;
        if(selected && focused_shape != null){
            dragingX = evt.getX();
            dragingY = evt.getY();
            distX =  dragingX - focused_shape.minX;
            distY = dragingY -focused_shape.minY;
            distX_2 = focused_shape.maxX - dragingX;
            distY_2 = focused_shape.maxY -dragingY;
        }

        prevX = startX = evt.getX();    // save mouse coordinates.
        prevY = startY = evt.getY();

        brushColor = getCurrentColor();                 //get current color
        dragGraphics = (Graphics2D) OSI.getGraphics();  //convert Graphics

        isDrawing = true;                                 //start isDrawing
    }



    public void mouseReleased(MouseEvent evt)
    {
        if (!isDrawing)
            return;             //return if the user isn't isDrawing.
        isDrawing = false;        //set isDrawing to false
        mouseX = evt.getX();    //save mouse coordinates
        mouseY = evt.getY();

        if (currentTool.toolType != 0 && currentTool.toolType != 2 &&
                currentTool.toolType != 1 )
        {
            repaintRectangle(startX, startY, prevX, prevY);
            if (mouseX != startX && mouseY != startY) {
                // Draw the shape only if both its height and width are non-zero.
                drawGraphics(dragGraphics, currentTool, startX, startY, mouseX, mouseY);
                repaintRectangle(startX, startY, mouseX, mouseY);
                if(!isremoving && !isfill) {
                    My_shape line = new My_shape();
                    line.color = brushColor;
                    if(currentTool.toolType == 4 || currentTool.toolType == 5) {
                        line.minX = Math.min(startX, mouseX);
                        line.minY = Math.min(startY, mouseY);
                        line.maxX = Math.max(startX, mouseX);
                        line.maxY = Math.max(startY, mouseY);
                    } else{
                        line.minX = startX;
                        line.minY = startY;
                        line.maxX = mouseX;
                        line.maxY = mouseY;
                    }
                    line.width = Math.abs(mouseX - startX);
                    line.height = Math.abs(mouseY - startY);
                    line.Tooltype = currentTool.toolType;
                    line.isdragged = false;
                    line.thinkness = currentTool.thickness;
                    shape_list.add(line);
                }

                numstroke++;
                A_one.DemoBorderLayout.sbar = new StatusbarView(numstroke);
                System.out.println("The Canvas now has " + shape_list.size());
                A_one.DemoBorderLayout.sbar.repaint();
                A_one.DemoBorderLayout.drawingPanel.repaint();
            }
        }
        /* If we are deleting shape */
        if (currentTool.toolType == 2){
            for(int i = 0; i < shape_list.size();i++){
                My_shape sp = shape_list.get(i);
                if(sp.Tooltype == 3){// Line
                    Point2d M1 = new Point2d(mouseX,mouseY);
                    Point2d close_p = closestPoint(mouseX,mouseY,
                            new Point2d(sp.minX,sp.minY), new Point2d(sp.maxX,sp.maxY));
                    double dist = M1.distance(close_p);
                    if(dist <= 5){
                        currentTool.toolType = 3;
                        isremoving = true;
                        if(sp.isfilled){
                            isfill = true;
                        }
//                        int prev_think = currentTool.thickness;
//                        currentTool.thickness = sp.thinkness;
//                        drawGraphics(dragGraphics, currentTool, sp.minX, sp.minY, sp.maxX, sp.maxY);
//                        currentTool.thickness = prev_think;
                        currentTool.toolType = 2;
//                        brushColor = Color.black;
                        isremoving = false;
                        isfill = false;
                        removeshape(sp);
                        repaintall();
                    }
                } else if (sp.Tooltype == 4 || sp.Tooltype == 5){
                    if((mouseX - sp.minX)*(mouseX - sp.maxX) <= 0 && (mouseY - sp.minY)*(mouseY - sp.maxY) <= 0){
                        currentTool.toolType = sp.Tooltype;
                        isremoving = true;
                        if(selected && focused_shape == sp){
//                            drawGraphics(dragGraphics, currentTool, sp.minX - 5, sp.minY -5,
//                                    sp.maxX+5, sp.maxY+5 );
                            selected = false;
                            focused_shape = null;
                        }
                            currentTool.toolType = 2;
                            brushColor = A_one.DemoBorderLayout.colorPalette.selectedColorDisplay.getBackground();
                            isfill = false;
                            isremoving = false;
                            removeshape(sp);
                            repaintall();

                    }
                }
                repaint();
            }
        }
        /* If we are filling any shape */
        else if (currentTool.toolType == 1){
            for(int i = 0; i < shape_list.size();i++){
                My_shape sp = shape_list.get(i);
                if(sp.Tooltype == 4 | sp.Tooltype == 5){
                    if((mouseX - sp.minX)*(mouseX - sp.maxX) <= 0 && (mouseY - sp.minY)*(mouseY - sp.maxY) <= 0) {
                        isfill = true;
                        currentTool.toolType = sp.Tooltype;
                        //brushColor = sp.color;
                        drawGraphics(dragGraphics, currentTool, sp.minX, sp.minY, sp.maxX, sp.maxY);
                        currentTool.toolType = 1;
                        //brushColor = Color.black;
                        isfill = false;
                        removeshape(sp);
                    }
                }

            }
        }
        /* If we are selecting object */
        else if (currentTool.toolType == 0){
            for(int i = 0; i < shape_list.size();i++) {

                My_shape sp = shape_list.get(i);
                if (sp.Tooltype == 4 | sp.Tooltype == 5) {
                    if ((mouseX - sp.minX) * (mouseX - sp.maxX) <= 0 && (mouseY - sp.minY) * (mouseY - sp.maxY) <= 0) {
                        selected = true;
                        My_shape sp_copy = sp;
                        int index = shape_list.indexOf(sp);
                        shape_list.remove(sp);
                        shape_list.add(sp_copy);
                        focused_shape = sp_copy;
                        A_one.DemoBorderLayout.colorPalette.selectedColorDisplay.setBackground(sp.color);
                        A_one.DemoBorderLayout.ThPanel.Slider.setValue(10);
                        A_one.DemoBorderLayout.ThPanel.repaint();
                        repaintall();
                        //selected = false;
                        //removeshape(sp);
                        //focused_shape = null;
                        break;
                    }
                } else if (sp.Tooltype == 3){
                    Point2d M1 = new Point2d(mouseX,mouseY);
                    Point2d close_p = closestPoint(mouseX,mouseY,
                            new Point2d(sp.minX,sp.minY), new Point2d(sp.maxX,sp.maxY));
                    double dist = M1.distance(close_p);
                    if(dist <= 5){
                        A_one.DemoBorderLayout.colorPalette.selectedColorDisplay.setBackground(sp.color);
                        A_one.DemoBorderLayout.ThPanel.Slider.setValue(sp.thinkness);
                        A_one.DemoBorderLayout.ThPanel.repaint();
                        selected = true;
                        My_shape sp_copy = sp;
                        int index = shape_list.indexOf(sp);
                        shape_list.remove(sp);
                        shape_list.add(sp_copy);
                        focused_shape = sp_copy;
                        repaintall();
                    }
                }

            }

        }
        dragGraphics.dispose();
        dragGraphics = null;
        requestFocus();
    }

    public void mouseDragged(MouseEvent evt) {
        /* Check if we are in the drawing mode */
        if (!isDrawing)
            return;
        if (isremoving)
            return;
        if (isfill)
            return;

        mouseX = evt.getX();   // x-coordinate of mouse.
        mouseY = evt.getY();   // y=coordinate of mouse.

        if(selected && currentTool.toolType == 0){
            System.out.println("The First x coord is " + dragingX + ". The y coord is " + dragingY);
            moveshape(mouseX - distX, mouseY - distY);
            return;

        }

        if (currentTool.toolType == 1) {
            drawGraphics(dragGraphics, ToolButton.createTool(1), prevX, prevY, mouseX, mouseY);
            repaintRectangle(prevX, prevY, mouseX, mouseY);
        } else {
            repaintRectangle(startX, startY, prevX, prevY);
            repaintRectangle(startX, startY, mouseX, mouseY);
        }

        prevX = mouseX;  // Save coordinates for the next call to mouseDragged or mouseReleased.
        prevY = mouseY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {
    }
    /**************************************************************************************************************
     **************************************************Key EVENTS************************************************
     **************************************************************************************************************/

    @Override
    public void keyPressed(KeyEvent e) {
        dragGraphics = (Graphics2D) OSI.getGraphics();  //convert Graphics
    }

    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            focused_shape = null;
            selected = false;
            repaintall();
        }
    }
}