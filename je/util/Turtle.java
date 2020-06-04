package je.util;
/*
 * Class    : Turtle
 * Copyright: (c) Gerhard Röhner
 *
 * History
 * --------
 * 3.00 2000.10.10 first version as java class
 * 3.01 2002.10.22 DrawDynamic 
 * 3.02 2010.10.11 sleepTime for paint delay
 * 3.03 2012.03.20 cartesian coordinate system, used with originX, originY, setOrigin
 * 3.04 2015.01.24 english version, integrated component
 */


import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;

/**
 * This class is a turtle component for simple graphic programming.
 *
 * @see <a href="http://www.javaeditor.org">
 * @author Gerhard Röhner
 * @version 3.03 20/03/2012
 */


public class Turtle extends Canvas {
  
  // -- private Attribute ------------------------------------------------------
  
  private BufferedImage myBufferedImage;
  private Graphics myBufferedGraphics;
  private Color foreground;
  private Color background;
  private static final double piDurch180 = Math.PI / 180;
  
  // -- public attributes -----------------------------------------------------
  
  /**
  * turtleX is the x-coordinate of the turtle
  * <P>
  * Example: <PRE>myTurtle.turtleX = 100;</PRE>
  */
  public double turtleX;
  
  /**
  * turtleY is the y-coordinate of the turtle.
  * <P>
  * Example: <PRE>myTurtle.turtleY = 200;</PRE>
  */
  public double turtleY;
  
  /**
  * turtleW is the current angle of the turtle in the range form 0 to 360 degrees.
  * <P>
  * Example: <PRE>myTurtle.turtleW = 180;</PRE>
  */
  public double turtleW;
  
  /**
  * originX is the x-position of the cartesian coodinate system within the turtle canvas.
  * <P>
  * Example: <PRE>myTurtle.originX = 0;</PRE>
  */
  public double originX;
  
  /**
  * originY is the y-position of the cartesian coodinate system within the turtle canvas.
  * <P>
  * Example: <PRE>myTurtle.originY = 100;</PRE>
  */
  public double originY;    
  
  /**
  * If drawDynamic is true you can watch the drawing of the turtle.
  * <P>
  * Example: <PRE>myTurtle.drawDynamic = true;</PRE>
  */
  public boolean drawDynamic;
  
  /**
  * For drawDynamic = true you set the delay in milliseconds for drawing.
  * <P>
  * Example: <PRE>myTurtle.sleepTime = 500;</PRE>
  */
  public int sleepTime = 0;
  
  // --- constructor -----------------------------------------------------------
  
  /**
  * Creates a Turtle with a canvas.
  * At the beginning the Turtle is placed in the middle of it's canvas.
  * The start angle is 0 degree, which means the Turtle looks to the right.
  * The background color is white, the drawing color black.
  * <P>
  * The turtle position can easily be changed by clicking into the canvas.
  * <P>
  * The size of the canvas can easily be changed with the mouse.
  * <P>
  * Example: <PRE>Turtle myTurtle = new Turtle();</PRE>
  */
  
