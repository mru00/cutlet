package com.cutlet.lib.tneller;

/**
 * SimulatedAnnealer.java - a simple simulated annealing algorithm for
 * stochastic local search.
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

public class SimulatedAnnealer {
    private static java.util.Random random = new java.util.Random();
    private State state;
    private double energy;
    private State minState;
    private double minEnergy;
    private double initTemp;
    private double decayRate;

    public SimulatedAnnealer(State initState, double initTemp, double decayRate) {
        state = initState;
        energy = initState.energy();
        minState = (State) state.clone();
        minEnergy = energy;
	this.initTemp = initTemp;
	this.decayRate = decayRate;
    }
    
    public State search(int iterations) 
    {
	double temperature = initTemp; // ***
	for (int i = 0; i < iterations; i++) {
//            if (i % 100000 == 0) 
//                System.out.println(minEnergy 
//                              + "\t" + energy);
            state.step();
            double nextEnergy = state.energy();
	    if (nextEnergy <= energy 
		|| random.nextDouble() // ***
		< Math.exp((energy - nextEnergy)
			   / temperature)) {
                energy = nextEnergy;
                if (nextEnergy < minEnergy) {
                    minState 
			= (State) state.clone();
                    minEnergy = nextEnergy;
                }
	    }
	    else
		state.undo();
	    temperature = temperature*decayRate; // ***
	}	    
	return minState;
	// *** indicates change from HillDescender
    }
}

