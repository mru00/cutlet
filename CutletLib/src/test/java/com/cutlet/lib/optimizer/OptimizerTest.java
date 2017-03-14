/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Project;
import com.cutlet.lib.testing.DataRegal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rmuehlba
 */
public class OptimizerTest {
    
    public OptimizerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of optimize method, of class Optimizer.
     */
    @org.junit.Test
    public void testOptimize() {
        System.out.println("optimize");
        Project project = (new DataRegal()).getData();
        OptimizationStrategy strategy = new SAOptimizationStrategy();
        Optimizer instance = new Optimizer();
        OptimizationResult result = instance.optimize(project, strategy);
        //("The test case is a prototype.");
    }
    
}
