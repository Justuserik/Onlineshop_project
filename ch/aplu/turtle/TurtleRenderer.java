// TurtleRenderer.java

// Copyright 2002 Regula Hoefer-Isenegger
// Some modifications by Aegidius Pluess

// This file is part of The Java Turtle (TJT) package
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
import java.awt.image.*;
import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.*;


/** This class is responsible for creating and choosing the correct Turtle picture.
    @author <a href="mailto:regula@hoefer.ch">Regula Hoefer-Isenegger</a>
    @version 0.1
*/
public class TurtleRenderer implements ImageObserver {
  /** Holds the current image */
  private Image currentImage;
  /** Holds all images */
  private Vector images;
  /** Tells how many pictures are needed*/
  private int resolution;
  /** A reference to the <code>Turtle</code> */
  private Turtle turtle;
  /** Holds the current Angle */
  private double currentAngle;

  private final int turtleSize = 29;

  /***/
  public TurtleRenderer(Turtle turtle) {
    this.currentImage = null;
    this.images = new Vector();
    this.turtle = turtle;
    currentAngle = 0;
  }

  /** As an image stays unchanged, there's no need to ever update it. So this method returns always false.

  For further information cf. <code>java.awt.image.ImageObserver</code>.
  @see java.awt.image.ImageObserver#imageUpdate(java.awt.Image, int, int, int, int, int)
  */
  public boolean imageUpdate(Image img, int infoflags,
                             int x, int y, int width, int height) {
    return false;
  }

  /** Return the current image.
   */
  public Image currentImage() {
    return this.currentImage;
  }

  /** Tell whether the image has changed.
   */
  public boolean imageChanged(double angle) {
    return (this.currentImage != getImage(angle));
  }

  /** Set the current image to the specified one. */
  private void setCurrentImage(Image image) {
    currentImage = image;
  }

  /** Choose the image for the angle <code>angle</code>.
   */
  private Image getImage(double angle) {
    while (angle < 0) {
      angle += 2*Math.PI;
    }
    while (angle >= 2*Math.PI) {
      angle -= 2*Math.PI;
    }
    double res = 2*Math.PI/(double)this.resolution;
    int index = (int)(angle/res);
    return image(index);
  }

  /** Set the current image to the one corresponding to the angle <code>angle</code>.
   */
  public void setAngle(double angle) {
    currentAngle = angle;
    setCurrentImage(getImage(angle));
  }

  /** @return the current angle. */
  protected double getAngle(){
    return currentAngle;
  }

  /** Create the images. There are <code>resolution</code> images (i.e. two subsequent
      images contain an angle of 2&pi;/<resolution> or 360/resolution degrees).
  */
  public void init(TurtleFactory factory, int resolution) {
    this.resolution = resolution;
    Integer res = new Integer(resolution);
    double incRes = Math.PI*2/res.doubleValue();
    double angle = 0;
    images = new Vector();
    for (int i = 0; i < resolution; i++) {
      images.add(factory.turtleImage(turtle.getColor(),
                         turtle.getPlayground().toScreenAngle(angle),
                         turtleSize, turtleSize));
      angle += incRes;
    }
    setCurrentImage(getImage(currentAngle));
  }

  /** Tell how many images this <code>TurtleRenderer</code> holds */
  private int countImages() {
    return this.images.size();
  }

  /** Get the image at <code>index</code> */
  private Image image(int index) {
    return (Image)this.images.elementAt(index);
  }

  /** This method is responsible for painting the turtle onto the
      playground at (<code>x, y</code>).
  */
  public final void paint(double x, double y) {
    internalPaint(x, y, turtle.getPlayground().getGraphics());
  }

  /** This method is responsible for painting the turtle onto the
      playground at <code>p</code>.
  */
  public final void paint(Point2D.Double p) {
    internalPaint(p.x, p.y, turtle.getPlayground().getGraphics());
  }

  /** This method is responsible for painting the <code>Turtle</code>
      at (<code>x, y</code>).<br>
      The Graphics argument tells where to paint.
  */
  public final void paint(double x, double y, Graphics g) {
    internalPaint(x, y, g);
  }

  /** This method is responsible for painting the <code>Turtle</code>
      at <code>p</code>.<br>
      The Graphics argument tells where to paint.
  */
  public void paint(Point2D.Double p, Graphics g){
    internalPaint(p.x, p.y, g);
  }

