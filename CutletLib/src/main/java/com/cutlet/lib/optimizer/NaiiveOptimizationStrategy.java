/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.FreeNode;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class NaiiveOptimizationStrategy extends AbstractOptimizationStrategy {

    private final static Logger log = Logger.getLogger(NaiiveOptimizationStrategy.class.getName());

    @Override
    public OptimizationResult optimize(@NonNull final Project project, @NonNull final FitnessFunction fitness) {

        OptimizationResult optimizationResult = new OptimizationResult();

        List<Panel> panels = project.getPanels().stream()
                .sorted((b, a) -> Double.compare(a.getDimension().getLength(), b.getDimension().getLength()))
                .collect(Collectors.toList());

        for (Panel p : panels) {
            FreeNode candidate = findSheet(optimizationResult, p);
            if (candidate == null) {
                optimizationResult.createNewLayout(p);
                candidate = findSheet(optimizationResult, p);
            }

            candidate = cutToFit(candidate, project, p);
            candidate.setPanel(p);
        }

        return optimizationResult;
    }
}
