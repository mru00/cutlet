/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cutlet.lib.errors;

/**
 *
 * @author rmuehlba
 */
public class OptimizationFailedException extends Exception {

    /**
     * Creates a new instance of <code>OptimizationFailedException</code>
     * without detail message.
     */
    public OptimizationFailedException() {
    }

    /**
     * Constructs an instance of <code>OptimizationFailedException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public OptimizationFailedException(String msg) {
        super(msg);
    }
    
    public OptimizationFailedException(Throwable other) {
        super(other);
    }
}
 