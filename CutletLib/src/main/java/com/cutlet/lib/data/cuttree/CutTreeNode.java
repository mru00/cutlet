/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.data.cuttree;

/**
 *
 * @author rmuehlba
 */
public interface CutTreeNode {
    
    public void replaceChild(CutTreeNode from, CutTreeNode to);
}
