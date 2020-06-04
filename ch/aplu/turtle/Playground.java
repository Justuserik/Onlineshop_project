// Playground.java

// Copyright 2002 Regula Hoefer-Isenegger
// Major code modifications by Aegidius Pluess
// Adaption for the Java-Editor by Gerhard Röhner
//
// This file is part of The Java Turtle package (TJT)
//
// TJT is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2 of the License, or
// (at your option) any later version.
//
// TJT is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with The Java Turtle Package; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

package ch.aplu.turtle;

import ch.aplu.turtle.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.print.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.font.*;

/**
       A <code>Playground</code> is the <code>Turtle</code>'s home, i.e. the <code>Turtle</code> lives
       and moves in the <code>Playground</code>.

       The<code>Playground</code> is responsible for interpreting angle and position of the
       <code>Turtle</code> and for choosing the correct turtle image and putting it on the right
       spot of the Playground. This means: e.g. whenever you wish to switch the x- and y-axis, you
       should do it in this class, and not in the <code>Turtle</code> class.

       @author <a href="mailto:regula@hoefer.ch">Regula Hoefer-Isenegger</a>
       @version 0.1.1
*/

public class Playground extends JPanel implements Printable {
  
  /** Hold the <code>Turtle</code>s of this Playground. */
  private Vector<Turtle> turtles;
  
  /** Hold the offscreen buffer and graphics context
  * where Turtle traces are drawn.
  */
  private BufferedImage traceBuffer = null;
  protected Graphics2D traceG2D = null;
  private Dimension playgroundSize;
  
  /** Hold the offscreen buffer and graphics context
  * of the Turtles images.
  */
  private BufferedImage turtleBuffer = null;
  protected Graphics2D turtleG2D = null;
  
  /** Flag to tell whether we have at least one Turtle shown. */
  private boolean isTurtleVisible = false;
  
  /** Flag to tell whether we use automatic repainting */
  private boolean isRepainting = true;
  
  /** The default background color.
  */
  protected static Color DEFAULT_BACKGROUND_COLOR = Color.white;
  
  private double printerScale = 1;  // Default printer scaling
  private TPrintable traceCanvas;   // Store ref to user class
  private Graphics2D printerG2D = null;
  private boolean isPrintScreen = false; // Indicate we are printing the playground
  private double printerScaleFactor = 1.1;  // Magnification factor for printer
  
  /**
  * originX is the x-position of the cartesian coodinate system within the playground.
  */
  public int originX;
  
  /**
  * originY is the y-position of the cartesian coodinate system within the playground.
  */
  public int originY;   
  
  private static Color[] ColorArray = {Color.cyan, Color.red, Color.green, Color.blue, Color.yellow,  
  Color.lightGray, Color.magenta, Color.orange, Color.pink, Color.black, Color.gray
  };    
  
  /**
  *  Create a Playground with default background color.
  * e.g. creates a new vector (which holds the
  * <code>Turtle</code>s), 
  */
  public Playground() {
    turtles = new Vector<Turtle>();
    setDoubleBuffered(false);
    setBackground(DEFAULT_BACKGROUND_COLOR);
    initBuffers(new Dimension(100, 100));
  }
  
  /**
  * Initializes the offscreen buffers and sets the size.
  */
  protected void initBuffers(Dimension size) {
    Color bkColor = getBackground();
    playgroundSize = size;
    traceBuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
    traceG2D = traceBuffer.createGraphics();
    traceG2D.setColor(bkColor);
    traceG2D.fillRect(0, 0, size.width, size.height);
    traceG2D.setBackground(bkColor);
    
    turtleBuffer = new BufferedImage(size.width, size.height, BufferedImage.TYPE_INT_ARGB);
    turtleG2D = turtleBuffer.createGraphics();
    turtleG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    originX = size.width/2;
    originY = size.height/2;
  }
  
  /** Add a new <code>Turtle</code> to the Playground.
  */
  public void add(Turtle turtle) {
    turtles.add(turtle);
    turtle.setPlayground(this);
    int i = turtles.size();
    while (i > 10) { 
      i = i - 10;
    } // end of while
    turtle.init(ColorArray[i-1]);
    toTop(turtle);
  }
  
