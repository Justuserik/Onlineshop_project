package je.fx.util;

import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

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
 * 3.05 2017.03.18 first simple turtle for JavaFX
 * 4.00 2017.04.10 animated turtle for JavaFX
 * 4.01 2019.02.02 rotate starts animation too

 * This class is a turtle component for simple graphic programming.
 *
 * @see <a href="http://www.javaeditor.org">
 * @author Gerhard Röhner
 * @version 4.00 2017.04.16
 */


public class Turtle extends Pane {
  
  // -- private attributes -----------------------------------------------------
  
  // coordinates of the turtle
  private double turtleX = 0;
  private double turtleY = 0;
  private double turtleW = 0;
  
  // coordinates of a cartesian coordinate system relativ to the canvas
  private double originX;
  private double originY;
  
  // animation
  private Canvas canvas = new Canvas();
  private ImageView turtleImage = new ImageView(getClass().getResource("/je/fx/util/turtle.png").toExternalForm());  
  private SequentialTransition seqtr = new SequentialTransition();
  private Timer timer = new Timer();
  private boolean timerStarted = false;
  private boolean animated = true;
  private double animationspeed = 100;
  private boolean isDrawing = true;
  private boolean visibleImage = true;
    
  // line
  private GraphicsContext gc;
  private Color fillColor = Color.WHITE;
  private Color strokeColor = Color.BLACK;
  private StrokeLineCap lineCap = StrokeLineCap.SQUARE;
  private StrokeLineJoin lineJoin = StrokeLineJoin.MITER;
  private double lineWidth = 1;
  private double lineDashOffset = 0;
  private double miterLimit = 10;
  private boolean smooth = true;
 
  // --- constructor -----------------------------------------------------------
  
  /**
   * Creates a Turtle with a canvas.
   * At the beginning the Turtle is placed in the middle of it's canvas.
   * The start angle is 0 degree, which means the Turtle looks to the right.
   * The fill color is white, the drawing color black.
   * <P>
   * The turtle position can easily be changed by clicking into the canvas.
   * <P>
   * The size of the canvas can easily be changed with the mouse.
   * <P>originX and orginY define the origin of a cartesian coordinate system
   * related to upper left corner of the canvas.
   * <P>turtleX and turtleY are the coordinates related to this cartesian coordinate 
   * system. 
   * Example: <PRE>Turtle myTurtle = new Turtle();</PRE>
   */
    
  public Turtle() {
    canvas.setOnMouseClicked(
      (event) -> {canvasMouseClicked(event);} 
    );
    getChildren().add(canvas);
    gc = canvas.getGraphicsContext2D();
    setFill(Color.WHITE);
  }
   
  /**
   * Set the position of the Turtle by clicking on the canvas
   * <P>
   */ 
  public void canvasMouseClicked(MouseEvent evt) {
    turtleX = evt.getX() - originX;
    turtleY = originY - evt.getY();
    turtleW = 0;
    setTurtleImage();
  }
  
  // --- Drawing ---------------------------------------------------------------
  
  /**
   * The Turtle draws a line of the length specified in the current direction. 
   * <P>
   * Example: <PRE>myTurtle.draw(100);</PRE>
   */
  public void draw(double length) {
    drawto(turtleX + length * Math.cos(turtleW * Math.PI / 180),
           turtleY + length * Math.sin(turtleW * Math.PI / 180));
  }
  
