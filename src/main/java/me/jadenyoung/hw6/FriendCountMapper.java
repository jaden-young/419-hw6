package me.jadenyoung.hw6;

import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FriendCountMapper extends Mapper<Object, Text, IntPairWritable, IntegerHashSetWritable> {
    private static final IntPairWritable pair = new IntPairWritable();
    private static final IntegerHashSetWritable set = new IntegerHashSetWritable();

    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        // This was a lot prettier with the Java 8 streams api, but
        // we're running 7.
        String[] words = value.toString().split(" ");
        String meStr = words[0];
        int me = Integer.parseInt(meStr);
        int[] myFriends = new int[words.length - 1];
        for (int i = 1; i < words.length; i++) {
            myFriends[i - 1] = Integer.parseInt(words[i]);
        }

        // for each friend, write out "(me, friend): friends - friend"
        for (int friend : myFriends) {
            set.clear();
            if (me > friend) {
                pair.set(friend, me);
            } else {
                pair.set(me, friend);
            }
            for (int other : myFriends) {
                if (other != friend) {
                    set.add(other);
                }
            }
            context.write(pair, set);
        }
    }
}
