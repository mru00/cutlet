/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
