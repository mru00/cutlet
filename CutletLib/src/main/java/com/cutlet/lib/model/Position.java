/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.model;

import java.io.Serializable;
import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString
public class Position implements Serializable {

    private double x, y;

    public Position(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(final double y) {
        this.y = y;
    }

}
