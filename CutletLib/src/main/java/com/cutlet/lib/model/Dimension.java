/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public Dimension(double length, double width) {
        this.length = length;
        this.width = width;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getArea() {
        return getLength() * getWidth();
    }

    public boolean canHold(@NonNull Dimension other) {
        return other.getLength() <= this.getLength() && other.getWidth() <= this.getWidth();
    }    
}
