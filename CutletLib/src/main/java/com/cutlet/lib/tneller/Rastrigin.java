package com.cutlet.lib.tneller;

/**
 * Rastrigin.java - Stochastic local search implementation of
 * a Rastrigin function search state.
 * See http://cs.gettysburg.edu/~tneller/resources/sls/ for details.
 *
 * @author Todd Neller
 * @version 1.0
 *

Copyright (C) 2005 Todd Neller

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either version 2
of the License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

Information about the GNU General Public License is available online at:
  http://www.gnu.org/licenses/
To receive a copy of the GNU General Public License, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
02111-1307, USA.

 */

public class Rastrigin implements State {
    public static final double STDDEV = .05;
    public static java.util.Random random 
                    = new java.util.Random();
    public double x;
    public double y;
    public double prevX;
    public double prevY;

    public Rastrigin() {
        this(10.0, 10.0);
    }

    public Rastrigin(double x, double y) {
        this.x = x;
        this.y = y;
        prevX = x;
        prevY = y;
    }

    public void step() {
        prevX = x;
        prevY = y;
        x += STDDEV * random.nextGaussian();
        y += STDDEV * random.nextGaussian();
    }

    public void undo() {
        x = prevX;
        y = prevY;
    }
            
    public double energy() {
        return x * x + y * y - Math.cos(18 * x) 
               - Math.cos(18 * y) + 2;
    }

    public Object clone() {
        Rastrigin copy = new Rastrigin(x, y);
        copy.prevX = prevX;
        copy.prevY = prevY;
        return copy;
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
