/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Project;
import java.util.logging.Logger;

/**
 *
 * @author rmuehlba
 */
public class Optimizer {

    private Logger log = Logger.getLogger("Optimizer");

    public OptimizationResult optimize(Project project, OptimizationStrategy strategy) {

        if (project.getPanels().isEmpty()) {
            log.info("Project doesn't have any panels configured; nothing to optimize");
            return new OptimizationResult();
        }

        log.info("starting optimization");

        FitnessFunction fitness = new SimpleFitnessFunction();
        OptimizationResult result = strategy.optimize(project, fitness);
        result.finishConstruction();
        log.info(String.format("finished optimization. #sheets=%d, result=%s", result.getLayouts().size(), result));
        return result;
    }

}