  public void setBounds(int x, int y, int width, int height) {
    super.setBounds(x, y, width, height);
    initBuffers(new Dimension(width, height));
  }  
  
  /** Remove a <code>Turtle</code> from the Playground.
  */
  public void remove(Turtle turtle) {
    turtles.remove(turtle);
  }
  
  /** Tell current number of <code>Turtle</code>s in this Playground.
  */
  public int countTurtles() {
    return turtles.size();
  }
  
  /** Return the <code>Turtle</code> at index <code>index</code>.
  */
  public Turtle getTurtle(int index) {
    return turtles.elementAt(index);
  }
  
  /** Move the given <code>Turtle</code> above all the others, then
  paints all turtles.
  @see #toTop
  */
  public void paintTurtles(Turtle turtle) {
    toTop(turtle);
    paintTurtles();
  }
  
  /** Paint all turtles (calling paintComponent())
  */
  public void paintTurtles() {
    isTurtleVisible = false;
    Graphics2D g2D = getTurtleG2D();
    for (int i = 0; i < countTurtles(); i++) {
      Turtle aTurtle = getTurtle(i);
      if (!aTurtle.isHidden()) {
        paintTurtle(aTurtle);
      }
    }
    
    // This is the main repaint call, when the turtle is
    // moving (even when all turtles are hidden).
    // Strange behaviour an slow Mac machines (pre J2SE 1.4 version):
    // It happens that some turtle images are not completely redrawn.
    // This is probably due to an improper handling of fast multiple repaint requests.
    // Workaround: we wait a small amount of time (and give the thread away)
    // (No visible slow down on new machines.)
    
    paintPlayground();
    if (printerG2D == null) {
      //if (isRepainting)
      //repaint();
      //paintPlayground();
    }
    
    if (isTurtleVisible) {
      try {
        Thread.currentThread().sleep(10);
      }
      catch (Exception e) {}
    }
  }
  
  
  /** Paint the given <code>Turtle</code>.
  * ( no repaint() )
  */
  public void paintTurtle(Turtle turtle) {
    if (turtleBuffer == null){
      turtleBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      turtleG2D = turtleBuffer.createGraphics();
    }
    Graphics2D turtleGraphics = getTurtleG2D();
    turtle.getTurtleRenderer().paint(turtle._getX(), turtle._getY(), turtleGraphics);
    isTurtleVisible = true;
  }
  
  /** Put an image of the given <code>Turtle</code> in turtle buffer.
  */
  protected void stampTurtle(Turtle turtle) {
    turtle.clone();
    isTurtleVisible = true;
    if (printerG2D == null)
    repaint();
  }
  
  /** Draw a line from the point <code>(x0, y0)</code> to <code>(x1, y1)</code>
  with the color of the given <code>Pen</code>.
  */
  protected void lineTo(double x0, double y0, double x1, double y1, Pen pen) {
    int ix0 = (int)Math.round(x0);
    int iy0 = (int)Math.round(y0);
    int ix1 = (int)Math.round(x1);
    int iy1 = (int)Math.round(y1);
    Color color = pen.getColor();
    
    if (traceBuffer == null) {
      traceBuffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
      traceG2D = traceBuffer.createGraphics();
    }
    Graphics2D traceG2D = getTraceG2D();
    traceG2D.setColor(color);
    traceG2D.setStroke(pen.getStroke());
    traceG2D.drawLine(ix0, iy0, ix1, iy1);
    if (printerG2D != null)
    printerG2D.drawLine(ix0, iy0, ix1, iy1);
  }
  
  /** A class for convenience. */
  protected class Point extends java.awt.Point {
    Point(int x, int y) {
      super(x, y);
    }
    Point() {
      super();
    }
    Point(Point p) {
      super(p.x, p.y);
    }
    
