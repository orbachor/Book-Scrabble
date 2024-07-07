package test;

import java.io.BufferedReader;
import java.io.FileReader;
public class Dictionary
{
   private final CacheManager LRUcache; //exist words
   private final CacheManager LFUcache; // not exist words
   private final BloomFilter bl;
   private final String[] Files;
    public Dictionary(String...fileNames)
    {
        LRUcache= new CacheManager(400,new LRU());
        LFUcache= new CacheManager(100,new LFU());
        bl = new BloomFilter(256, "SHA1", "MD5");
        Files=fileNames;
        for (String word: fileNames)
        {
            try {
                FileReader fr = new FileReader(word);
                BufferedReader br = new BufferedReader(fr);
                String line;
                while ((line = br.readLine()) != null) {
                    String[] allWords = line.split(" ");
                    for (String i : allWords) {
                        bl.add(i);
                    }
                }
                fr.close();
                br.close();
            } catch (Exception e) {
                System.out.println("There is not word"+e.getMessage());
            }
        }
    }

    public boolean query (String word)
    {
        return checkWordExistence(word);
    }

    public boolean challenge(String word)
    {
        return searchInTextFiles(word);
    }
    public boolean searchInTextFiles(String word) {
        boolean isFound = false;
        try {
            isFound = IOSearcher.search(word,Files);
        } catch (Exception e) {
            System.out.println("There is not word"+e);
        }
        return isFound;
    }
    public boolean checkWordExistence(String word) {
        if (LRUcache.query(word)) {
            return true;
        }
        if (LFUcache.query(word)) {
            return false;
        }

        if (bl.contains(word))
        {
            LRUcache.add(word);
            return true;
        }
        else
        {
            LFUcache.add(word);
            return false;
        }
    }
}
