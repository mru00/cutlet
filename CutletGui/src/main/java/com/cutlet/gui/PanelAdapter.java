/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.gui;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author rmuehlba
 */
public class PanelAdapter {

    private final SimpleStringProperty name;
    private final SimpleIntegerProperty count;
    private final SimpleDoubleProperty length;
    private final SimpleDoubleProperty width;

    public PanelAdapter(String name, int count, double length, double width) {
        this.name = new SimpleStringProperty(name);
        this.count = new SimpleIntegerProperty(count);
        this.length = new SimpleDoubleProperty(length);
        this.width = new SimpleDoubleProperty(width);
    }

    public String getName() {
        return name.get();
    }
    
    public void setName(String name) {
        this.name.set(name);
    }

    public int getCount() {
        return count.get();
    }

    public double getLength() {
        return length.get();
    }

    public double getWidth() {
        return width.get();
    }

}
