/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.errors.EmptyProjectException;
import com.cutlet.lib.errors.OptimizationFailedException;
import com.cutlet.lib.model.Project;
import com.google.common.base.Stopwatch;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class Optimizer {
    
    private static final Logger log = Logger.getLogger("Optimizer");
    
    public OptimizationResult optimize(@NonNull Project project, @NonNull OptimizationStrategy strategy) throws OptimizationFailedException {
        
        if (project.getPanels().isEmpty()) {
            throw new EmptyProjectException("Project doesn't have any panels configured; nothing to optimize");
        }
        
        log.info("starting optimization");
        
        FitnessFunction fitness = new SimpleFitnessFunction();
        
        Stopwatch stopwatch = Stopwatch.createStarted();
        OptimizationResult result = strategy.optimize(project, fitness);
        result.setRuntime(stopwatch.elapsed(TimeUnit.MILLISECONDS));
        
        log.info(String.format("finished optimization. #sheets=%d, result=%s", result.getLayouts().size(), result));
        return result;
    }
    
}
