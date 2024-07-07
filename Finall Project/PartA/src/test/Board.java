package test;
import java.util.ArrayList;

public class Board {
    private final int SIZE = 15;
    public Tile[][] board;
    public Character[][] Type;
    ArrayList<Word> allWords; //Set
    private static Board b = null;
    public boolean isEmpty;

    private Board() {
        allWords = new ArrayList<>();
        board = new Tile[SIZE][SIZE];

        isEmpty = true;
        Type = new Character[][]{
                {'r', 'g', 'g', 'p', 'g', 'g', 'g', 'r', 'g', 'g', 'g', 'p', 'g', 'g', 'r'},
                {'g', 'y', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'y', 'g'},
                {'g', 'g', 'y', 'g', 'g', 'g', 'p', 'g', 'p', 'g', 'g', 'g', 'y', 'g', 'g'},
                {'p', 'g', 'g', 'y', 'g', 'g', 'g', 'p', 'g', 'g', 'g', 'y', 'g', 'g', 'p'},
                {'g', 'g', 'g', 'g', 'y', 'g', 'g', 'g', 'g', 'g', 'y', 'g', 'g', 'g', 'g'},
                {'g', 'b', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'b', 'g'},
                {'g', 'g', 'p', 'g', 'g', 'g', 'p', 'g', 'p', 'g', 'g', 'g', 'p', 'g', 'g'},
                {'r', 'g', 'g', 'p', 'g', 'g', 'g', 's', 'g', 'g', 'g', 'p', 'g', 'g', 'r'},
                {'g', 'g', 'p', 'g', 'g', 'g', 'p', 'g', 'p', 'g', 'g', 'g', 'p', 'g', 'g'},
                {'g', 'b', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'b', 'g'},
                {'g', 'g', 'g', 'g', 'y', 'g', 'g', 'g', 'g', 'g', 'y', 'g', 'g', 'g', 'g'},
                {'p', 'g', 'g', 'y', 'g', 'g', 'g', 'p', 'g', 'g', 'g', 'y', 'g', 'g', 'p'},
                {'g', 'g', 'y', 'g', 'g', 'g', 'p', 'g', 'p', 'g', 'g', 'g', 'y', 'g', 'g'},
                {'g', 'y', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'b', 'g', 'g', 'g', 'y', 'g'},
                {'r', 'g', 'g', 'p', 'g', 'g', 'g', 'r', 'g', 'g', 'g', 'p', 'g', 'g', 'r'}
                // s-star- x2 word
                // p-pale blue- x2 char
                // r-red- x3 word
                // b-blue- x3 char
                // y-yellow- x2 word
        };
    }

    public static Board getBoard() {
        if (b == null) {
            b = new Board();
        }
        return b;
    }

    public Tile[][] getTiles() {
        Tile[][] temptile = new Tile[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++)
            System.arraycopy(board[i], 0, temptile[i], 0, SIZE);
        return temptile;
    }


    //-----------------------------------------BoardLegal + methods----------------------------------//

    public boolean boardLegal(Word w) {
        if (w == null)
            return false;
        if (!inboard(w))
            return false;
        if (isEmpty) // board is empty?
        {
            return starCheck(w);
        } else return NearByTiles(w);
    }

    private boolean starCheck(Word w) {
        if (w.vertical ? w.getCol() != 7 : w.getRow() != 7)
            return false;
        if (w.vertical && (w.getRow() < 7) && (w.getRow() + w.tiles.length < 7))// vertical
            return false;
        // Horizontal
        return w.vertical || (w.getCol() >= 7) || (w.getCol() + w.tiles.length >= 7);
    }

    public boolean inboard(Word w) {
        if (!w.vertical) // horizontal
        {
            return w.getRow() >= 0 && (w.getCol() + w.tiles.length - 1) < SIZE && w.getCol() >= 0 && w.getRow() < SIZE;
        } else // vertical
        {
            return w.getRow() >= 0 && (w.getRow() + w.tiles.length - 1) < SIZE && w.getCol() >= 0 && w.getCol() < SIZE;
        }
    }

    public boolean leftSideHoriziontal(Word w) //there is char on the left side
    {
        if (w.getCol() > 0) {
            int col = w.getCol();
            return board[w.getRow()][col - 1] != null;
        }
        return false;
    }

