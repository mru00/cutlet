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
@ToString(exclude = "sheet")
public class PanelInstance implements Serializable {

    private final String title;
    private final StockSheet sheet;
    private final Dimension dimension;
    private final boolean canRotate;
    private final int instanceId;

    public String getTitle() {
        return title;
    }

    public PanelInstance(@NonNull final StockSheet sheet,
            @NonNull final Dimension dimension,
            @NonNull final String title,
            final int instanceId,
            final boolean canRotate) {

        this.sheet = sheet;
        this.title = title;
        this.dimension = dimension;
        this.canRotate = canRotate;
        this.instanceId = instanceId;

        if (dimension.getWidth() > sheet.getDimension().getWidth() || dimension.getLength() > sheet.getDimension().getLength()) {
            throw new RuntimeException("panel too large for sheet");
        }
    }

    public StockSheet getSheet() {
        return sheet;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public int getInstanceId() {
        return instanceId;
    }
}
