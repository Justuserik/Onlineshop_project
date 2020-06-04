// TPrintable.java

// Copyright 2002 Regula Hoefer-Isenegger

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

// Added by Aegidius Pluess

package ch.aplu.turtle;

/**
 * Interface for printing on an attached printer.
 * Normally an application uses a Turtle and implements this interface.
 * draw() should contain all drawing operations into the
 * Turtle's Playground. The printing occures, when Turtle's print() is
 * called.<br><br>
 */


public interface TPrintable
{
  /**
   * This method must perform all drawing operations.
   * Be aware that for some undocumented reason
   * draw() is called twice. So be sure you initialize it correctly.
   */

   public void draw();
}