  /**
   * The Turtle draws a line form the current position (turtleX, turtleY) to the
   * position (x, y) relativ to the cartesian coordinate system.
   * <P>
   * Example: <PRE>myTurtle.drawto(200, 300);</PRE>
   */
  public void drawto(double x, double y) {
    if (seqtr.getStatus() != Animation.Status.RUNNING) {
      double x1 = originX + turtleX;
      double x2 = originX + x;
      double y1 = originY - turtleY;
      double y2 = originY - y;  
      if (animated) {
        KeyFrame keyFrame;
        double length = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        double speed = length*1000/animationspeed;
        turtleImage.toFront();
      
        // animate turtleImage
        KeyValue xTurtle  = new KeyValue(turtleImage.xProperty(), x2-11);
        KeyValue yTurtle  = new KeyValue(turtleImage.yProperty(), y2-13);
      
        if (isDrawing) {
          Line line = new Line(x1, y1, x1, y1);
          line.setStroke(strokeColor);
          line.setStrokeWidth(lineWidth);
          line.setStrokeLineCap(lineCap);
          line.setStrokeDashOffset(lineDashOffset);
          line.setStrokeLineJoin(lineJoin);
          line.setStrokeMiterLimit(10); 
          line.setSmooth(smooth);   
      
          // clipping of line
          Rectangle clipRect = new Rectangle(0, 0, canvas.getWidth(), canvas.getHeight());
          line.setClip(clipRect);
          line.setOpacity(0);
          getChildren().add(line);
      
          // unhide line of length 0 by setting opacity to 1
          FadeTransition ft = new FadeTransition(Duration.millis(1), line);
          ft.setToValue(1);
          seqtr.getChildren().add(ft); 
      
          // animate line and turtleImage
          KeyValue xLine   = new KeyValue(line.endXProperty(), x2); 
          KeyValue yLine   = new KeyValue(line.endYProperty(), y2);           
          keyFrame = new KeyFrame(Duration.millis(speed), xLine, yLine, xTurtle, yTurtle);
        } else {
          // isMoving, animate only turtleImage
          keyFrame = new KeyFrame(Duration.millis(speed), xTurtle, yTurtle);
        } 
      
        Timeline timeline = new Timeline(keyFrame);    
        seqtr.getChildren().add(timeline);
        if (! timerStarted) 
          startTimer();
      } else if (isDrawing) {
        gc.setStroke(strokeColor);
        gc.setLineWidth(lineWidth);
        gc.setLineCap(lineCap);
        gc.setLineDashOffset(lineDashOffset);
        gc.setLineJoin(lineJoin);
        gc.setMiterLimit(10); 
        gc.strokeLine(x1, y1, x2, y2);      
      } 
      turtleX = x;
      turtleY = y;
    }
  } 
    
  /**
   * private method to execute a rotation
   * <P>
   */ 
  private void rotate(double angle) {
    if (animated && visibleImage) {
      double speed = Math.abs(angle*1000/animationspeed);
      RotateTransition rt = new RotateTransition(Duration.millis(speed), turtleImage);
      rt.setToAngle(-turtleW); 
      seqtr.getChildren().add(rt);
      if (! timerStarted) 
        startTimer();      
    }
  }
      
  /**
    * Clear the Turtle's canvas with the fill color.
    * <P>
    * Example: <PRE>myTurtle.clear();</PRE>
    */
  public void clear() {
    getChildren().remove(1, getChildren().size());
    if (animated) {
      getChildren().add(turtleImage);    
    } 
    setFill(fillColor);
  }
      
  /**
    * Stops the animation 
    * <P>
    * Example: <PRE>myTurtle.stop();</PRE>
    */
  public void stop() {
    seqtr.stop();
  }
  
  /**
    * private method for placing the TurtleImage.
    * <P>
    */   
  private void setTurtleImage() {
    if (animated) {
      turtleImage.setX(originX + turtleX - 11);
      turtleImage.setY(originY - turtleY - 13);
      turtleImage.setRotate(turtleW);
    } 
  }  
  
  /**
    * private method to start an animation
    * needed for setTurtleW
    */
  private void go() {
    turtleImage.toFront();
    seqtr.play(); 
    seqtr.getChildren().clear();
  }  
    
  /**
   * private method for starting the animation.
   * <P>
   */  
  private void startTimer() {
    timer.schedule(new TimerTask() {
      public void run() {
        Platform.runLater(new Runnable() {
          public void run() {
            go();
            timerStarted = false;
          }
        });
      }
    }, 1); // one-time execution after a delay of 1 ms
    timerStarted = true;
  }
  
  // --- Moving ----------------------------------------------------------------
    
  /**
   * The Turtle moves without drawing the length specified in the current 
   * direction.
   * <P>
   * Example: <PRE>myTurtle.move(100);</PRE>
   */
  public void move(double length) {
    isDrawing = false;
    draw(length);
    isDrawing = true;
  }
    
