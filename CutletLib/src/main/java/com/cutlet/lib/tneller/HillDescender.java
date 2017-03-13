package com.cutlet.lib.tneller;

/**
 * HillDescender.java - a simple hill descent algorithm for stochastic
 * local search.
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

public class HillDescender {
    private State state;
    private double energy;
    private State minState;
    private double minEnergy;

    public HillDescender(State initState) {
        state = initState;
        energy = initState.energy();
        minState = (State) state.clone();
        minEnergy = energy;
    }

    public State search(int iterations) 
    {
        for (int i = 0; i < iterations; i++) {
            if (i % 100000 == 0) 
                System.out.println(minEnergy 
                              + "\t" + energy);
            state.step();
            double nextEnergy = state.energy();
            if (nextEnergy <= energy) {
                energy = nextEnergy;
                if (nextEnergy < minEnergy) {
                    minState 
                      = (State) state.clone();
                    minEnergy = nextEnergy;
                }
            }
            else
                state.undo();
        }
        return minState;
    }       
}
