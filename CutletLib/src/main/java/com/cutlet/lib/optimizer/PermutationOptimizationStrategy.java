/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.PanelInstance;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.FreeNode;
import com.cutlet.lib.errors.OptimizationFailedException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.lang.reflect.Array;

/**
 *
 * @author rmuehlba
 */
public class PermutationOptimizationStrategy extends AbstractOptimizationStrategy {

    private final Logger log = Logger.getLogger("PermutationOptimizationStrategy");

    @Override
    public OptimizationResult optimize(Project project, FitnessFunction fitness) throws OptimizationFailedException {

        if (project.getPanels().size() > 10) {
            throw new OptimizationFailedException("Permutation calculation for n > 10");
        }
        Permutations<PanelInstance> perm = new Permutations<>(project.getPanels().toArray(new PanelInstance[]{}));

        OptimizationResult bestResult = null;
        double bestFitness = Double.MAX_VALUE;

        int count = 0;
        while (perm.hasNext()) {

            OptimizationResult res = optimizeAux(project, Arrays.asList(perm.next()));
            double curFitness = fitness.fitness(res.getStats());

            if (curFitness < bestFitness) {
                bestFitness = curFitness;
                bestResult = res;
            }
            count++;
        }
        System.out.println("total: " + count);

        return bestResult;
    }

    public OptimizationResult optimizeAux(Project project, List<PanelInstance> panels) {

        OptimizationResult optimizationResult = new OptimizationResult();

//        optimizationResult.createNewLayout(p);
        for (PanelInstance p : panels) {
            FreeNode candidate = findSheet(optimizationResult, p);
            if (candidate == null) {
                log.info("failed to find free space on sheet, creating a new layout");

                optimizationResult.createNewLayout(p);
                candidate = findSheet(optimizationResult, p);
            }

            candidate = cutToFit(candidate, project, p);
            candidate.setPanel(p);
        }

        return optimizationResult;
    }

}


// I took the implementation Permutations from http://stackoverflow.com/a/14444037/1689451
// I am unsure about the license, probably need to go away

class Permutations<E> implements  Iterator<E[]>{

    private E[] arr;
    private int[] ind;
    private boolean has_next;

    public E[] output;//next() returns this array, make it public

    Permutations(E[] arr){
        this.arr = arr.clone();
        ind = new int[arr.length];
        //convert an array of any elements into array of integers - first occurrence is used to enumerate
        Map<E, Integer> hm = new HashMap<E, Integer>();
        for(int i = 0; i < arr.length; i++){
            Integer n = hm.get(arr[i]);
            if (n == null){
                hm.put(arr[i], i);
                n = i;
            }
            ind[i] = n.intValue();
        }
        Arrays.sort(ind);//start with ascending sequence of integers


        //output = new E[arr.length]; <-- cannot do in Java with generics, so use reflection
        output = (E[]) Array.newInstance(arr.getClass().getComponentType(), arr.length);
        has_next = true;
    }

    public boolean hasNext() {
        return has_next;
    }

    /**
     * Computes next permutations. Same array instance is returned every time!
     * @return
     */
    public E[] next() {
        if (!has_next)
            throw new NoSuchElementException();

        for(int i = 0; i < ind.length; i++){
            output[i] = arr[ind[i]];
        }


        //get next permutation
        has_next = false;
        for(int tail = ind.length - 1;tail > 0;tail--){
            if (ind[tail - 1] < ind[tail]){//still increasing

                //find last element which does not exceed ind[tail-1]
                int s = ind.length - 1;
                while(ind[tail-1] >= ind[s])
                    s--;

                swap(ind, tail-1, s);

                //reverse order of elements in the tail
                for(int i = tail, j = ind.length - 1; i < j; i++, j--){
                    swap(ind, i, j);
                }
                has_next = true;
                break;
            }

        }
        return output;
    }

    private void swap(int[] arr, int i, int j){
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public void remove() {

    }
}