  /**
   * The Turtle moves without drawing to position (x, y) relativ to the
   * cartesian coordinate system.
   * <P>
   * Example: <PRE>myTurtle.moveto(100, 200);</PRE>
   */
  public void moveto(double x, double y) {
    isDrawing = false;
    drawto(x, y);
    isDrawing = true;   
  }
    
  // --- angle and turns -------------------------------------------------------
    
  /**
   * Turns the angle of the turtle relativ by the parameter angle. 
   * Positive values mean a turn right, negative a turn left.
   * <P>
   * Example: <PRE>myTurtle.turn(-90);</PRE>
   */
  public void turn(double angle) {
    turtleW = turtleW + angle;
    rotate(angle);
  }
    
  /**
    * Sets the angle of the turtle absolute by the parameter angle.
    * The angle increases counterclockwise, therefore
    * right = 0, top = 90, left = 180, bottom = 270.
    * <P>
    * Example: <PRE>myTurtle.turnto(270);</PRE>
    */
  public void turnto(double angle) {
    double w = angle - turtleW;
    turtleW = angle;
    rotate(w);
  }
       
  // -- getter/setter ----------------------------------------------------------
    
  /**
   * Sets turtleX of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>myTurtle.setTurtleX(100);</PRE>
   */
  public void setTurtleX(double x) {
    turtleX = x;
    setTurtleImage();
  } 
  
  /**
   * Gets turtleX of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>int xpos = myTurtle.getTurtleX();</PRE>
   */
  public double getTurtleX() {
    return turtleX;
  } 
  
  /**
   * Sets turtleY of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>myTurtle.setTurtleY(100);</PRE>
   */
  public void setTurtleY(double y) {
    turtleY = y;
    setTurtleImage();
  } 
  
  /**
   * Gets turtleY of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>int ypos = myTurtle.getTurtleY();</PRE>
   */
  public double getTurtleY() {
    return turtleY;
  } 

  /**
   * Sets turtleW, the current angle of the turtle in the range form 0 to 360 degrees.
   * <P>
   * Example: <PRE>myTurtle.setTurtleW(90);</PRE>
   */
  public void setTurtleW(double w) {
    turnto(w);
    go();
  }
  
  /**
   * Gets turtleW, the current angle of the turtle in degrees.
   * <P>
   * Example: <PRE>double alpha = myTurtle.getTurtleW();</PRE>
   */
  public double getTurtleW() {
    return turtleW;
  }
    
  // --- get/set origin --------------------------------------------------------
    
  /**
   * gets OriginX of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>int originX = myTurtle.getOriginX();</PRE>
   */
  public double getOriginX() {
    return originX;
  }
  
  /**
   * Sets OriginX of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>myTurtle.setOriginX(100);</PRE>
   */
  public void setOriginX(double x) {
    originX = x;
    setTurtleImage();
  }
  
  /**
   * gets OriginY of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>int originY = myTurtle.getOriginY();</PRE>
   */
  public double getOriginY() {
    return originY;
  }
  
  /**
   * Sets OriginY of the cartesian coordinate system within the turtle's canvas.
   * <P>
   * Example: <PRE>myTurtle.setOriginY(100);</PRE>
   */
  public void setOriginY(double y) {
    originY = y;
    setTurtleImage();
  }
  
  /**
   * Sets the width of the Turtle.
   * <P>
   * Example: <PRE>myTurtle.setWidth(400);</PRE>
   */
  public void setWidth(int width) {
    super.setWidth(width);
    canvas.setWidth(width);
    Rectangle ClipImage = new Rectangle(0, 0, width, getHeight());    
    turtleImage.setClip(ClipImage);
    setFill(fillColor);
    setTurtleImage();    
  }
    
  /**
   * Sets the height of the Turtle.
   * <P>
   * Example: <PRE>myTurtle.setHeight(400);</PRE>
   */
  public void setHeight(int height) {
    super.setHeight(height);
    canvas.setHeight(height);
    Rectangle ClipImage = new Rectangle(0, 0, getWidth(), height);    
    turtleImage.setClip(ClipImage);
    setFill(fillColor);
    setTurtleImage();    
  }    
    
  /**
   * Sets animation on/off.
   * <P>
   * Example: <PRE>myTurtle.setAnimated(true);</PRE>
   */
  public void setAnimated(boolean a) {
    animated = a;
    if (animated) {
      getChildren().add(turtleImage); 
      setTurtleImage();      
    } else {
      getChildren().remove(1, getChildren().size());
    } 
  }  
    
