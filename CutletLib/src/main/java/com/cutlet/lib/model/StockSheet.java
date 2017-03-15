/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.model;

import com.cutlet.lib.material.Material;
import java.io.Serializable;
import lombok.NonNull;
import lombok.ToString;


/**
 *
 * @author rmuehlba
 */
@ToString
public class StockSheet implements Serializable {

    private final Material material;
    private final double thickness;
    private final String title;
    private final Dimension dimension;
    


    public StockSheet(@NonNull final String title, 
            @NonNull final Material material, 
            @NonNull final Dimension dimension, 
            final double thickness) {
        
        this.material = material;
        this.dimension = dimension;
        this.thickness = thickness;
        this.title = title;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public double getThickness() {
        return thickness;
    }
}
