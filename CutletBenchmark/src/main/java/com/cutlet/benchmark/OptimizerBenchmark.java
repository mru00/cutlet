/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.benchmark;

import com.cutlet.lib.errors.OptimizationFailedException;
import com.cutlet.lib.testing.RandomData;
import com.cutlet.lib.testing.Data1;
import com.cutlet.lib.testing.TestData;
import com.cutlet.lib.testing.DataRegal;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.model.Layout;
import com.cutlet.lib.optimizer.GAOptimizationStrategy;
import com.cutlet.lib.optimizer.NaiiveOptimizationStrategy;
import com.cutlet.lib.optimizer.OptimizationResult;
import com.cutlet.lib.optimizer.OptimizationResultStats;
import com.cutlet.lib.optimizer.SmartOptimizationStrategy;
import java.io.PrintStream;
import java.util.logging.Logger;
import com.cutlet.lib.optimizer.OptimizationStrategy;
import com.cutlet.lib.optimizer.Optimizer;
import com.cutlet.lib.optimizer.PermutationOptimizationStrategy;
import com.cutlet.lib.optimizer.SAOptimizationStrategy;
import com.cutlet.lib.optimizer.SAOptimizationStrategy2;
import com.cutlet.lib.optimizer.SimpleFitnessFunction;
import com.cutlet.lib.testing.DataNoPanels;
import com.cutlet.lib.testing.DataTable;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 *
 * @author rmuehlba
 */
public class OptimizerBenchmark {

    private final Logger log = Logger.getLogger("benchmark");

    public static void main(String... args) {

        setDebugLevel(Level.SEVERE);

        System.err.println("benchtmark");

        (new OptimizerBenchmark()).runBenchmarks();
    }

    public void runBenchmarks() {
        log.info("running benchmarks");

        List<TestData> benchmarks = Arrays.asList(
                new Data1(),
                new RandomData(100),
                new RandomData(201),
                new DataRegal(),
                new DataTable(),
                new DataNoPanels()
        );

        List<String> optimizers = Arrays.asList("Smart", "Naiive", "SA", "SA2", "GA" /*, "Perm"*/);

        final PrintStream output = System.err;

        output.println(String.format("%-10s %-13s %10s %10s %10s %10s %10s %10s %10s",
                "Method",
                "dataset",
                "#panels",
                "#sheets",
                "waste%",
                "#cuts",
                "cutlength",
                "fitness",
                "runtime"));

        for (String opt : optimizers) {
            for (TestData b : benchmarks) {
                try {
                    final OptimizationStrategy strategy = createStrategy(opt);
                    final Optimizer optimizer = new Optimizer();
                    final Project project = b.getData();
                    saveProject(project);
                    final OptimizationResult result = optimizer.optimize(project, strategy);

                    final int nPanelsAct = sumNumberOfPanels(result);
                    final int nPanelsExp = project.getPanels().stream().mapToInt(p-> p.getCount()).sum();

                    OptimizationResultStats stats = result.getStats();

                    if (nPanelsAct != nPanelsExp) {
                        throw new OptimizationFailedException(String.format(
                                    "number of panelsActual[%d] != number of panelsExpected[%d] in %s[%s]", 
                                    nPanelsAct, 
                                    nPanelsExp, 
                                    b.getTitle(), 
                                    opt));
                    }

                    output.println(String.format("%-10s %-13s %10d %10d %10.1f %10d %10.0f %10.1f %10.0f",
                                opt,
                                b.getTitle(),
                                nPanelsExp,
                                stats.getNumberOfLayouts(),
                                stats.getWastagePercent(),
                                stats.getNumberOfCuts(),
                                stats.getTotalCutLength(),
                                (new SimpleFitnessFunction()).fitness(stats),
                                result.getRuntime()));

                    //ConsoleOutput.dumpResult(result);
                } catch (OptimizationFailedException ex) {
                    //Logger.getLogger(OptimizerBenchmark.class.getName()).log(Level.SEVERE, null, ex);
                    output.println(String.format("%-10s %-13s failed: %s",
                            opt,
                            b.getTitle(),
                            ex.getMessage()));
                }
            }
        }
    }

    private int sumNumberOfPanels(OptimizationResult r) {
        int i = 0;
        for (Layout l : r.getLayouts()) {
            i += l.getNumberOfPlacedPanels();
        }
        return i;
    }

    private void saveProject(Project project) {
        // just for testing, save and load the project
        try (ObjectOutputStream oos
                = new ObjectOutputStream(new FileOutputStream("project.cutlet"))) {

            oos.writeObject(project);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try (ObjectInput input = new ObjectInputStream(new BufferedInputStream(new FileInputStream("project.cutlet")));) {
            //deserialize the List
            input.readObject();
        } catch (ClassNotFoundException ex) {
            log.log(Level.SEVERE, "Cannot perform input. Class not found.", ex);
        } catch (IOException ex) {
            log.log(Level.SEVERE, "Cannot perform input.", ex);
        }
    }

    public OptimizationStrategy createStrategy(String which) {
        switch (which) {
            case "Smart":
                return new SmartOptimizationStrategy();
            case "Naiive":
                return new NaiiveOptimizationStrategy();
            case "Perm":
                return new PermutationOptimizationStrategy();
            case "SA":
                return new SAOptimizationStrategy();
            case "SA2":
                return new SAOptimizationStrategy2();
            case "GA":
                return new GAOptimizationStrategy();
        }
        throw new RuntimeException("optimizer class for " + which + " not found");
    }

    public static void setDebugLevel(Level newLvl) {
        Logger rootLogger = LogManager.getLogManager().getLogger("");
        Handler[] handlers = rootLogger.getHandlers();
        rootLogger.setLevel(newLvl);
        for (Handler h : handlers) {
            //if (h instanceof FileHandler) {
            h.setLevel(newLvl);
            //}
        }
    }
}
