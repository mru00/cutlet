/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.model;

import com.cutlet.lib.optimizer.OptimizationResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class Project implements Serializable {

    private List<Panel> panels = new ArrayList<>();
    private double bladeWidth = 3;
    private OptimizationResult optimizationResult = null;

    public List<Panel> getPanels() {
        return panels;
    }

    public void setPanels(@NonNull List<Panel> inputs) {
        this.panels = inputs;
    }

    public double getBladeWidth() {
        return bladeWidth;
    }

    public void setBladeWidth(double bladeWidth) {
        if (bladeWidth < 0) {
            throw new IllegalArgumentException("bladeWidth can't be negative");
        }

        this.bladeWidth = bladeWidth;
    }

    public OptimizationResult getOptimizationResult() {
        return optimizationResult;
    }

    public void setOptimizationResult(OptimizationResult optimizationResult) {
        this.optimizationResult = optimizationResult;
    }
    
    
}
