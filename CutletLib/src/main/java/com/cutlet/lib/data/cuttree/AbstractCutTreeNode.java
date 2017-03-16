/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.PanelInstance;
import com.cutlet.lib.model.Position;
import java.io.Serializable;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public abstract class AbstractCutTreeNode implements CutTreeNode, Serializable {

    private final CutTreeNode parent;

    private final Position position;
    private final Dimension dimension;

    public AbstractCutTreeNode(@NonNull final CutTreeNode parent, 
            @NonNull final Position position, 
            @NonNull final Dimension dimension) {
        
        this.dimension = dimension;
        this.position = position;
        this.parent = parent;
    }

    public boolean canHold(@NonNull final PanelInstance p) {
        return getDimension().canHold(p.getDimension());
    }

    public CutTreeNode getParent() {
        return parent;
    }

    public Position getPosition() {
        return position;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
