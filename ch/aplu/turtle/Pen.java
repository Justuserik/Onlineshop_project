// Pen.java

// Copyright 2002 Regula Hoefer-Isenegger
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
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Stroke;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

/**   The Pen class provides anything used for drawing the lines, such as line width,
      pen color, end caps, dashed lines, etc.

      @see java.awt.BasicStroke

      @author <a href="mailto:regula@hoefer.ch">Regula Hoefer-Isenegger</a>
      @version 0.1.1
*/
public class Pen
{
  /* Attributes *********************************************/

  /** The default font that is used when drawing Text.

      First argument must be one of "Serif", "SansSerif", "Monotyped", "Dialog" or "DialogInput"
      to guarantee that this font exists on all systems.

      @see java.awt.Font for more information, e.g. on font styles.

  */
  public static Font DEFAULT_FONT = new Font("SansSerif", Font.PLAIN, 24);
  private Color color;
  private Color fillColor;
  private BasicStroke stroke;
  private Font font;

  /* Constructors *******************************************/
  /** Constructor with standard Color and standard Stroke.

  @see java.awt.BasicStroke
  */
  public Pen(){
    color = Color.black;
    setFillColor(Color.black);
    stroke = new BasicStroke();
    font = DEFAULT_FONT;
  }
  /**  Constructor with Color <code>color</code> and standard Stroke.

  @see java.awt.BasicStroke
  */
  public Pen(Color color){
    this.color = color;
    setFillColor(color);
    stroke = new BasicStroke();
    font = DEFAULT_FONT;
  }
  /* Methods ************************************************/
  /** Query the <code>Pen</code>s color.*/
  public Color getColor(){
    return color;
  }
  /** Set the <code>Pen</code>s color.*/
  public void setColor(Color color){
    this.color = color;
  }
  /** Set the <code>Pen</code>s fill color.
   */
  public void setFillColor(Color color){
    this.fillColor = color;
  }
  /** Query the <code>Pen</code>s fill color.*/
  public Color getFillColor(){
    return this.fillColor;
  }
  /** Get the <code>Pen</code>s <code>Stroke</code>

  @see BasicStroke
  @see Stroke
  */
  public Stroke getStroke(){
    return stroke;
  }
  /** Query the <code>Pen</code>s line width*/
  public float getLineWidth(){
    return stroke.getLineWidth();
  }
  /** Query the <code>Pen</code>s end cap style.

  @see java.awt.BasicStroke
  */
  public int getEndCap(){
    return stroke.getEndCap();
  }
  /** Query the <code>Pen</code>s line join style.

  @see java.awt.BasicStroke
  */
  public int getLineJoin(){
    return stroke.getLineJoin();
  }
  /** Query the <code>Pen</code>s miter limit style.

  @see java.awt.BasicStroke
  */
  public float getMiterLimit(){
    return stroke.getMiterLimit();
  }
  /** Query the <code>Pen</code>s dash array.

  @see java.awt.BasicStroke
  */
  public float[] getDashArray(){
    return stroke.getDashArray();
  }
  /** Query the <code>Pen</code>s dash phase.

  @see java.awt.BasicStroke
  */
  public float getDashPhase(){
    return stroke.getDashPhase();
  }

  /** Set the <code>Pen</code>s line width. */
  public void setLineWidth(float width){
    stroke = new BasicStroke((float)width,
                             stroke.getEndCap(),
                             stroke.getLineJoin(),
                             stroke.getMiterLimit(),
                             stroke.getDashArray(),
                             stroke.getDashPhase());
  }
  /** Set the <code>Pen</code>s end cap style.

  @see java.awt.BasicStroke
  */
  public void setEndCap(int endCap){
    stroke = new BasicStroke(stroke.getLineWidth(),
                             endCap,
                             stroke.getLineJoin(),
                             stroke.getMiterLimit(),
                             stroke.getDashArray(),
                             stroke.getDashPhase());
  }
  /** Set the <code>Pen</code>s line join style.

  @see java.awt.BasicStroke
  */
  public void setLineJoin(int join){
    stroke = new BasicStroke(stroke.getLineWidth(),
                             stroke.getEndCap(),
                             join,
                             stroke.getMiterLimit(),
                             stroke.getDashArray(),
                             stroke.getDashPhase());
  }
  /** Set the <code>Pen</code>s miter limit.

  @see java.awt.BasicStroke
  */
  public void setMiterLimit(float miterlimit){
    stroke = new BasicStroke(stroke.getLineWidth(),
                             stroke.getEndCap(),
                             stroke.getLineJoin(),
                             miterlimit,
                             stroke.getDashArray(),
                             stroke.getDashPhase());
  }
  /** Set the <code>Pen</code>s dash array.

  @see java.awt.BasicStroke
  */
  public void setDash(float[] dashArray){
    stroke = new BasicStroke(stroke.getLineWidth(),
                             stroke.getEndCap(),
                             stroke.getLineJoin(),
                             stroke.getMiterLimit(),
                             dashArray,
                             stroke.getDashPhase());
  }
  /** Set the <code>Pen</code>s dash phase.

  @see java.awt.BasicStroke
  */
  public void setDashPhase(float dashPhase){
    stroke = new BasicStroke(stroke.getLineWidth(),
                             stroke.getEndCap(),
                             stroke.getLineJoin(),
                             stroke.getMiterLimit(),
                             stroke.getDashArray(),
                             dashPhase);
  }
  /** Provides information about the currently available font families (e.g. "Roman").
      Each font name is a string packed into a array of strings.
      @see java.awt.Font for more information about font attributes etc.
  */
  public static String[] getAvailableFontFamilies(){
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String s[] = ge.getAvailableFontFamilyNames();
    return s;
  }
  /** Change the font style.

      @see java.awt.Font for possible styles.
  */
  public void setFontStyle(int style){
    font = font.deriveFont(style);
  }
  /** Change the font size (in points).
  */
  public void setFontSize(int size){
    font = font.deriveFont((float)size);
  }
  /** Change the font size (in points).
      You will probably only need the int version <a href="#setFontSize(int)">setFontSize(int)</a>.
  */
  public void setFontSize(float size){
    font = font.deriveFont(size);
  }
  /** Query the size (in points, rounded to int) of the current font.
   */
  public int getFontSize(){
    return font.getSize() ;
  }
  /** Change the font to the given one.
   */
  public void setFont(Font f){
    font = f;
  }
  /** Query the current font.
  */
  public Font getFont(){
    return font;
  }
}