    public boolean rightSideHoriziontal(Word w) //there is char on the right side
    {
        int col = w.getCol();
        int length = w.tiles.length;
        if (col + length + 1 < SIZE) {
            return board[w.getRow()][col + length] != null;
        }
        return false;
    }

    public boolean AboveVertical (Word w) //there is char above me
    {
        if (w.getRow() > 0) {
            return board[w.getRow() - 1][w.getCol()] != null;
        }
        return false;
    }

    public boolean DownVertical(Word w) //there is char down me
    {
        if (w.getRow() < SIZE - 1) {
            return board[w.getRow() + w.tiles.length][w.getCol()] != null;
        }
        return false;
    }

    public boolean InitVertical(Word w) {
        int row = w.getRow();
        int col = w.getCol();
        int length = w.tiles.length;

        for (int i = 0; i < length; i++) {
            if( row+i < SIZE) {
                if ((w.tiles[i] == null && board[row + i][col] == null) || (w.tiles[i] != null && board[row + i][col] != null && w.tiles[i] != board[row+i][col]))
                    return false;
            }
        }
        return true;
    }

    public boolean InitHorizontal(Word w) // if there is word in w tile's place and legal
    {
        int row = w.getRow();
        int col = w.getCol();
        int length = w.tiles.length;

        for (int i = 0; i < length; i++) {
            if( col+i < SIZE ) {
                if ((w.tiles[i] == null && board[row][col + i] == null) || (w.tiles[i] != null && board[row][col + i] != null && w.tiles[i] != board[row][col +i]))
                    return false;
            }
        }
        return true;
    }
    public boolean above_downHorizontal(Word w) {
        if (w.getRow() == 0) // ------------------------------down of Horizontal in row=0
        {
            for (int i = 0; i < w.tiles.length; i++) {
                if (board[w.getRow() + 1][w.getCol() + i] != null) {
                    return true;
                }
            }
        }
        if (w.getRow() == SIZE - 1) //---------------------above of Horiziontal in row = size
        {
            for (int i = 0; i < w.tiles.length; i++) {
                if (b.board[w.getRow() - 1][w.getCol() + i] != null) {
                    return true;
                }
            }
        }

        if ((w.getRow() > 0 && w.getRow() < SIZE - 1)) {
            for (int i = 0; i < w.tiles.length; i++) {
                if ((board[w.getRow() + 1][w.getCol() + i] != null) || (board[w.getRow() - 1][w.getCol() + i] != null)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean Right_LeftVertical(Word w) {
        if (w.getCol() == 0) // ------------------------------right of Vertical in Col=0
        {
            for (int i = 0; i < w.tiles.length; i++) {
                if (board[w.getRow() + i][w.getCol() + 1] != null) {
                    return true;
                }
            }
        }

        if (w.getCol() == SIZE - 1) //---------------------left of Vertical in col = size
        {
            for (int i = 0; i < w.tiles.length; i++) {
                if (b.board[w.getRow() + i][w.getCol() - 1] != null) {
                    return true;
                }
            }
        }

        if ((w.getCol() > 0 && w.getCol() < SIZE - 1)) {
            for (int i = 0; i < w.tiles.length; i++) {
                if ((board[w.getRow() + i][w.getCol() + 1] != null) || (board[w.getRow() + i][w.getCol() - 1] != null)) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean NearByTiles(Word w) {
        if (!w.vertical) // horizontal
        {
            if (!InitHorizontal(w)) return false;
            if (leftSideHoriziontal(w)) return true;
            if (rightSideHoriziontal(w)) return true;
            return above_downHorizontal(w);
        } else // vertical
        {
            if (!InitVertical(w)) return false;
            if (AboveVertical(w)) return true;
            if (DownVertical(w)) return true;
            return Right_LeftVertical(w);
        }
    }

    //-------------------------------------End of BoardLegal + methods----------------------------------//

    public boolean dictionaryLegal(Word w) {
        return true;
    }

    //----------------------------------------getWords + methods ----------------------------------//


    public ArrayList<Word> getWords(Word w) {
        ArrayList<Word> wordslist = new ArrayList<>();
        if (boardLegal(w)) {
            wordslist.add(w);
        }
        int row = w.getRow();
        int col = w.getCol();
        int length = w.tiles.length;
        if (w.vertical) // vertical
        {
            if (row > 0 && board[row - 1][col] != null)
            {
                Word temp=FindVertically(row, col,w.tiles[0],w);
                if(NotExists(wordslist,temp)) wordslist.add(temp);
            }
            if (row + length < SIZE - 1 && board[row + length][col] != null)
            {
                Word temp=FindVertically(row + length, col,w.tiles[length-1],w);
                if(NotExists(wordslist,temp)) wordslist.add(temp);
            }

            if (col < SIZE - 1) //--------------------------- check the right side
            {
                for (int i = 0; i < length; i++)
                {
                    if (board[row+i][col + 1] != null)
                    {
                        Word temp=FindHorizontally(row + i, col,w.tiles[i],w);
                        if(NotExists(wordslist,temp)) wordslist.add(temp);
                    }
                }
            }
            if (col > 0) // --------------------------------check the left side
            {
                for (int i = 0; i < length; i++) {
                    if (board[row+i][col - 1] != null)
                    {
                        Word temp=FindHorizontally(row + i, col,w.tiles[i],w);
                        if(NotExists(wordslist,temp)) wordslist.add(temp);
                    }
                }
            }
        } else // horizontal
        {
            if (col > 0 && board[row][col - 1] != null) //check left side
            {
                Word temp=FindHorizontally(row, col,w.tiles[0],w);
                if(NotExists(wordslist,temp)) wordslist.add(temp);
            }
            if (col + length < SIZE - 1 && board[row][col + length + 1] != null)  //check right side
            {
                Word temp=FindHorizontally(row, col + length,w.tiles[length-1],w);
                if(NotExists(wordslist,temp)) wordslist.add(temp);
            }

            if (row < SIZE - 1) // check down of word
            {
                for (int i = 0; i < length; i++) {
                    if (board[row + 1][col+i] != null)
                    {
                        Word temp=FindVertically(row, col + i,w.tiles[i],w);
                        if(NotExists(wordslist,temp)) wordslist.add(temp);
                    }
                }
            }
            if (row > 0) // check up of word
            {
                for (int i = 0; i < length; i++) {
                    if (board[row - 1][col+i] != null)
                    {
                        Word temp=FindVertically(row, col + i,w.tiles[i],w);
                        if(NotExists(wordslist,temp)) wordslist.add(temp);
                    }
                }
            }
        }
        return wordslist;
    }

    private boolean NotExists(ArrayList<Word> wordslist, Word wordToCheck) {
        for (Word word : wordslist) {
            if (word.equals(wordToCheck))
            {
                return false;
            }
        }
        return true;
    }


    public Word FindHorizontally(int row, int col,Tile A,Word w) // Left to Right
    {
        Tile[][] temporaryBoard=new Tile[SIZE][SIZE];
        SuperCopy(temporaryBoard);
        placeTheWord(w,temporaryBoard);
        int StartWordCol=col;
        int EndWordCol = col;
        while ((EndWordCol + 1 < SIZE) && (temporaryBoard[row][EndWordCol+1] != null))
            EndWordCol++;
        while ((StartWordCol - 1 >= 0) && (temporaryBoard[row][StartWordCol-1] != null))
            StartWordCol--;
        ArrayList<Tile> t =new ArrayList<>();
        for (int i = StartWordCol; i <= (EndWordCol); i++) {
            if( temporaryBoard[row][i]==null)
                t.add(A);
            else
                t.add(temporaryBoard[row][i]);
        }
        Tile[] newTile;
        int length=t.size();
        newTile = t.toArray(new Tile[length]);
        return new Word(newTile, row, StartWordCol, false);
    }

    public Word FindVertically(int row, int col,Tile A,Word w) // Down to Up
    {
        Tile[][] temporaryBoard=new Tile[SIZE][SIZE];
        SuperCopy(temporaryBoard);
        placeTheWord(w,temporaryBoard);
        int EndWordRow=row;
        int StartWordRow = row;
        while (StartWordRow - 1 >= 0 && temporaryBoard[StartWordRow-1][col] != null)
            StartWordRow--;
        while (EndWordRow + 1 < SIZE && temporaryBoard[EndWordRow+1][col] != null)
            EndWordRow++;
        ArrayList<Tile> t =new ArrayList<>();
        for (int i = StartWordRow; i <= EndWordRow; i++) {
            if( temporaryBoard[i][col]==null)
                t.add(A);
            else
                t.add(temporaryBoard[i][col]);
        }
        Tile[] newTile;
        int length=t.size();
        newTile = t.toArray(new Tile[length]);
        return new Word(newTile, StartWordRow, col, true);
    }

    public void SuperCopy(Tile[][] BoardCopy)
    {
        if(!isEmpty) {
            for (int i = 0; i < SIZE; i++) {
                System.arraycopy(board[i], 0, BoardCopy[i], 0, SIZE);
            }
        }
    }

    //-----------------------------------------End of getWords----------------------------------//

    //-----------------------------------------------getScore----------------------------------//
    public int getScore(Word w) {
        if(w == null)
            return 0;
        int length = w.tiles.length;
        int row = w.getRow();
        int col = w.getCol();

        int red = 0;
        int yellow = 0;

        int finallscore = 0;

        if (w.vertical) {
            for (int i = 0; i < length; i++) {
                switch (Type[row + i][col]) {
                    case 'r': //red color
                        red++;
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                    case 'y': //yellow color
                        yellow++;
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                    case 'b': //blue color
                        finallscore = finallscore + w.tiles[i].score * 3;
                        break;
                    case 'p': //pale blue
                        finallscore = finallscore + w.tiles[i].score * 2;
                        break;
                    case 's':
                        Type[7][7] = 'g'; // calculated the star once
                        yellow++;
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                    case 'g': //default
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                }
            }
        } else //horizontal
        {
            for (int i = 0; i < length; i++) {
                switch (Type[row][col + i]) {
                    case 'r': //red color
                        red++;
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                    case 'y': //yellow color
                        yellow++;
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                    case 'b': //blue color
                        finallscore = finallscore + w.tiles[i].score * 3;
                        break;
                    case 'p': //pale blue
                        finallscore = finallscore + w.tiles[i].score * 2;
                        break;
                    case 's':
                        Type[7][7] = 'g'; // calculated the star once
                        yellow++;
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                    case 'g': //default
                        finallscore = finallscore + w.tiles[i].score;
                        break;
                }
            }
        }

        if (red != 0) {
            finallscore = finallscore * red * 3;
        } else if (yellow != 0)
            finallscore = finallscore * yellow * 2;

        return finallscore;
    }


//----------------------------------end of GetScore-----------------------------------------//

// ----------------------------------tryPlaceWord-----------------------------------------//


    public int tryPlaceWord(Word w)
    {
        int score = 0;
        w = checkifCompleteword(w); // fill the null places

        if (!boardLegal(w))
            return 0;
        ArrayList<Word> words = getWords(w);
        ArrayList<Word> arr =new ArrayList<>();

        for (Word i : words)
            if (!dictionaryLegal(i))
                return 0;
        for (Word i:words)
        {
            if (NotExists(allWords, i)) {
                allWords.add(i);
                arr.add(i);
            }
        }
        placeTheWord(w,board);
        if(arr.isEmpty())
            return 0;
        for (Word t : arr)
            score = score + getScore(t);
        return score;
    }

    public Word checkifCompleteword(Word w) {
        int length = w.tiles.length;
        int row = w.getRow();
        int col = w.getCol();
        ArrayList<Tile> tiles = new ArrayList<>();
        if (!w.vertical) {
            for (int i = 0; i < length; i++) {
                if (w.tiles[i] == null)
                    tiles.add(board[row][col + i]);
                else
                    tiles.add(w.tiles[i]);
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (w.tiles[i] == null)
                    tiles.add(board[row + i][col]);
                else
                    tiles.add(w.tiles[i]);
            }
        }
        Tile[] t;
        int length_tiles=tiles.size();
        t = tiles.toArray(new Tile[length_tiles]);
        if (w.vertical) return new Word(t, row, col, true);
        else return new Word(t, row, col, false);
    }

    public void placeTheWord(Word w, Tile[][] board) {
        int length = w.tiles.length;
        int row = w.getRow();
        int col = w.getCol();
        if (!w.vertical)
        {
            for (int i = 0; i < length; i++) {
                if (w.tiles[i] != null)
                    board[row][col + i] = w.tiles[i];
            }
        } else {
            for (int i = 0; i < length; i++) {
                if (w.tiles[i] != null)
                    board[row + i][col] = w.tiles[i];
            }
        }
        isEmpty=false; // the board is not empty
    }

}
