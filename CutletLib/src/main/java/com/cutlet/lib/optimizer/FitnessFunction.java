/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.optimizer;

/**
 *
 * @author rmuehlba
 */
@FunctionalInterface
public interface FitnessFunction {
    public double fitness(OptimizationResultStats result);
}
