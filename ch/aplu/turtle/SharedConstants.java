// SharedConstants.java

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

/* History;
V1.36 - Sep 2004: First official release
V1.37 - Nov 2004: Unchanged, modifications in ch.aplu.util
V1.38 - Dec 2004: Unchanged, modifications in ch.aplu.util
V1.39 - Jan 2005: Unchanged, modifications in ch.aplu.util
V1.40 - Mar 2005: Add background color, TurtleKeyAdapter, TurtleArea
V1.41 - May 2005: User defined turtle shape, minor changes in doc and code style
V1.42 - Dec 2005: Unchanged, modifications in ch.aplu.util
V1.43 - Feb 2006: Bug removed: Turtle.turtleFrame was not initialized in all ctors of class Turtle
V1.44 - Mar 2007: Bug removed: stampTurtle did not work properly in wrap mode
V1.45 - Aug 2007: TurtleKeyAdapter: use wait/notify to reduce CPU time (from 100% to 2%)
V1.46 - Aug 2007: synchronize(playground) for forward and rotate animation,
                  new method bong() using StreamingPlayer
V1.47 - Sept 2007: Unchanged, modifications in ch.aplu.util
V1.48 - Sept 2007: Unchanged, modifications in ch.aplu.util
V1.49 - Oct 2007: Unchanged, modifications in ch.aplu.util
V1.50 - Oct 2007: Unchanged, modifications in ch.aplu.util
V1.51 - Nov 2007: Fixed: correct position of label, when wrapping is on
                  Fixed: getPos() returns now the wrapped coordinates
                  Added: _getPos() returns unwrapped coordinates
V1.52 - Nov 2007: Added bean classes in order to use turtles with a Gui builder
V1.53 - Nov 2007: Added TurtlePane visual information when used in Gui builder design mode
V1.54 - Nov 2007: Minor changes to documentation
V1.55 - Dec 2007: Added property enableFocus to GPane, default: setFocusable(false)
V1.56 - Mar 2008: Unchanged, modifications in ch.aplu.util
V1.57 - Mar 2008: Unchanged, modifications in ch.aplu.util
V1.58 - Mar 2008: Modification to fill() (fill(x, y)):
                  region is defined with pixel color at current position
                  as opposed to background color
V1.59 - Oct 2008: Added ctors TurtleFrame with window position (ulx, uly)
                  Added Turtle.getPixelColor()
V2.00 - Nov 2008: Unchanged, modifications in ch.aplu.util
                  J2SE V1.4 no longer supported
V2.01 - Jan 2009: Unchanged, modifications in ch.aplu.util
V2.02 - Feb 2009  Turtle constructors run in EDT now
V2.03 - Feb 2009  Unchanged, modifications in ch.aplu.util
V2.04 - Feb 2009  Unchanged, modifications in ch.aplu.util
V2.05 - Feb 2009  Unchanged, modifications in ch.aplu.util
V2.06 - Mar 2009  Unchanged, modifications in ch.aplu.util
V2.07 - Mar 2009  All except print methods synchronized, so Turtle package is
                  now thread-safe
V2.08 - Apr 2009  Unchanged, modifications in ch.aplu.util
V2.09 - Jun 2009  Unchanged, modifications in ch.aplu.util
V2.10 - Jun 2010  Version for the Java-Editor
*/

package ch.aplu.turtle;

interface SharedConstants
{
  int DEBUG_LEVEL_OFF = 0;
  int DEBUG_LEVEL_LOW = 1;
  int DEBUG_LEVEL_MEDIUM = 2;
  int DEBUG_LEVEL_HIGH = 3;

  int DEBUG = DEBUG_LEVEL_OFF;

  String ABOUT =
    "Copyright © 2002-2009\nRegula Hoefer-Isenegger, Aegidius Pluess\n, Gerhard Röhner\n" +
    "under GNU General Public License\n" +
    "http://www.aplu.ch\n" +
    "All rights reserved";
  String VERSION = "2.10 - June 2015";
}
