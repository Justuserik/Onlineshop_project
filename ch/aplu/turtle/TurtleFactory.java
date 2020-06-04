// TurtleFactory.java

// Copyright 2002 Regula Hoefer-Isenegger
//
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

import java.awt.image.*;
import java.awt.*;

class TurtleFactory
{
  /** Generates the shape of the turtle with <code>color</code>,
   *  angle <code>angle</code>, width <code>w</code>
   *  and height <code>h</code>.
   */
  protected Image turtleImage(Color color, double angle, int w, int h)
  {
    BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
    Graphics2D g = (Graphics2D)bi.getGraphics();
    // Origin in center
    g.translate(w/2, h/2);
    // angle = 0 is direction east (as usual in mathematics)
    g.rotate(Math.PI/2 - angle);
    g.setColor(color);

    // Body
    g.fillOval((int)( -0.35 * w), (int)( -0.35 * h),
        (int)(0.7 * w), (int)(0.7 * h));

    // Head
    g.fillOval((int)( -0.1 * w), (int)( -0.5 * h),
        (int)(0.2 * w), (int)(0.2 * h));

    // Tail
    int[] xcoords =
        {(int)( -0.05 * w), 0, (int)(0.05 * w)};
    int[] ycoords =
        {(int)(0.35 * h), (int)(0.45 * h), (int)(0.35 * h)};
    g.fillPolygon(xcoords, ycoords, 3);

    // Feet
    for (int i = 0; i < 4; i++)
    {
      g.rotate(Math.PI / 2);
      g.fillOval((int)( -0.35 * w), (int)( -0.35 * h),
          (int)(0.125 * w), (int)(0.125 * h));
    }
    return (Image)bi;
  }
}