    /** Get a new Point with coordinates (this.x+p.x, this.y+p.y).
    */
    protected Point add(Point p) {
      return new Point(this.x+p.x, this.y+p.y);
    }
    
    /** Translate by the amounts dx = p.x, dy = p.y. */
    protected void translate(Point p) {
      translate(p.x, p.y);
    }
    
    public String toString() {
      return "(" + x + "," + y + ")";
    }
  }
  
  /** Fill a region.
  The region is defined by the <code>Turtle</code>s actual position and
  is bounded by any other color than the give background color.
  */
  public void fill(Turtle t, Color bgColor) {
    final Point[] diff = { new Point(0,-1), new Point(-1,0), new Point(1,0),  new Point(0,1)};
    final int N=0;
    final int W=1;
    final int E=2;
    final int S=3;
    
    int bgcolor = bgColor.getRGB();
    int fillColor = t.getPen().getFillColor().getRGB();
    Vector list = new Vector();
    Point2D.Double p1 = toScreenCoords(t.getPos());
    int startX = (int)Math.round(p1.getX());
    int startY = (int)Math.round(p1.getY());
    Point p = new Point(startX, startY);
    if (traceBuffer.getRGB(startX, startY) == bgcolor) {
      traceBuffer.setRGB(startX, startY, fillColor);
      list.addElement(new Point(startX, startY));
      int d = N;
      int back;
      while (list.size() > 0) {
        while (d <= S) { // forward
          Point tmp = p.add(diff[d]);
          try {
            if (traceBuffer.getRGB(tmp.x, tmp.y) == bgcolor) {
              p.translate(diff[d]);
              traceBuffer.setRGB(p.x, p.y, fillColor);
              if (printerG2D != null)
              {
                printerG2D.setColor(t.getPen().getFillColor());
                //                 printerG2D.drawLine(p.x,p.y, p.x, p.y);
                BasicStroke stroke = new BasicStroke(2);
                printerG2D.setStroke(stroke);
                Line2D line = new Line2D.Double(p.x, p.y, p.x, p.y);
                printerG2D.draw(line);
              }
              list.addElement(new Integer(d));
              d=N;
            }
            else {
              d++;
            }
          }
          catch (ArrayIndexOutOfBoundsException e) {
            d++;
          }
        }
        Object obj = list.remove(list.size()-1);
        try {
          d=((Integer)obj).intValue(); // last element
          back = S - d;
          p.translate(diff[back]);
        }
        catch (ClassCastException e) {
          // the first (zeroest) element in list is the start-point
          // just do nothing with it
        }
      }
    }
    traceG2D.drawLine(0, 0, 0, 0); // Workaround because on Mac the trace buffer is not drawn without this
    if (printerG2D == null)
    repaint();
  }
  
  /**
  * Clear the playground with given color.
  */
  public void clear(Color color) {
    traceG2D.setColor(color);
    traceG2D.fillRect(0, 0, getWidth(), getHeight());
    turtleG2D.setColor(color);
    turtleG2D.fillRect(0, 0, getWidth(), getHeight());
    isTurtleVisible = true;
    if (printerG2D == null)
    repaint();
  }
  
  /**
  * Clear playground.
  */
  public void clear() {
    clear(getBackground());
  }
  
  /** Paint the Playground.
  just a method for convenience.
  */
  public void paintComponent() {
    paintComponent(getGraphics());
  }
  
