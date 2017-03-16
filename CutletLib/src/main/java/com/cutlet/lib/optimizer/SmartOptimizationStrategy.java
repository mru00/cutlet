/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.PanelInstance;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.FreeNode;
import com.cutlet.lib.data.cuttree.PanelNode;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class SmartOptimizationStrategy extends AbstractOptimizationStrategy {

    private static final Logger log = Logger.getLogger(SmartOptimizationStrategy.class.getName());

    @Override
    public OptimizationResult optimize(@NonNull Project project, @NonNull FitnessFunction fitness) {

        List<PanelInstance> panels = project.getPanelInstances().stream()
                .sorted((b, a) -> Double.compare(a.getDimension().getArea(), b.getDimension().getArea()))
                .collect(Collectors.toList());

        OptimizationResult optimizationResult = new OptimizationResult();
        
        if (panels.isEmpty()) {
            return optimizationResult;
        }

        Deque<PanelInstance> unassignedPanels = new LinkedList<>();

        for (PanelInstance p : panels) {
            unassignedPanels.add(p);
        }

        final PanelInstance firstPanel = unassignedPanels.peekFirst();

        optimizationResult.createNewLayout(firstPanel);

        while (!unassignedPanels.isEmpty()) {
            PanelInstance panel = unassignedPanels.removeFirst();

            List<FreeNode> candidates = getFreeNodes(optimizationResult).stream()
                    .filter(t -> t.canHold(panel))
                    .collect(Collectors.toList());

            FreeNode candidate = candidates.stream()
                    .filter((t) -> almostSameSize(t.getDimension().getWidth(), panel.getDimension().getWidth()) && almostSameSize(t.getDimension().getLength(), panel.getDimension().getLength()))
                    .findFirst()
                    .orElse(null);

            if (candidate == null) {
                candidate = candidates.stream()
                        .filter((t) -> almostSameSize(t.getDimension().getWidth(),panel.getDimension().getWidth()))
                        .findFirst()
                        .orElse(null);
            }

            if (candidate == null) {
                candidate = candidates.stream()
                        .filter((t) -> almostSameSize(t.getDimension().getLength(), panel.getDimension().getLength()))
                        .findFirst()
                        .orElse(null);
            }

            if (candidate == null) {
                candidate = candidates.stream()
                        .findFirst()
                        .orElse(null);
            }

            if (candidate == null) {

                assert(optimizationResult.getLayouts().size() < 100);
                log.info("failed to find free space on sheet, creating a new layout");
                optimizationResult.createNewLayout(panel);
                // push back p, process it again!
                unassignedPanels.addFirst(panel);
                continue;
            }

            candidate = cutToFit(candidate, project, panel);
            final PanelNode pn = candidate.setPanel(panel);

            log.info(String.format("placed panel %s on layout", pn));
        }

        return optimizationResult;
    }
}
