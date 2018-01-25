package Crossword.board;

import Crossword.dictionary.Entry;
import Crossword.dictionary.InteliCwDB;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

public class Board {
    public String[][] board;
    private int x;
    private int y;
    public LinkedList<String> clue = new LinkedList<String>();

    public Board(int x, int y) {
        board = new String[x][y];
        this.x = x;
        this.y = y;
    }

    public int getWidth() {
        return this.x;
    }

    public int getHeight() {
        return this.y;
    }

    public String getCell(int x, int y) {
        return board[x][y];
    }

    public void setCell(int x, int y, String c) {
        board[x][y] = c;
    }

    //public LinkedList<BoardCell> getStartCells(){}
    public void createBoard() throws IOException {
        InteliCwDB inteliCwDB = new InteliCwDB("C:/Users/Adrian/Desktop/cwdb.txt");
        int cluenumber = 0;
        int random=0;
        for (int i = 1; i < this.y; i=i+2) {
            Entry entry = inteliCwDB.getRandomLessThen(this.x - 4);
            clue.add(entry.getClue());
            cluenumber++;
            random = (new Random().nextInt(4));
            insertWordVer(entry.getWord().toCharArray(), random, i, cluenumber);
            if (entry.getWord().length() < this.x - random ) {
                Entry entry1 = inteliCwDB.getRandomLessThen(this.x - entry.getWord().length() - 2 - random);
                if (entry1.getClue() != "") {
                    clue.add(entry1.getClue());
                    cluenumber++;
                    insertWordVer(entry1.getWord().toCharArray(), entry.getWord().length() + random + 1, i, cluenumber);
                }
            }
        }
        for (int i = 0; i < this.x; i ++) {
            for (int j = this.y; j > 2; j--) {
                for (int l = 0; l < this.y - j; l++) {
                    String tmp = createPattern(l, j - l, i);
                    Entry tmp2 = inteliCwDB.getRandom(tmp);
                    if (!tmp2.getWord().isEmpty() && board[i][l]==null) {
                        clue.add(tmp2.getClue());
                        cluenumber++;
                        insertWordHorizon(tmp2.getWord().toCharArray(), i, l, cluenumber);
                        l += tmp2.getWord().length()+1;
                    }
                }
            }
        }
    }

    public void insertWordHorizon(char[] tab, int x, int y, int number) {
        int i = 1;
        setCell(x, y, String.valueOf(number + "."));
        for (int j = 0; j < tab.length; j++) {
            setCell(x, y + i, new StringBuilder().append(tab[j]).toString());
            i++;
        }

    }

    public void insertWordVer(char[] tab, int x, int y, int number) {
        int i = 1;
        setCell(x, y, String.valueOf(number + "."));
        for (int j = 0; j < tab.length; j++) {
            setCell(x + i, y, new StringBuilder().append(tab[j]).toString());
            i++;
        }

    }

    public void printAll() {
        for (int i = 0; i < this.y; i++) {
            for (int j = 0; j < this.x; j++) {
                if (board[i][j] == null) {
                    System.out.print(" ");
                } else {
                    if (board[i][j].length() == 2) {
                        System.out.print(board[i][j] + "");
                    } else {
                        System.out.print(board[i][j] + " ");
                    }

                }

            }
            System.out.println("");
        }
        int numer = 1;
        for (String x : clue) {
            System.out.println(numer + ": " + x);
            numer++;
        }
    }

    String createPattern(int fromy, int toy, int x) {
        String tmp = "^";
        for (int i = fromy+1; i < toy - fromy; i++) {
            if (board[x][i ] == null) {
                tmp += ".";
            } else tmp += board[x][i ];
        }
        tmp += "$";


        return tmp;
    }
}