  /**
   * gets animation mode
   * <P>
   * Example: <PRE>myTurtle.getAnimated();</PRE>
   */
  public boolean getAnimated() {
    return animated;
  }    
  
  /**
   * Sets the animationspeed, default is 100px/s and 100°/s
   * <P>
   * Example: <PRE>myTurtle.setAnimationspeed(100);</PRE>
   */
  public void setAnimationspeed(double speed) {
    animationspeed = speed;
  }  
  
  /**
   * Gets the animationspeed, default is 100px/s and 100°/s
   * <P>
   * Example: <PRE>myTurtle.getAnimationspeed();</PRE>
   */
  public double getAnimationspeed() {
    return animationspeed;
  }   
  
  /**
   * Sets the visibility of the turtle image
   * <P>
   * Example: <PRE>myTurtle.setVisibleImage(true);</PRE>
   */
  public void setVisibleImage(boolean value) {
    if (visibleImage != value) {
      visibleImage = value;
      turtleImage.setVisible(visibleImage);
    } 
  }  
  
  /**
   * Gets the visibility of the turtle image
   * <P>
   * Example: <PRE>myTurtle.getVisibleImage();</PRE>
   */
  public boolean getVisibleImage() {
    return visibleImage;
  }    
  
  // --- stroke- and fill color --------------------------------------------
  
  /**
   * Gets the drawing color of the Turtle.
   * <P>
   * Example: <PRE>Color c = myTurtle.getStroke();</PRE>
   */
  public Color getStroke() {
    return strokeColor;
  }
  
  /**
   * Sets the drawing color of the Turtle to color c.
   * <P>
   * Example: <PRE>myTurtle.setStroke(Color.red);</PRE>
   */
  public void setStroke(Color c) {
    strokeColor = c;
  }
  
  /**
   * Gets the backgorund color of the Turtle.
   * <P>
   * Example: <PRE>Color c = myTurtle.getFill();</PRE>
   */
  public Color getFill() {
    return fillColor;
  }  
  
  /**
   * Sets the canvas color of the Turtle to color c.
   * <P>
   * Example: <PRE>myTurtle.setFill(Color.blue); </PRE>
   */
  public void setFill(Color c) {
    fillColor = c;
    gc.setFill(fillColor);
    gc.fillRect(0, 0, getWidth(), getHeight());
  }
    
  // --- strokes ---------------------------------------------------------------
    
  /**
   * Sets the stroke line cap of the Turtle to cap. The default value is SQUARE.
   * <P>
   * Example: <PRE>myTurtle.setLineCap(StrokeLineCap.BUTT); </PRE>
   */
  public void setLineCap(StrokeLineCap cap) {
    lineCap = cap;
  }
  
  /**
   * Sets the stroke line join of the Turtle to join. The default value is MITER.
   * <P>
   * Example: <PRE>myTurtle.setLineJoin(StrokeLineJoin.ROUND); </PRE>
   */
  public void setLineJoin(StrokeLineJoin join) {
    lineJoin = join;
  } 
  
  /**
   * Sets the line width of the Turtle to width w.
   * <P>
   * Example: <PRE>myTurtle.setLineWidth(3); </PRE>
   */
  public void setLineWidth(int width) {
    lineWidth = width;
  } 
  
  /**
   * Sets the line dash offset of the Turtle to dash. The default value is 0.
   * <P>
   * Example: <PRE>myTurtle.setLineDashOffset(double dash); </PRE>
   */
  public void setLineDashOffset(double dash) {
    lineDashOffset = dash;
  }  
 
  /**
   * Sets the miter limit of the Turtle to ml.
   * <P>
   * Example: <PRE>myTurtle.setMiterLimit(15); </PRE>
   */
  public void setMiterLimit(double limit) {
    miterLimit = limit;
  } 
  
  /**
   * Sets the smooth-property of the Turtle to s.
   * <P>
   * Example: <PRE>myTurtle.setSmooth(true); </PRE>
   */
  public void setSmooth(boolean smooth) {
    this.smooth = smooth;
  }  
     
}
