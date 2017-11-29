package me.jadenyoung.hw6;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.WritableComparable;

public class IntPairWritable implements WritableComparable<IntPairWritable> {

    private int left;
    private int right;

    public IntPairWritable() {
    }

    public IntPairWritable(int left, int right) {
        this.left = left;
        this.right = right;
    }

    public IntPairWritable(IntWritable left, IntWritable right) {
        this.left = left.get();
        this.right = right.get();
    }

    public void readFields(DataInput in) throws IOException {
        this.left = in.readInt();
        this.right = in.readInt();
    }

    public void write(DataOutput out) throws IOException {
        out.writeInt(left);
        out.writeInt(right);
    }

    public void set(int left, int right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "(" + String.valueOf(left) + ", " + String.valueOf(right) + ")";
    }

    @Override
    public int hashCode() {
        return new Integer(left).hashCode() + new Integer(right).hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IntPairWritable) {
            IntPairWritable ipw = (IntPairWritable) o;
            return ipw.left == ipw.left && ipw.right == ipw.right;
        }
        return false;
    }

    public int compareTo(IntPairWritable ipw) {
        int cmp = Integer.compare(left, ipw.left);
        if (cmp != 0) {
            return cmp;
        }
        return Integer.compare(right, ipw.right);
    }

}
