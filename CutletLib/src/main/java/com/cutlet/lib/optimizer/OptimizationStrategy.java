/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Project;

/**
 *
 * @author rmuehlba
 */
public interface OptimizationStrategy {
    public OptimizationResult optimize(Project project, FitnessFunction fitness);
}
