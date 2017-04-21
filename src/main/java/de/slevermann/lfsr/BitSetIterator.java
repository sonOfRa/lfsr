package de.slevermann.lfsr;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BitSetIterator implements Iterator<Boolean> {

    private FixedBitSet bitSet;
    private int pos = 0;

    public BitSetIterator(FixedBitSet bitSet) {
        this.bitSet = bitSet;
    }

    @Override
    public boolean hasNext() {
        return pos < bitSet.getSize();
    }

    @Override
    public Boolean next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Iterated past end of BitSet");
        }
        return bitSet.getBit(pos++);
    }
}
