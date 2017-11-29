package me.jadenyoung.hw6;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.HashSet;

import org.apache.hadoop.io.Writable;

public class IntegerHashSetWritable implements Writable {
    private HashSet<Integer> backingSet;

    public IntegerHashSetWritable() {
        backingSet = new HashSet<Integer>();
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(backingSet.size());
        for (Integer elem : backingSet) {
            out.writeInt(elem);
        }
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        int count = in.readInt();
        for (int i = 0; i < count; i++) {
            int val = in.readInt();
            add(val);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Integer i : backingSet) {
            sb.append(i.toString());
            sb.append(", ");
        }
        sb.append("}");

        return sb.toString();
    }

    public void add(Integer i) {
        backingSet.add(i);
    }

    public HashSet<Integer> getBackingSet() {
        return backingSet;
    }

    public boolean remove(Integer i) {
        return backingSet.remove(i);
    }

    public void retainAll(IntegerHashSetWritable other) {
        backingSet.retainAll(other.getBackingSet());
    }

    public void clear() {
        backingSet.clear();
    }

    public void set(HashSet<Integer> s) {
        backingSet = s;
    }

    public void set(IntegerHashSetWritable s) {
        backingSet = s.getBackingSet();
    }
}