/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import java.io.Serializable;

/**
 *
 * @author rmuehlba
 */
public class RootNode implements CutTreeNode, Serializable {
    private CutTreeNode child;
    private Dimension dimension;

    public RootNode(Dimension dimension) {
        this.dimension = dimension;
    }
    
    public CutTreeNode getChild() {
        return child;
    }

    public void setChild(final CutTreeNode child) {
        this.child = child;
    }

    @Override
    public void replaceChild(CutTreeNode from, CutTreeNode to) {
        assert (child == from);
        child = to;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
