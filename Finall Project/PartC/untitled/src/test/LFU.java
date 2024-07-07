package test;

import java.util.HashMap;
import java.util.PriorityQueue;

public class LFU implements CacheReplacementPolicy {

    PriorityQueue<String> cache;
    HashMap<String,Integer> frequency; // String = value Integer = Frequency
    public LFU() {

        frequency = new HashMap<>();
        cache = new PriorityQueue<>((word1, word2) -> {
            //System.out.println("the first var before enqueue is :-->"  +cache.peek());
            //System.out.println("the first word:"+word1);
            //System.out.println("the second word:"+word2);
            int sum= frequency.get(word1) - frequency.get(word2);
            //System.out.println("the sum is:"+sum);
            //System.out.println("the first var after enqueue is :-->"  +cache.peek());
            //System.out.println(" ");
            return sum;
        });

//            lfu.add("a");
//            lfu.add("b");
//            lfu.add("b");
//            lfu.add("c");
//            lfu.add("a");
//            lfu.add("c");
    }

    @Override
    public void add(String word)
    {
        frequency.put(word, frequency.getOrDefault(word, 0) + 1);
        cache.remove(word); // Remove to update position based on updated frequency
        cache.add(word); // Add back to update position
    }

    @Override
    public String remove() {
        if (cache.isEmpty()) {
            return null;
        }
        String removedWord = cache.poll(); // Remove the least frequently used word
        frequency.remove(removedWord); // Update frequency map
        return removedWord;
    }
}

