package org.howard.edu.lsp.assignment6;

import java.util.ArrayList;
import java.util.List;

/**
 * IntegerSet represents a mathematical set of integers.
 * <p>
 * The set:
 * <ul>
 *   <li>Contains no duplicate elements</li>
 *   <li>Supports standard set operations (union, intersection, difference, complement)</li>
 *   <li>Uses an {@link java.util.ArrayList} for internal storage</li>
 * </ul>
 */
public class IntegerSet  {
    /**
     * Internal list that stores the unique integer elements of the set.
     */
    private List<Integer> set = new ArrayList<Integer>();

    /**
     * Clears the internal representation of the set, removing all elements.
     */
    public void clear() {
        set.clear();
    }

    /**
     * Returns the number of elements in the set.
     *
     * @return the size of the set
     */
    public int length() {
        return set.size();
    }

    /**
     * Compares this set to another object for equality.
     * Two IntegerSet instances are equal if they contain exactly the same values,
     * regardless of order.
     *
     * @param o the object to compare with
     * @return true if the two sets contain the same elements, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        // Same reference -> equal
        if (this == o) {
            return true;
        }

        // Null or different class -> not equal
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IntegerSet other = (IntegerSet) o;

        // Quick length check (no duplicates, so sizes must match)
        if (this.length() != other.length()) {
            return false;
        }

        // Same contents in any order
        return this.set.containsAll(other.set) && other.set.containsAll(this.set);
    }

    /**
     * Returns true if the set contains the specified value, false otherwise.
     *
     * @param value the value to look for
     * @return true if value is in the set, false otherwise
     */
    public boolean contains(int value) {
        return set.contains(value);
    }

    /**
     * Returns the largest item in the set.
     *
     * @return the maximum integer in the set
     * @throws IllegalStateException if the set is empty
     */
    public int largest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot determine largest element of an empty set.");
        }

        int max = set.get(0);
        for (int value : set) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    /**
     * Returns the smallest item in the set.
     *
     * @return the minimum integer in the set
     * @throws IllegalStateException if the set is empty
     */
    public int smallest() {
        if (set.isEmpty()) {
            throw new IllegalStateException("Cannot determine smallest element of an empty set.");
        }

        int min = set.get(0);
        for (int value : set) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    /**
     * Adds an item to the set if it is not already present.
     * If the item is already in the set, this method does nothing.
     *
     * @param item the integer to add
     */
    public void add(int item) {
        if (!set.contains(item)) {
            set.add(item);
        }
    }

    /**
     * Removes an item from the set if present.
     * If the item is not in the set, this method does nothing.
     *
     * @param item the integer to remove
     */
    public void remove(int item) {
        // Remove by value, not by index
        set.remove(Integer.valueOf(item));
    }

    /**
     * Modifies this set so that it becomes the union of this set and another set.
     * After this call, this set contains all unique elements that are in
     * either this set or the other set.
     *
     * @param other the other IntegerSet to union with
     */
    public void union(IntegerSet other) {
        if (other == null) {
            return;
        }

        // Union with itself doesn't change anything
        if (this == other) {
            return;
        }

        for (Integer value : other.set) {
            if (!this.set.contains(value)) {
                this.set.add(value);
            }
        }
    }

    /**
     * Modifies this set so that it becomes the intersection of this set and another set.
     * After this call, this set contains only the elements that appear in both sets.
     *
     * @param other the other IntegerSet to intersect with
     */
    public void intersect(IntegerSet other) {
        if (other == null) {
            return;
        }

        // Intersection with itself is itself
        if (this == other) {
            return;
        }

        // retainAll keeps only elements that are also in other.set
        set.retainAll(other.set);
    }

    /**
     * Modifies this set so that it becomes the set difference (this \ other).
     * After this call, this set contains elements that are in this set but not in other.
     *
     * @param other the other IntegerSet whose elements will be removed from this set
     */
    public void diff(IntegerSet other) {
        if (other == null) {
            return;
        }

        // Difference with itself -> empty set
        if (this == other) {
            clear();
            return;
        }

        set.removeAll(other.set);
    }

    /**
     * Modifies this set so that it becomes the complement (other \ this).
     * After this call, this set contains elements that are in other but not in this set.
     *
     * @param other the IntegerSet used as the base for the complement
     */
    public void complement(IntegerSet other) {
        if (other == null) {
            // complement relative to "nothing" makes no sense; we treat as empty
            clear();
            return;
        }

        // Take a snapshot of the current contents of this set
        List<Integer> originalThis = new ArrayList<Integer>(this.set);

        // Start with all elements of "other"https://chatgpt.com/c/6913e4c9-98e8-8332-aac8-5dbb7458b538
        List<Integer> newSet = new ArrayList<Integer>(other.set);

        // Remove anything that appears in the original "this"
        newSet.removeAll(originalThis);

        // Replace internal list with the new one
        this.set = newSet;
    }

    /**
     * Returns true if the set has no elements, false otherwise.
     *
     * @return true if this set is empty, false otherwise
     */
    public boolean isEmpty() {
        return set.isEmpty();
    }

    /**
     * Returns a String representation of the set.
     * Format: elements in square brackets, separated by commas and spaces,
     * e.g., {@code [1, 2, 3]}.
     *
     * @return a string representation of this set
     */
    @Override
    public String toString() {
        // ArrayList's toString already formats as [e1, e2, e3]
        return set.toString();
    }
}
