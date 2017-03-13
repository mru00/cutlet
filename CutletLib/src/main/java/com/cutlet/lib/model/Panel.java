/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cutlet.lib.model;

import java.io.Serializable;
import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString(exclude="sheet")
public class Panel implements Serializable {

    private final String title;
    private final StockSheet sheet;
    private final Dimension dimension;

    public String getTitle() {
        return title;
    }

    public Panel(StockSheet sheet, Dimension dimension, String title) {
        this.sheet = sheet;
        this.title = title;
        this.dimension = dimension;
        
        if (getDimension().getWidth() > sheet.getDimension().getWidth() || getDimension().getLength() > sheet.getDimension().getLength()) {
            throw new RuntimeException("panel too large for sheet");
        }
    }

    public StockSheet getSheet() {
        return sheet;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
