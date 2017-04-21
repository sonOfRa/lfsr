package de.slevermann.lfsr;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;


/**
 * Tests for FixedBitSet
 */
public class FixedBitSetTest {

    private FixedBitSet b10101;
    private FixedBitSet b01010;
    private FixedBitSet b11111;
    private FixedBitSet b00000;
    private FixedBitSet b11000;

    @BeforeMethod
    public void setUp() throws Exception {
        b10101 = new FixedBitSet("10101");
        b01010 = new FixedBitSet("01010");
        b11111 = new FixedBitSet("11111");
        b00000 = new FixedBitSet("00000");
        b11000 = new FixedBitSet("11000");
    }

    @Test
    public void testConstructor() throws Exception {
        FixedBitSet bitSet = new FixedBitSet(5);
        Assert.assertEquals(bitSet.getSize(), 5, "Constructor should set the correct size");
        for (Boolean bit : bitSet) {
            Assert.assertFalse(bit, "All bits should be false in default constructor");
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorEmpty() throws Exception {
        FixedBitSet bitSet = new FixedBitSet(0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testConstructorNegative() throws Exception {
        FixedBitSet bitSet = new FixedBitSet(-1);
    }

    @Test
    public void testStringConstructor() throws Exception {
        FixedBitSet bitSet = new FixedBitSet("00000");
        for (Boolean bit : bitSet) {
            Assert.assertFalse(bit, "All bits should be false in bit string '00000'");
        }
        FixedBitSet bitSet2 = new FixedBitSet("11111");
        for (Boolean bit : bitSet2) {
            Assert.assertTrue(bit, "All bits should be true in bit string '11111'");
        }
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testStringConstructorEmpty() throws Exception {
        FixedBitSet bitSet = new FixedBitSet("");
    }

    @Test(expectedExceptions = NumberFormatException.class)
    public void testStringConstructorNonBinary() {
        FixedBitSet bitSet = new FixedBitSet("1234");
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testSetBitNegative() throws Exception {
        b00000.setBit(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testSetBitOutOfRange() throws Exception {
        b00000.setBit(5);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetBitNegative() throws Exception {
        b00000.getBit(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testGetBitOutOfRange() throws Exception {
        b00000.getBit(5);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testClearBitNegative() throws Exception {
        b00000.clearBit(-1);
    }

    @Test(expectedExceptions = IndexOutOfBoundsException.class)
    public void testClearBitOutOfRange() throws Exception {
        b00000.clearBit(5);
    }

    @Test
    public void testSetBit() throws Exception {
        b00000.setBit(1);
        Assert.assertEquals(b00000, new FixedBitSet("00010"));
    }

    @Test
    public void testSetBitNoOp() throws Exception {
        b01010.setBit(1);
        Assert.assertEquals(b01010, new FixedBitSet("01010"));
    }

    @Test
    public void testClearBit() throws Exception {
        b11111.clearBit(1);
        Assert.assertEquals(b11111, new FixedBitSet("11101"));
    }

    @Test
    public void testClearBitNoOp() throws Exception {
        b10101.clearBit(1);
        Assert.assertEquals(b10101, new FixedBitSet("10101"));
    }

    @Test
    public void testPushLeftZero() throws Exception {
        boolean out = b11000.pushLeft(false);
        Assert.assertFalse(out, "Pushed out bit should be zero");
        Assert.assertEquals((Object) b11000, new FixedBitSet("01100"));
    }

    @Test()
    public void testPushRightZero() throws Exception {
        boolean out = b11000.pushRight(false);
        Assert.assertTrue(out, "Pushed out bit should be one");
        Assert.assertEquals((Object) b11000, new FixedBitSet("10000"));
    }

    @Test
    public void testPushLeftOne() throws Exception {
        boolean out = b11000.pushLeft(true);
        Assert.assertFalse(out, "Pushed out bit should be zero");
        Assert.assertEquals((Object) b11000, new FixedBitSet("11100"));
    }

    @Test
    public void testPushRightOne() throws Exception {
        boolean out = b11000.pushRight(true);
        Assert.assertTrue(out, "Pushed out bit should be zero");
        Assert.assertEquals((Object) b11000, new FixedBitSet("10001"));
    }

}