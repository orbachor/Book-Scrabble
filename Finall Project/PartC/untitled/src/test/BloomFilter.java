package test;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;

public class BloomFilter {
    private final BitSet bitset; // arr of bites
    private final String[] hashAlgs;
    private final int Size;


    public BloomFilter(int size , String...algs) //String[]algs--> k
    {
        Size=size;
        bitset=new BitSet();
        hashAlgs=algs;
    }
    public void add (String st)
    {
        MessageDigest md; // to use alg
        byte[] bts;
        BigInteger place;
        for (String alg: hashAlgs)
        {
            bitset.set(get_index(st,alg)); //turn on the bit
        }
    }

    public boolean contains(String st)
    {
        for (String alg: hashAlgs)
        {
            if(!bitset.get(get_index(st,alg)))
                return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder bin = new StringBuilder();
        for( int i=0; i< bitset.length(); i++)
        {
            bin.append(bitset.get(i)?'1':'0'); // from bitset.get(i) the value is 1 or 0 (true || false).
        }
        return bin.toString();
    }


    public int get_index(String st, String alg)
    {
        MessageDigest md;
        byte[] bts;
        BigInteger place;
        try {
            md=MessageDigest.getInstance(alg); // check if the alg exist
            }
            catch (NoSuchAlgorithmException e)
            {
            throw new RuntimeException("The alg is not exist" + e);
            }
        bts=md.digest(st.getBytes()); // get the bytes that alg create on this specific st
        place=new BigInteger(bts); // get the object BigInteger
        return Math.abs(place.intValue())% Size; // get the int value
    }
}
