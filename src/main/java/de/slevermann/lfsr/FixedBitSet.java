package de.slevermann.lfsr;

import lombok.Getter;

import java.math.BigInteger;
import java.util.stream.IntStream;

/**
 * A BitSet with a fixed size that allows shifting and rotating.
 *
 * @author Simon Levermann
 */
public class FixedBitSet implements Iterable<Boolean> {

    private BigInteger state;

    @Getter
    private final int size;

    public FixedBitSet(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.size = size;
        this.state = BigInteger.ZERO;
    }

    public FixedBitSet(String binary) {
        if (binary.isEmpty()) {
            throw new IllegalArgumentException("Size must be > 0");
        }
        this.size = binary.length();
        this.state = new BigInteger(binary, 2);
    }

    public FixedBitSet(FixedBitSet fixedBitSet) {
        this.size = fixedBitSet.size;
        this.state = fixedBitSet.state;
    }

    public BitSetIterator iterator() {
        return new BitSetIterator(this);
    }

    /**
     * @param position
     * @return the bit at the given position
     * @throws IndexOutOfBoundsException if pos >= size
     */
    public boolean getBit(int position) {
        if (position >= size || position < 0) {
            throw new IndexOutOfBoundsException("Index " + position + " is out of bounds for size " + size);
        }
        return state.testBit(position);
    }

    /**
     * @param position
     */
    public void setBit(int position) {
        if (position >= size || position < 0) {
            throw new IndexOutOfBoundsException("Index " + position + " is out of bounds for size " + size);
        }
        state = state.setBit(position);
    }

    /**
     * @param position
     */
    public void clearBit(int position) {
        if (position >= size || position < 0) {
            throw new IndexOutOfBoundsException("Index " + position + " is out of bounds for size " + size);
        }
        state = state.clearBit(position);
    }

    /**
     * Pushes the bit in on the left, pushing the rightmost bit out
     *
     * @param in the bit to push in
     * @return the formerly rightmost bit
     */
    public boolean pushLeft(boolean in) {
        boolean rightMost = state.testBit(0);
        state = state.shiftRight(1);
        if (in) {
            state = state.setBit(size - 1);
        }
        return rightMost;
    }

    /**
     * Pushes the bit in on the right, pushing the leftmost bit out
     *
     * @param in the bit to push in
     * @return the formerly leftmost bit
     */
    public boolean pushRight(boolean in) {
        boolean leftMost = state.testBit(size - 1);
        state = state.shiftLeft(1);
        if (in) {
            state = state.setBit(0);
        }
        return leftMost;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("size: " + size + ", ");
        for (int i = size - 1; i >= 0; i--) {
            out.append(getBit(i) ? '1' : '0');
        }
        return out.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FixedBitSet bitSet = (FixedBitSet) o;

        if (size != bitSet.size) return false;
        return IntStream.range(0, size).noneMatch(i -> getBit(i) != bitSet.getBit(i));
    }

    @Override
    public int hashCode() {
        int result = 0;
        for (Boolean bit : this) {
            result = 31 * result + bit.hashCode();
        }
        result = 31 * result + size;
        return result;
    }
}
