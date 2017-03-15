/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.model;

import com.cutlet.lib.optimizer.OptimizationResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class Project implements Serializable {

    private List<Panel> panels = new ArrayList<>();
    private double bladeWidth = 3;
    private Optional<OptimizationResult> optimizationResult;

    public List<Panel> getPanels() {
        return panels;
    }

    public void setPanels(@NonNull final List<Panel> inputs) {
        this.panels = inputs;
    }

    public double getBladeWidth() {
        return bladeWidth;
    }

    public void setBladeWidth(final double bladeWidth) {
        if (bladeWidth < 0) {
            throw new IllegalArgumentException("bladeWidth can't be negative");
        }

        this.bladeWidth = bladeWidth;
    }

    public Optional<OptimizationResult> getOptimizationResult() {
        return optimizationResult;
    }

    public void setOptimizationResult(@NonNull final OptimizationResult optimizationResult) {
        this.optimizationResult.of(optimizationResult);
    }
    
    
}