  /** Draw the trace and turtle buffers.
  */
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2D = (Graphics2D)g;
    g2D.drawImage(traceBuffer, 0, 0, this);
    if (isTurtleVisible)
    g2D.drawImage(turtleBuffer, 0, 0, this);
  }
  
  public void paintPlayground() {
    Graphics g = getGraphics();
    if (g != null) {
      super.paintComponent(g);
      Graphics2D g2D = (Graphics2D)g;
      g2D.drawImage(traceBuffer, 0, 0, this);
      if (isTurtleVisible)
      g2D.drawImage(turtleBuffer, 0, 0, this);
      
    } // end of if
  }
  
  /** Remove all turtles from the turtle buffer.
  */
  public void clearTurtles() {
    for (int i = 0; i < countTurtles(); i++) {
      Turtle turtle = getTurtle(i);
      clearTurtle(turtle);
    }
  }
  
  /** Remove the given turtle from the turtle buffer.
  Override this method if you have added a new behaviour (like
  wrap or clip) to the turtle.
  */
  public void clearTurtle(Turtle turtle) {
    if(turtle != null){
      if (!turtle.isHidden()) {
        if(turtle.isClip()){
          clearClipTurtle(turtle);
        }
        else if(turtle.isWrap()){
          clearWrapTurtle(turtle);
        }
      }
    }
  }
  
  /** This method is called when the given <code>Turtle</code> is in wrap mode.
  
  @see ch.aplu.turtle.Turtle#wrap
  */
  protected void clearWrapTurtle(Turtle turtle){
    clearWrapTurtle(turtle, turtleBuffer);
  }
  
  /** Here the actual clearing of a <code>Turtle</code> in wrap mode from the
  given image is performed.
  */
  protected void clearWrapTurtle(Turtle turtle, Image im){
    Rectangle bounds = getBounds(turtle);
    int pWidth = getWidth();
    int pHeight = getHeight();
    int x = bounds.x;
    int y = bounds.y;
    while (x > pWidth){
      x -= pWidth;
    }
    while (x < 0){
      x += pWidth;
    }
    while (y > pHeight){
      y -= pHeight;
    }
    while (y < 0){
      y += pHeight;
    }
    x = x % pWidth;
    y = y % pHeight;
    toAlphaNull(im, new Rectangle(x, y, bounds.width, bounds.height)); // OK
    boolean right = (x + bounds.width > getWidth());
    boolean bottom = (y + bounds.height > getHeight());
    if (right) {
      toAlphaNull(im, new Rectangle(x-pWidth, y, bounds.width, bounds.height));
    }
    if (bottom) {
      toAlphaNull(im, new Rectangle(x, y-pHeight, bounds.width, bounds.height));
    }
    if (right && bottom) {
      toAlphaNull(im, new Rectangle(x-pWidth, y-pHeight, bounds.width, bounds.height));
    }
  }
  
  /** Copy and translate a given Rectangle.
  */
  private Rectangle copyAndTranslate(Rectangle rect, int dx, int dy) {
    return new Rectangle(rect.x+dx, rect.y+dy,
    rect.width, rect.height);
  }
  
  /** This method is called when the given <code>Turtle</code> is in clip mode.
  
  @see ch.aplu.turtle.Turtle#clip
  */
  protected void clearClipTurtle(Turtle turtle) {
    clearClipTurtle(turtle, turtleBuffer);
  }
  
  /** Here the actual clearing of a <code>Turtle</code> in clip mode from the
  given image is performed.
  */
  protected void clearClipTurtle(Turtle turtle, Image im) {
    Rectangle bounds = getBounds(turtle);
    toAlphaNull(im, bounds);
  }
  
  /** Set the alpha channel of all pixels in the given image
  in the given Rectangle to zero (i.e. totally transparent).
  
  This method is used byte the clearXXXTurtle methods.
  */
  private void toAlphaNull(Image im, Rectangle rect) {
    Rectangle rim = new Rectangle(0, 0, im.getWidth(this), im.getHeight(this));
    Rectangle r = new Rectangle();
    if (rect.intersects(rim)) {
      r=rect.intersection(rim);
    }
    int size = r.width*r.height;
    float[] alphachannel = new float[r.width*r.height];
    ((BufferedImage)im).getAlphaRaster().setPixels(r.x, r.y, r.width, r.height, alphachannel);
  }
  
  /** Puts a Turtle above all others.
  */
  public Turtle toTop(Turtle turtle) {
    if (turtles.removeElement(turtle)) {
      turtles.add(turtle);
    }
    return turtle;
  }
  /** Put a Turtle below all others.
  */
  public Turtle toBottom(Turtle turtle) {
    if (turtles.removeElement(turtle)) {
      turtles.add(0,turtle);
    }
    return turtle;
  }
  
  /** Calculate the screen coordinates of the given point.
  */
  public Point2D.Double toScreenCoords(Point2D.Double p) {
    return internalToScreenCoords(p.x, p.y);
  }
  
  /** Calculate the screen coordinates of the given point coordinates.
  */
  public Point2D.Double toScreenCoords(double x, double y) {
    return internalToScreenCoords(x, y);
  }
  
  protected Point2D.Double internalToScreenCoords(double x, double y) {
    // reflect at x-axis, then translate to center of Playground
    // pixel coordinates coorespond to turtle coordinates, only translation needed
    double newX = originX + x;
    double newY = originY - y;
    return new Point2D.Double(newX, newY);
  }
  
  /** Calculate the turtle coordinates of the given screen coordinates.
  */
  public Point2D.Double toTurtleCoords(double x, double y) {
    // pixel coordinates coorespond to turtle coordinates, only translation needed
    double newX = x - originX;
    double newY = originY - y;
    return new Point2D.Double(newX, newY);
  }
  
  /** Calculate the turtle coordinates of the given screen point.
  */
  public Point2D.Double toTurtleCoords(Point2D.Double p) {
    return toTurtleCoords(p.x, p.y);
  }
  
  /** Calculate the screen angle.
  I.e. the interpretation of angle.
  @param radians The angle in radians.
  */
  double toScreenAngle(double radians) {
    double sa = radians;
    if (sa < Math.PI/2){
      sa += 2*Math.PI;
    }
    sa -= Math.PI/2;
    if (sa != 0) {
      sa = Math.PI*2 - sa;
    }
    return sa;
  }
  
  /** Calculate the bounds of the <code>Turtle</code>s picture on the screen.
  */
  protected Rectangle getBounds(Turtle turtle) {
    Rectangle bounds = turtle.getBounds();
    Point2D.Double tmp = toScreenCoords(new Point2D.Double(bounds.getX(), bounds.getY()));
    bounds.setRect(tmp.x-2, tmp.y-2, bounds.width+4, bounds.height+4);
    return bounds;
  }
  
  /** Return the graphics context of the turtle buffer.
  */
  public Graphics2D getTurtleG2D() {
    return turtleG2D;
  }
  
  /** Return the image of the turtle buffer.
  */
  public BufferedImage getTurtleBuffer() {
    return turtleBuffer;
  }
  
  /**  Return the graphics context of the trace buffer.
  */
  public Graphics2D getTraceG2D() {
    return traceG2D;
  }
  
  /**  Return the graphics context of the printer.
  */
  public Graphics2D getPrinterG2D() {
    return printerG2D;
  }
  
  /** Return the image of the trace buffer.
  */
  public BufferedImage getTraceBuffer() {
    return traceBuffer;
  }
  
  /** Clean the traces.
  All turtles stay how and where they are, only lines, text and stamps will be removed.
  */
  void clean() {
    Graphics2D g = getTraceG2D();
    g.setColor(getBackground());
    g.fillRect(0,0,getWidth(), getHeight());
    if (printerG2D == null)
    repaint();
  }
  
  /** Draw the <code>text</code> at the current position of the Turtle <code>t</code>.
  Drawing a text at some coordinates <code>(x,y)</code> we mean that the bottom left corner of
  the text will be at these coordinates.
  Font and colour are specified by the Turtle's Pen.
  */
  public void label(String text, Turtle t) {
    Point2D.Double sc = toScreenCoords(t.getPos());
    int x = (int)Math.round(sc.x);
    int y = (int)Math.round(sc.y);
    Graphics2D traceG2D = getTraceG2D();
    FontRenderContext frc = traceG2D.getFontRenderContext();
    Font f = t.getFont();
    TextLayout tl = new TextLayout(text, f, frc);
    traceG2D.setColor(t.getPen().getColor());
    tl.draw(traceG2D, x, y);
    if (printerG2D != null)
    {
      printerG2D.setColor(t.getPen().getColor());
      tl.draw(printerG2D, x, y);
    }
    
    if (printerG2D == null)
    repaint();
  }
  
  /**  Set antialiasing on or off for the turtle trace buffer
  * This may result in an better trace quality.
  */
  public void setAntiAliasing(boolean on) {
    if (on)
    traceG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_ON);
    else
    traceG2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
    RenderingHints.VALUE_ANTIALIAS_OFF);
  }
  
  
  /**
  * Set the given TPrintable (implementing draw()),
  * open a printer dialog and start printing with given scale.
  * Return false, if printer dialog is aborted,
  * otherwise return true.<br>
  * If tp ==  null, the current playground is printed.
  *
  */
  protected boolean print(TPrintable tp, double scale) {
    if (tp == null)
    isPrintScreen = true;
    else
    isPrintScreen = false;
    printerScale = scale;
    PrinterJob pj = PrinterJob.getPrinterJob();
    pj.setPrintable(this);
    traceCanvas = tp;
    if (pj.printDialog()) {
      try {
        pj.print();
      }
      catch (PrinterException ex) {
        System.out.println(ex);
      }
      return true;
    }
    else
    return false;
  }
  
  /**
  * For internal use only. Implementation of Printable.
  * (Callback method called by printing system.)
  */
  public int print(Graphics g, PageFormat pf, int pageIndex) {
    if (pageIndex != 0)
    return NO_SUCH_PAGE;
    Graphics2D g2D = (Graphics2D)g;
    double printerWidth = pf.getImageableWidth();
    double printerHeight = pf.getImageableHeight();
    double printerSize = printerWidth > printerHeight ? printerWidth :
    printerHeight;
    double xZero = pf.getImageableX();
    double yZero = pf.getImageableY();
    
    printerG2D = g2D;  // Indicate also, we are printing now
    
    // Needed for fill operations: the trace canvas must be empty in order to
    // perform the fill algoritm (searching for outline of figure)
    if (!isPrintScreen)
    clean();
    
    g2D.scale(printerScaleFactor * printerScale, printerScaleFactor * printerScale);
    g2D.translate(xZero/printerScale, yZero/printerScale);
    
    if (isPrintScreen)
    {
      print(g);
    }
    else  // Printing the traceCanvas
    {
      // Hide all turtles
      boolean[] turtleState = new boolean[countTurtles()];
      for (int i = 0; i < countTurtles(); i++) {
        Turtle aTurtle = getTurtle(i);
        turtleState[i] = aTurtle.isHidden();
        aTurtle.ht();
      }
      traceCanvas.draw();
      
      // Restore old context
      for (int i = 0; i < countTurtles(); i++) {
        Turtle aTurtle = getTurtle(i);
        if (!turtleState[i])
        aTurtle.st();
      }
    }
    
    printerG2D = null;
    return PAGE_EXISTS;
  }
  
  /** Return the color of the pixel at the current turtle position.
  */
  public Color getPixelColor(Turtle t) {
    Point2D.Double p1 = toScreenCoords(t.getPos());
    int x = (int)Math.round(p1.getX());
    int y = (int)Math.round(p1.getY());
    return new Color(traceBuffer.getRGB(x, y));
  }
  
  public void enableRepaint(boolean b) {
    isRepainting = b;
  }
  
  /** set the background color of the playground
  */
  public void setBackground(Color color) {
    super.setBackground(color);
    if (traceG2D != null) {
      clear(color);
    } // end of if
  }
  
  /**
  * Sets the origin of the cartesian coordinate system within the playground
  */
  public void setOrigin(int x, int y) {
    for (int i = 0; i < countTurtles(); i++) {
      Turtle turtle = getTurtle(i);
      Point2D.Double p1 = toScreenCoords(turtle.getPos());
      double newX = p1.getX() - x;
      double newY = y - p1.getY();
      turtle.internalSetPos(newX, newY);
    }      
    originX = x;
    originY = y;
  }
  
  public int getOriginX() {
    return originX;
  }
  
  public int getOriginY() {
    return originY;
  }
  
}

