package com.cutlet.lib.tneller;

/**
 * BinPackingProblem.java - Stochastic local search implementation of
 * a random bin packing problem state.
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

import java.util.PriorityQueue;

public class BinPackingProblem implements State {
    public static java.util.Random random = new java.util.Random();
    public int[] sizes;
    public int[] placements;
    public int[] binLoads;
    public int lastItem;
    public int lastPlacement;

    public BinPackingProblem() {
	this(1, 100, 100, 10);
    }

    public BinPackingProblem(int min, int max, int items, int bins) {
	sizes = new int[items];
	placements = new int[items];
	binLoads = new int[bins];
	for (int i = 0; i < items; i++) {
	    sizes[i] = random.nextInt(max - min + 1) + min;
	    placements[i] = random.nextInt(bins);
	    binLoads[placements[i]] += sizes[i];
	}
    }

    public void step() {
	lastItem = random.nextInt(sizes.length);
	lastPlacement = placements[lastItem];
	while (placements[lastItem] == lastPlacement)
	    placements[lastItem] = random.nextInt(binLoads.length);
	binLoads[lastPlacement] -= sizes[lastItem];
	binLoads[placements[lastItem]] += sizes[lastItem];
    }

    public void undo() {
	binLoads[placements[lastItem]] -= sizes[lastItem];
	binLoads[lastPlacement] += sizes[lastItem];
	placements[lastItem] = lastPlacement;
    }
	
    public double energy() {
	int min = binLoads[0];
	int max = binLoads[0];	
	for (int i = 1; i < binLoads.length; i++) {
	    if (binLoads[i] < min)
		min = binLoads[i];
	    if (binLoads[i] > max)
		max = binLoads[i];
	}
	return max - min;
    }

    public Object clone() {
	try {
	    BinPackingProblem bp = (BinPackingProblem) super.clone();
	    bp.sizes = sizes.clone();
	    bp.placements = placements.clone();
	    bp.binLoads = binLoads.clone();
	    return bp;
	}
	catch (CloneNotSupportedException e) {
	    System.out.println(e);
	}
	return null;
    }

    public String toString() {
	StringBuffer sb = new StringBuffer();
	for (int b = 0; b < binLoads.length; b++) {
	    sb.append(binLoads[b] + " = ");
	    PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
	    for (int i = 0; i < sizes.length; i++)
		if (placements[i] == b)
		    queue.add(sizes[i]);
	    if (!queue.isEmpty())
		sb.append(queue.poll());
	    while (!queue.isEmpty())
		sb.append(" + " + queue.poll());
	    sb.append("\n");
	}
	return sb.toString();
    }
}



