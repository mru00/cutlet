/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.model;

import java.io.Serializable;
import lombok.NonNull;
import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString
public class Dimension implements Serializable{

    private double length;
    private double width;

    public Dimension(final double length, final double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(final double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(final double width) {
        this.width = width;
    }

    public double getArea() {
        return getLength() * getWidth();
    }

    public boolean canHold(@NonNull final Dimension other) {
        return other.getLength() <= this.getLength() && other.getWidth() <= this.getWidth();
    }    
    
    public Dimension rotated() {
        return new Dimension(width, length);
    }
}
