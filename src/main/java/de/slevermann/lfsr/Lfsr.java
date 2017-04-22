package de.slevermann.lfsr;

import java.util.Deque;
import java.util.Set;

/**
 * Class representing an LFSR (Linear Feedback Shift Register)
 * The highest order bits and taps are on the right.
 *
 * @author Simon Levermann
 */
public class Lfsr {

    private int size;

    private Set<Integer> taps;

    private FixedBitSet state;

    private FixedBitSet initialState;

    private Deque<Boolean> outputBits;

    public Lfsr(String initialState, Set<Integer> taps) {
        this(new FixedBitSet(initialState), taps);
    }

    public Lfsr(FixedBitSet initialState, Set<Integer> taps) {
        if (initialState.getSize() < 2) {
            throw new IllegalArgumentException("Need at least 2 bits of state");
        }
        if (taps.size() > initialState.getSize()) {
            throw new IllegalArgumentException("Cannot have more taps than state bits");
        }
        if (taps.size() < 2) {
            throw new IllegalArgumentException("Need at least 2 taps for an LFSR");
        }
        this.initialState = initialState;
        this.state = new FixedBitSet(initialState);
        this.size = state.getSize();
    }

    /**
     * Performs a single step in the LFSR.
     * <p>
     * This XORs all the taps into a new value, and pushes that value to the front of the state.
     * The value pushed out at the end of the state is added to the deque of output bits that can be
     * consumed as the key stream.
     */
    public void step() {
        boolean tapped = false;
        for (int i : taps) {
            tapped ^= state.getBit(size - i);
        }
        boolean output = state.pushRight(tapped);
        outputBits.addFirst(output);
    }

    /**
     * Perform multiple steps.
     *
     * @param n amount of steps to perform. Negative values step backwards.
     * @see #step()
     */
    public void step(int n) {
        if (n > 0) {
            for (int i = 0; i < n; i++) step();
        } else {
            for (int i = 0; i < -n; i++) stepBack();
        }
    }

    /**
     * Undo a single step in the LFSR.
     * <p>
     * This method takes the top bit in the output deque and pushes it back into the back of the state.
     * This method is a noop when invoked in the starting position
     */
    public void stepBack() {
        if (!outputBits.isEmpty()) {
            boolean output = outputBits.getFirst();
            state.pushLeft(output);
        }
    }

    /**
     * Undo multiple steps.
     *
     * If the amount of steps would step backwards past the initial state, the LSFR stays in the initial state.
     * @param n amount of steps to undo. Negative values step forwards.
     * @see #stepBack()
     */
    public void stepBack(int n) {
        if (n > 0) {
            for (int i = 0; i < n; i++) stepBack();
        } else {
            for (int i = 0; i < -n; i++) step();
        }
    }
}
