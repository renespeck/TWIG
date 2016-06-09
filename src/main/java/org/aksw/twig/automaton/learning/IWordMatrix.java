package org.aksw.twig.automaton.learning;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * A word two dimensional word matrix M. The double value of M_(a, b) is the chance that b is a successor to a by a chance of M_(a, b).
 */
public interface IWordMatrix {

    /**
     * Returns the chance of the {@code predecessor} being followed by the {@code successor}.
     * @param predecessor Predecessor.
     * @param successor Successor.
     * @return Chance.
     * @throws IllegalArgumentException Thrown if there is no mapping for the {@code predecessor}.
     */
    double getChance(String predecessor, String successor) throws IllegalArgumentException;

    /**
     * Get all words that can be successor to the {@code predecessor}. Those successors are mapped to their chance of succeeding.
     * @param predecessor Predecessor.
     * @return Map of successor to succeeding chance.
     */
    Map<String, Double> getMappings(String predecessor);

    /**
     * Saves the word matrix to given file.
     * @param file File to save the matrix in.
     * @throws IOException Thrown during saving process.
     */
    void saveToFile(File file) throws IOException;
}
