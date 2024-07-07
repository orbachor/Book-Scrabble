package test;
import java.util.HashSet;
import java.util.Set;


public class CacheManager {
	private final int Size;
    private final CacheReplacementPolicy Crp;
    private  final HashSet<String> cache;
    public CacheManager(int size,CacheReplacementPolicy crp) {
        Size = size;
        Crp =crp;
        cache= new HashSet<>();
    }

    public boolean query(String word)
    {
        return cache.contains(word);
    }

    public void add( String word)
    {
        Crp.add(word);
        cache.add(word);
        if(cache.size()>Size) // update the priority and check if bigger the Size
            cache.remove(Crp.remove());
    }
}

