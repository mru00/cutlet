/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
