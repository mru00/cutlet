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
public class EmptyProjectException extends OptimizationFailedException {

    /**
     * Creates a new instance of <code>EmptyProjectException</code> without
     * detail message.
     */
    public EmptyProjectException() {
    }

    /**
     * Constructs an instance of <code>EmptyProjectException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyProjectException(String msg) {
        super(msg);
    }
}
