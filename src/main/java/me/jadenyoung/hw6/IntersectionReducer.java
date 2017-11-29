package me.jadenyoung.hw6;

import java.io.IOException;
import java.util.Iterator;

import org.apache.hadoop.mapreduce.Reducer;

public class IntersectionReducer
        extends Reducer<IntPairWritable, IntegerHashSetWritable, IntPairWritable, IntegerHashSetWritable> {
    private final static IntegerHashSetWritable set = new IntegerHashSetWritable();

    public void reduce(IntPairWritable key, Iterable<IntegerHashSetWritable> values, Context context)
            throws IOException, InterruptedException {

        set.clear();
        Iterator<IntegerHashSetWritable> itr = values.iterator();
        set.set(itr.next());

        while (itr.hasNext()) {
            set.retainAll(itr.next());
        }

        context.write(key, set);
    }
}
