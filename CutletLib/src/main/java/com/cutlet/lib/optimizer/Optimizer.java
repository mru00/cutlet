/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
