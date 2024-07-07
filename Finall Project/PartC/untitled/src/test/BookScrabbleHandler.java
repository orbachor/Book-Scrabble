package test;

import java.io.*;

public class BookScrabbleHandler implements ClientHandler {
    BufferedReader in;
    PrintWriter out;

    @Override
    public void handleClient(InputStream inFromclient, OutputStream outToClient)
    {
        try{
            in =new BufferedReader(new InputStreamReader(inFromclient));
            out= new PrintWriter(outToClient,true); //Flush because we talk with client

            String line;
            boolean result;
            while ((line=in.readLine())!=null)
            {
                    String[] allWords=line.split(",");
                    if (allWords[0].equals("C")) {
                        DictionaryManager DM = DictionaryManager.get();
                        result = DM.query(allWords);
                        if (result)
                            out.println("true");
                        else
                            out.println("false");
                    }
                    else if(allWords[0].equals("Q"))
                    {
                        DictionaryManager DM = DictionaryManager.get();
                        result = DM.challenge(allWords);
                        if (result)
                            out.println("true");
                        else
                            out.println("false");
                    }
                    in.close();
                    out.close();
            }
        } catch (IOException e) {}
    }

    @Override
    public void close() {
        try {
            if (in != null)
                in.close();
            if (out != null)
                out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
