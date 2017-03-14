/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.model;

import com.cutlet.lib.material.Material;
import java.io.Serializable;
import lombok.ToString;


/**
 *
 * @author rmuehlba
 */
@ToString
public class StockSheet implements Serializable {

    public double getThickness() {
        return thickness;
    }

    private final Material material;
    private final double thickness;
    private final String title;
    private final Dimension dimension;
    


    public StockSheet(String title, Material material, Dimension dimension, double thickness) {
        this.material = material;
        this.dimension = dimension;
        this.thickness = thickness;
        this.title = title;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
