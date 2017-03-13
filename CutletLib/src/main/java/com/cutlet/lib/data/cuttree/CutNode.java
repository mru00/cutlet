/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Position;




/*
*   ---------------------l--------------------   ^
*   |          ^                             |   |
*   |         t|r           r                |   y
*   |          v          -h->               w
*   |          |            t                |
*   |                                        |
* (0,0)---------------------------------------   x->
 */


/**
 *
 * @author rmuehlba
 */
public class CutNode extends AbstractCutTreeNode {

    public enum Direction {
        VERTICAL, HORIZONTAL,
    };
    
    private CutTreeNode target;
    private CutTreeNode rest;
    
    private final Position cutPos;
    private final Dimension cutDim;
    
    private final double cutLength;

    public CutNode(CutTreeNode parent,
            double kerf, double cutPosition, Direction direction,
            Position position, Dimension dimension) {
        
        super(parent, position, dimension);

        final Position thisPosition = getPosition();
        final Dimension thisDimension = getDimension();
        final Position targetPosition, restPosition;
        final Dimension targetDimension, restDimension;
        
        final double offset = cutPosition + kerf;

        if (direction == Direction.HORIZONTAL) {

            targetDimension = new Dimension(thisDimension.getLength(), cutPosition);
            targetPosition = thisPosition;
            
            restDimension = new Dimension(thisDimension.getLength(), thisDimension.getWidth() - offset);
            restPosition = new Position(thisPosition.getX(), thisPosition.getY() + offset);

            cutPos = new Position(thisPosition.getX(), thisPosition.getY() + cutPosition);
            cutDim = new Dimension(thisDimension.getLength(), kerf);
            cutLength = thisDimension.getLength();
        } else {
            
            targetDimension = new Dimension(cutPosition, thisDimension.getWidth());
            targetPosition = thisPosition;
            
            restDimension = new Dimension(thisDimension.getLength() - offset, thisDimension.getWidth());
            restPosition = new Position(thisPosition.getX() + offset, thisPosition.getY());
            
            cutPos = new Position(thisPosition.getX() + cutPosition, thisPosition.getY());
            cutDim = new Dimension(kerf, thisDimension.getWidth());
            cutLength = thisDimension.getWidth();
        }

        this.target = new FreeNode(this, targetPosition, targetDimension);
        this.rest = new FreeNode(this, restPosition, restDimension);
    }

    public CutTreeNode getTarget() {
        return target;
    }

    public CutTreeNode getRest() {
        return rest;
    }

    public double getCutLength() {
        return cutLength;
    }
    
    @Override
    public void replaceChild(CutTreeNode from, CutTreeNode to) {
        if (from == target) {
            target = to;
        } else if (from == rest) {
            rest = to;
        } else {
            throw new RuntimeException("tried to replace a non-owned child");
        }
    }

    public Position getCutPos() {
        return cutPos;
    }

    public Dimension getCutDim() {
        return cutDim;
    }

}