  /** Call clipPaint and wrapPaint().

  You should override this method only, if you add a new (edge)
  behaviour to the turtle. I recommend to you then to first call this
  method from the overriding one.
  <br>
  If you want to change anything about the real painting, override
  clipPaint or wrapPaint.

  @see #clipPaint(int, int, Graphics2D)
  @see #wrapPaint(int, int, Graphics2D)
  */
  protected void internalPaint(double x, double y, Graphics g) {
    if(turtle.isClip()){
      Point2D.Double p =
        calcTopLeftCorner(turtle.getPlayground().toScreenCoords(x, y));
      clipPaint((int)p.x, (int)p.y, (Graphics2D)g);
    }
    else if(turtle.isWrap()){
      Point2D.Double p =
        calcTopLeftCorner(turtle.getPlayground().toScreenCoords(x, y));
      wrapPaint((int)p.x, (int)p.y, (Graphics2D)g);
    }
  }

  /** Define how to paint in clip mode and do it */
  protected void clipPaint(int x, int y, Graphics2D g2D) {
    g2D.drawImage(currentImage, x, y, this);
  }

  /** Define how to paint in wrap mode and do it */
  protected void wrapPaint(int x, int y, Graphics2D g2D) {
    int pWidth = turtle.getPlayground().getWidth();
    int pHeight = turtle.getPlayground().getHeight();
    int paintX = x;
    while (paintX > pWidth) {
      paintX -= pWidth;
    }
    while (paintX < 0) {
      paintX += pWidth;
    }
    int paintY = y;
    while (paintY > pHeight) {
      paintY -= pHeight;
    }
    while (paintY < 0) {
      paintY += pHeight;
    }
    g2D.drawImage(currentImage, paintX, paintY, this);
    int nWidth = currentImage.getWidth(this);
    int nHeight = currentImage.getHeight(this);
    boolean right = (paintX+nWidth > pWidth);
    boolean bottom = (paintY+nHeight > pHeight);
    if(right) {
      g2D.drawImage(currentImage,
                    paintX-pWidth,
                    paintY,
                    this);
    }
    if(bottom) {
      g2D.drawImage(currentImage,
                    paintX,
                    paintY-pHeight,
                    this);
    }
    if(right && bottom) {
      g2D.drawImage(currentImage,
                    paintX-pWidth,
                    paintY-pHeight,
                    this);
    }
  }

  /** Compute the x-coordinate of the top left corner of the Turtle image
      (it depends on the specified x-coordinate and the image width).
  */
  protected int calcTopLeftCornerX(double x) {
    int intX = (int)x;
    int nWidth;
    if(currentImage == null) {
      setCurrentImage(new TurtleFactory().
                        turtleImage(turtle.getColor(), getAngle(), turtleSize, turtleSize));
    }
    nWidth = this.currentImage.getWidth(this);
    // the center of the turtle lies on the turtle's location:
    intX -= nWidth/2;
    return intX; // top left corner of the Turtle's image
  }

  /** Compute the y-coordinate of the top left corner of the Turtle image
      (it depends on the specified y-coordinate and the image height).
  */
  protected int calcTopLeftCornerY(double y) {
    int intY = (int)y;
    if(currentImage == null) {
      setCurrentImage(new TurtleFactory().
                        turtleImage(turtle.getColor(), getAngle(), turtleSize, turtleSize));
    }
    int nHeight = currentImage.getHeight(this);
    // the center of the turtle lies on the turtle's location:
    intY -= nHeight/2;

    return intY; // top left corner of the Turtle's image
  }
  /** Compute the top left corner of the Turtle image
      (dependent on the specified x- and y-coordinate and the image
      width and height.
  */
  protected Point2D.Double calcTopLeftCorner(double x, double y) {
    if(currentImage == null) {
      setCurrentImage(new TurtleFactory().
                        turtleImage(turtle.getColor(), getAngle(), turtleSize, turtleSize));
    }
    int w = currentImage.getWidth(this);
    int h = currentImage.getHeight(this);
    return new Point2D.Double(x-w/2, y-h/2);
  }

  /** Compute the top left corner of the Turtle image
      (dependent on the specified point <code>p</code> and the image
      width and height.
  */
  protected Point2D.Double calcTopLeftCorner(Point2D.Double p) {
    return calcTopLeftCorner(p.x, p.y);
  }
}


















