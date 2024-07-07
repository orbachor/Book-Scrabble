package test;
import java.util.LinkedHashSet;
import java.util.Iterator;

public class LRU implements CacheReplacementPolicy  {
    LinkedHashSet<String> cache;
    public LRU() {
       cache = new LinkedHashSet<>();
    }
    @Override
    public void add(String word)
    {
        cache.remove(word);
        cache.add(word);
    }

    @Override
    public String remove() {

        if(cache.isEmpty())
            return null;
        else {
            Iterator<String> iterator = cache.iterator();
            String NewFirstLink = iterator.next();
            iterator.remove();
            return NewFirstLink;
        }
    }
}
