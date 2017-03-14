/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.testing;

import com.cutlet.lib.model.Layout;
import com.cutlet.lib.optimizer.OptimizationResult;
import java.io.PrintStream;

/**
 *
 * @author rmuehlba
 */
public class ConsoleOutput {

    public static void dumpResult(OptimizationResult r) {
        dumpResult(System.err, r);
    }
    
    public static void dumpResult(PrintStream out, OptimizationResult r) {
        out.println("");
        out.println("");
        out.println("RESULT");

        for (Layout l : r.getLayouts()) {
            dumpLayout(out, l);
        }
    }

    private static void dumpLayout(PrintStream out, Layout l) {
        out.println("layout " + l.getId());
        //dumpSheet(out, l.getCutTree(), 0);
    }
/*
    private static void dumpSheet(PrintStream out, CutTree cutTree, int level) {
        String indent = String.join("", Collections.nCopies(2 * level, " "));
        out.println(indent + "SubSheet " + cutTree);
        if (cutTree.hasCut()) {
            final Cut cut = cutTree.getCut();
            out.println(indent + " has cut");
            dumpSheet(out, cut.getTarget(), level + 1);
            dumpSheet(out, cut.getRest(), level + 1);
        } else {
            if (cutTree.hasPanel()) {
                out.println(indent + " has panel " + cutTree.getPanel());
            } else {
                out.println(indent + " is free" + cutTree);
            }
        }
    }
*/
}
