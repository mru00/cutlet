/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Layout;
import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.CutNode;
import com.cutlet.lib.data.cuttree.CutTreeNode;
import com.cutlet.lib.data.cuttree.FreeNode;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public abstract class AbstractOptimizationStrategy implements OptimizationStrategy {

    protected FreeNode findSheet(@NonNull final OptimizationResult result,
            @NonNull final Panel p) {

        return getFreeNodes(result).stream()
                .filter(a -> a.canHold(p))
                .findFirst()
                .orElse(null);
    }

    protected List<FreeNode> getFreeNodes(@NonNull final OptimizationResult result) {
        final List<FreeNode> free = new ArrayList<>();
        for (Layout layout : result.getLayouts()) {
            for (CutTreeNode n : layout.getCutTree()) {
                if (n instanceof FreeNode) {
                    free.add((FreeNode) n);
                }
            }
        }
        return free;
    }

    protected FreeNode cutToFit(@NonNull FreeNode candidate,
            @NonNull final Project project,
            @NonNull final Panel p) {

        final Dimension panelDimension = p.getDimension();
        if (candidate.getDimension().getLength() != panelDimension.getLength()) {
            final CutNode cut = candidate.cut(project.getBladeWidth(), panelDimension.getLength(), CutNode.Direction.VERTICAL);
            candidate = (FreeNode) cut.getTarget();
            assert (candidate.getDimension().getLength() == panelDimension.getLength()) : "cut made incorrect length: " + panelDimension.getLength() + " / " + candidate.getDimension().getLength();
        }

        if (candidate.getDimension().getWidth() != panelDimension.getWidth()) {

            final CutNode cut = candidate.cut(project.getBladeWidth(), panelDimension.getWidth(), CutNode.Direction.HORIZONTAL);
            candidate = (FreeNode) cut.getTarget();
            assert (candidate.getDimension().getWidth() == panelDimension.getWidth());
        }
        return candidate;
    }

    protected boolean almostSameSize(final double a, final double b) {
        final double epsilon = 0.1;
        return Math.abs(a - b) < epsilon;
    }
}
