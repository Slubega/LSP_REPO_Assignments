package org.howard.edu.lsp.assignment6;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * JUnit 5 test suite for the IntegerSet class.
 * Each public method in IntegerSet has at least one test.
 */
public class IntegerSetTest {

    @Test
    public void testAddContainsLengthAndIsEmpty() {
        IntegerSet set = new IntegerSet();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());

        set.add(1);
        set.add(2);
        set.add(2); // duplicate; should not be added again

        assertFalse(set.isEmpty());
        assertEquals(2, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertFalse(set.contains(3));
    }

    @Test
    public void testClear() {
        IntegerSet set = new IntegerSet();
        set.add(10);
        set.add(20);
        assertEquals(2, set.length());

        set.clear();
        assertTrue(set.isEmpty());
        assertEquals(0, set.length());
    }

    @Test
    public void testEquals() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();
        IntegerSet set3 = new IntegerSet();

        set1.add(1);
        set1.add(2);
        set1.add(3);

        set2.add(3);
        set2.add(2);
        set2.add(1);

        set3.add(1);
        set3.add(2);

        // Same contents, different order -> equal
        assertTrue(set1.equals(set2));
        assertTrue(set2.equals(set1));

        // Different contents -> not equal
        assertFalse(set1.equals(set3));

        // Equality with itself
        assertTrue(set1.equals(set1));

        // Comparison to null and to another type
        assertFalse(set1.equals(null));
        assertFalse(set1.equals("not a set"));
    }

    @Test
    public void testLargestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(10);
        set.add(3);

        assertEquals(10, set.largest());
    }

    @Test
    public void testLargestEmptySetThrows() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, set::largest);
    }

    @Test
    public void testSmallestNormalCase() {
        IntegerSet set = new IntegerSet();
        set.add(5);
        set.add(10);
        set.add(3);

        assertEquals(3, set.smallest());
    }

    @Test
    public void testSmallestEmptySetThrows() {
        IntegerSet set = new IntegerSet();
        assertThrows(IllegalStateException.class, set::smallest);
    }

    @Test
    public void testRemove() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);
        set.add(3);

        set.remove(2);
        assertFalse(set.contains(2));
        assertEquals(2, set.length());

        // Removing an element not present should not change the set
        set.remove(100);
        assertEquals(2, set.length());
    }

    @Test
    public void testUnion() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set2.add(2);
        set2.add(3);

        set1.union(set2);

        // set1 should now contain {1, 2, 3}
        assertEquals(3, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));

        // set2 should be unchanged
        assertEquals(2, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(3));
    }

    @Test
    public void testUnionWithSelfDoesNotBreak() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        // This should not throw or change the set
        set.union(set);

        assertEquals(2, set.length());
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
    }

    @Test
    public void testIntersect() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);
        set1.add(3);

        set2.add(2);
        set2.add(3);
        set2.add(4);

        set1.intersect(set2);

        // Intersection = {2, 3}
        assertEquals(2, set1.length());
        assertTrue(set1.contains(2));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(1));
        assertFalse(set1.contains(4));

        // set2 remains unchanged
        assertEquals(3, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(3));
        assertTrue(set2.contains(4));
    }

    @Test
    public void testIntersectDisjointSets() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);

        set2.add(3);
        set2.add(4);

        set1.intersect(set2);

        // No common elements -> empty
        assertTrue(set1.isEmpty());
    }

    @Test
    public void testDiff() {
        IntegerSet set1 = new IntegerSet();
        IntegerSet set2 = new IntegerSet();

        set1.add(1);
        set1.add(2);
        set1.add(3);

        set2.add(2);
        set2.add(4);

        set1.diff(set2);

        // set1 should now contain {1, 3}
        assertEquals(2, set1.length());
        assertTrue(set1.contains(1));
        assertTrue(set1.contains(3));
        assertFalse(set1.contains(2));

        // set2 unchanged
        assertEquals(2, set2.length());
        assertTrue(set2.contains(2));
        assertTrue(set2.contains(4));
    }

    @Test
    public void testDiffWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        set.diff(set);

        // Difference of a set with itself is empty
        assertTrue(set.isEmpty());
    }

    @Test
    public void testComplement() {
        IntegerSet base = new IntegerSet();
        IntegerSet other = new IntegerSet();

        base.add(1);
        base.add(2);

        other.add(1);
        other.add(2);
        other.add(3);
        other.add(4);

        // After complement, base should be (other \ base) = {3, 4}
        base.complement(other);

        assertEquals(2, base.length());
        assertTrue(base.contains(3));
        assertTrue(base.contains(4));
        assertFalse(base.contains(1));
        assertFalse(base.contains(2));

        // "other" remains unchanged
        assertEquals(4, other.length());
        assertTrue(other.contains(1));
        assertTrue(other.contains(2));
        assertTrue(other.contains(3));
        assertTrue(other.contains(4));
    }

    @Test
    public void testComplementWithSelf() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(2);

        // Complement of a set with itself should be empty (self \ self)
        set.complement(set);
        assertTrue(set.isEmpty());
    }

    @Test
    public void testToString() {
        IntegerSet set = new IntegerSet();
        set.add(1);
        set.add(3);
        set.add(2);

        // Order is insertion order (1, 3, 2), so toString should follow that.
        assertEquals("[1, 3, 2]", set.toString());
    }
}
