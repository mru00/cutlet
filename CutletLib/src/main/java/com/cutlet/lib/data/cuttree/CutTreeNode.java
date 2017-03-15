/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.data.cuttree;

import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public interface CutTreeNode {
    
    public void replaceChild(@NonNull final  CutTreeNode from, @NonNull final  CutTreeNode to);
}