  public Turtle() {
    myBufferedImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
    myBufferedGraphics = myBufferedImage.getGraphics();
    
    setForeground(Color.black);
    setBackground(Color.white);
    drawDynamic = true;
    
    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent evt) {
      turtleMouseClicked(evt);}});
    addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent evt) {
      turtleResized(evt);}});
    setOrigin(50, 50);
  }
  
  private void turtleMouseClicked(MouseEvent evt) {
    turtleX = evt.getX() - originX;
    turtleY = originY - evt.getY();
    turtleW = 0;
  }
  
  private void turtleResized(ComponentEvent evt) {
    int width = getWidth();
    int height = getHeight();
    
    BufferedImage newBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics newGraphics = newBufferedImage.getGraphics();
    newGraphics.setColor(background);
    newGraphics.fillRect(0, 0, width, height);
    newGraphics.setColor(foreground);
    newGraphics.drawImage(myBufferedImage, 0, 0, this);
    
    turtleX = 0;
    turtleY = 0;
    turtleW = 0;
    setOrigin(width / 2, height / 2);
    
    myBufferedImage = newBufferedImage;
    myBufferedGraphics = newGraphics;
  }
  
  public boolean isDoubleBuffered() {
    return true;
  }
  
  private void wTurtleMod360() {
    while (turtleW >= 360)
      turtleW = turtleW - 360;
    while (turtleW < 0)
      turtleW = turtleW + 360;
  }
  
  // --- Turtle coordinates ----------------------------------------------------
  
  /**
  * Sets turtleX to x.
  * <P>
  * Example: <PRE>myTurtle.setTurtleX(100);</PRE>
  */
  public void setTurtleX(double x) {
    turtleX = (int) x;
  }
  
  /**
  * Sets turtleY to y.
  * <P>
  * Example: <PRE>myTurtle.setTurtleY(-30);</PRE>
  */
  public void setTurtleY(double y) {
    turtleY = (int) y;
  }  
  
  /**
  * Sets turtleW to w.
  * <P>
  * Example: <PRE>myTurtle.setTurtleW(270);</PRE>
  */
  public void setTurtleW(double w) {
    turtleW = (int) w;
    wTurtleMod360();
  }   
  
  /**
  * Sets DrawDynamic to b.
  * <P>
  * Example: <PRE>myTurtle.setDrawDynamic(true);</PRE>
  */
  public void setDrawDynamic(boolean b) {
    drawDynamic = b;
  }   
  
  /**
  * Sets SleepTime to s.
  * <P>
  * Example: <PRE>myTurtle.setSleepTime(100);</PRE>
  */
  public void setSleepTime(int s) {
    sleepTime = s;
  }     
  
  // --- angle and turns -------------------------------------------------------
  
  /**
  * Turns the angle of the turtle relativ by the parameter angle. 
  * Positive values means a turn right, negative a turn left.
  * <P>
  * Example: <PRE>myTurtle.turn(-90);</PRE>
  */
  public void turn(double angle) {
    turtleW = turtleW + angle;
    wTurtleMod360();
  }
  
  /**
  * Sets the angle of the turtle absolute by the parameter angle.
  * The angle increases counterclockwise, therefore
  * right = 0, top = 90, left = 180, bottom = 270.
  * <P>
  * Example: <PRE>myTurtle.turnto(270);</PRE>
  */
  public void turnto(double angle) {
    turtleW = angle;
    wTurtleMod360();
  }
  
  // --- Drawing ---------------------------------------------------------------
  
  /**
  * The Turtle draws a line of the length specified in the current direction. 
  * <P>
  * Example: <PRE>myTurtle.draw(100);</PRE>
  */
  public void draw(double length) {
    drawto(turtleX + length * Math.cos(turtleW * piDurch180),
    turtleY + length * Math.sin(turtleW * piDurch180));
  }
  
  /**
  * The Turtle draws a line form the current position (turtleX, turtleY) to the
  * position (x, y) relativ to the cartesian coordinate system.
  * <P>
  * Example: <PRE>myTurtle.drawto(200, 300);</PRE>
  */
  public void drawto(double x, double y) {
    int x1 = (int) (originX + turtleX);
    int x2 = (int) (originX + x);
    int y1 = (int) (originY - turtleY);
    int y2 = (int) (originY - y);
    
    myBufferedGraphics.drawLine(x1, y1, x2, y2);
    if (drawDynamic){
      getGraphics().drawLine(x1, y1, x2, y2);
      try {
        Thread.currentThread().sleep(sleepTime);
      } catch(InterruptedException e) {
      }
    } else
    repaint();
    
    turtleX = x;
    turtleY = y;
  }
  
  // --- Moving ----------------------------------------------------------------
  
  /**
  * The Turtle moves without drawing the length specified in the current 
  * direction.
  * <P>
  * Example: <PRE>myTurtle.move(100);</PRE>
  */
  public void move(double length) {
    turtleX = turtleX + length * Math.cos (turtleW * piDurch180);
    turtleY = turtleY + length * Math.sin (turtleW * piDurch180);
  }
  
  /**
  * The Turtle moves without drawing to position (x, y) relativ to the
  * cartesian coordinate system.
  * <P>
  * Example: <PRE>myTurtle.moveto(100, 200);</PRE>
  */
  public void moveto(double x, double y) {
    turtleX = x;
    turtleY = y;
  }
  
  // --- set origin -----------------------------------------------------------
  
  /**
  * Sets the origin of the cartesian coordinate system within the turtle's canvas.
  * <P>
  * Example: <PRE>myTurtle.setOrigin(100, 200);</PRE>
  */
  public void setOrigin(double x, double y) {
    originX = x;
    originY = y;
  }
  
  // --- fore- and background color --------------------------------------------
  
  /**
  * Sets the drawing color of the Turtle to color c.
  * <P>
  * Example: <PRE>myTurtle.setForeground(Color.red);</PRE>
  */
  public void setForeground(Color c) {
    foreground = c;
    myBufferedGraphics.setColor(foreground);
    super.setForeground(foreground);
  }
  
  /**
  * Sets the canvas color of the Turtle to color c.
  * <P>
  * Example: <PRE>myTurtle.setBackground(Color.blue); </PRE>
  */
  public void setBackground(Color c) {
    background = c;
    myBufferedGraphics.setColor(background);
    myBufferedGraphics.fillRect(0, 0, getWidth(), getHeight());
    myBufferedGraphics.setColor(foreground);
    repaint();
  }
  
  /**
  * Clear the Turtle's canvas with the background color.
  * <P>
  * Example: <PRE>myTurtle.clear();</PRE>
  */
  public void clear() {
    setBackground(background);
    getGraphics().drawImage(myBufferedImage, 0, 0, this);
    repaint();
  }
  
  // --- Showing --------------------------------------------------------------
  
  /**
  * Shows the Turtle's canvas.
  */
  public void paint(Graphics g) {
    g.drawImage(myBufferedImage, 0, 0, this);
  }
  
  /**
  * Updates the Turtle's canvas.
  */
  public void update(Graphics g) {
    paint(g);
  }
  
}
