/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.optimizer;

import com.cutlet.lib.errors.OptimizationFailedException;
import com.cutlet.lib.model.Project;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public interface OptimizationStrategy {
    public OptimizationResult optimize(@NonNull Project project, @NonNull FitnessFunction fitness) throws OptimizationFailedException;
}
