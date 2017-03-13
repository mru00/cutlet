package com.cutlet.lib.tneller;

/**
 * SearchTest - Test stochastic local search algorithms and problems
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

public final class SearchTest {

    public static void main(final String[] args) {
	final int ITERATIONS = 1000000;
	
	// Uncomment the desired problem below:
	State state = new Rastrigin();
	//State state = new BinPackingProblem();
	
	// Uncomment the desired stochastic local search below:
	State minState = new HillDescender(state).search(ITERATIONS);
	//State minState = new SimulatedAnnealer(state, 1, .99999).search(ITERATIONS); 
	
	System.out.println(minState);
	System.out.println(minState.energy());
    }

}
