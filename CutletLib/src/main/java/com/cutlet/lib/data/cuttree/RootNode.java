/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import java.io.Serializable;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class RootNode implements CutTreeNode, Serializable {
    private CutTreeNode child;
    private Dimension dimension;

    public RootNode(@NonNull final Dimension dimension) {
        this.dimension = dimension;
    }
    
    public CutTreeNode getChild() {
        return child;
    }

    public void setChild(@NonNull final CutTreeNode child) {
        this.child = child;
    }

    @Override
    public void replaceChild(@NonNull final CutTreeNode from, @NonNull final CutTreeNode to) {
        assert (child == from);
        child = to;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
