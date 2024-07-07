package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class IOSearcher
{
    public static boolean search (String word, String...fileNames) throws IOException
    {
        for(String nameofFile: fileNames)
        {
            BufferedReader reader=new BufferedReader(new FileReader(nameofFile));
            String Line;
            while((Line = reader.readLine())!= null)
                if(Line.contains(word))
                {
                    reader.close();
                    return true;
                }
        }
        return false;
    }
}
