package test;

import java.util.HashMap;
import java.util.Map;
public class DictionaryManager {
    final private Map<String, Dictionary> dictionaryMap;
    private static final DictionaryManager DM = new DictionaryManager();
    private DictionaryManager()
    {
        dictionaryMap=new HashMap<>();
    }
    public boolean query(String...args)
    {
        boolean exist=false;
        String word= args[args.length-1];
        for (int i=0; i<args.length-1; i++)
        {
            if(!dictionaryMap.containsKey(args[i]))
                dictionaryMap.put(args[i],new Dictionary(args[i]));
            if(dictionaryMap.get(args[i]).query(word))
                exist=true;
        }
        return exist;
    }

    public boolean challenge(String...args)
    {
        boolean exist=false;
        String word= args[(args.length)-1];
        for (int i=0; i<args.length-1; i++)
        {
            if(!dictionaryMap.containsKey(args[i]))
                dictionaryMap.put(args[i],new Dictionary(args[i]));
            if(dictionaryMap.get(args[i]).challenge(word))
                exist=true;
        }
        return exist;
    }
    public int getSize()
    {
        return dictionaryMap.size();
    }
    public static  DictionaryManager get()
    {
        return DM;
    }
}