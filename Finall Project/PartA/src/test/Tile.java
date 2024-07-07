package test;


import java.util.Arrays;
import java.util.Objects;
import java.util.Random;


public class Tile
{
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public static class Bag
    {

        private final int[] frequency_const = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        public int[] frequency = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        protected final char[] letters = {'A','B','C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        protected final int[] scores = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
        private int bagCount =0;
        private static Bag check = null;
        private final Tile[] tile = new Tile[26];

        private Bag() {
            for (int i = 0; i < tile.length; i++) {
                tile[i] = new Tile(letters[i], scores[i]);
            }
            bagCount = Arrays.stream(frequency).sum();
        }

        public Tile getRand() {
            int totalscore = bagCount;       //--- size is method that return sum of tiles---
            if (totalscore == 0)
                return null;

            Random random = new Random();
            int i = random.nextInt(26);
            while (frequency[i] == 0)
            {
                i = random.nextInt(26);
            }
            frequency[i]--;
            return tile[i];
        }

        public Tile getTile(char ch)
        {//use the index
            for (int i = 0; i < 26; i++) {
                if (tile[i].letter == ch && frequency[i] > 0) {
                    frequency[i]--;
                    return tile[i];
                }
            }
            return null;
        }

        public void put(Tile t)// 26 to number
        {
            if (t != null)
            {
                for (int i = 0; i < 26; i++)
                {
                    if (tile[i].letter == t.letter && frequency[i] < frequency_const[i])
                    {

                        frequency[i]++;
                    }
                }
            }
        }

        public int[] getQuantities()
        {
            return frequency.clone();
        }

        public static Bag getBag()
        {
            if (check==null)
            {
                check = new Bag();
            }
            return check;
        }
    }
}
