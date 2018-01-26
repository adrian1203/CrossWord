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

    /**
     * Konstruktor
     * @param x wymiary planszy
     * @param y wymiary planszy
     */
    public Board(int x, int y) {
        board = new String[x][y];
        this.x = x;
        this.y = y;
    }

    /**
     * metoda do dalszeo rozwoju
     * @return
     */
    public int getWidth() {
        return this.x;
    }
    /**
     * metoda do dalszeo rozwoju
     * @return
     */
    public int getHeight() {
        return this.y;
    }
    /**
     * metoda do dalszeo rozwoju
     * @return
     */
    public String getCell(int x, int y) {
        return board[x][y];
    }
    /**
     * metoda ustawia komórkę
     * @return
     */
    public void setCell(int x, int y, String c) {
        board[x][y] = c;
    }

    /**
     * Metoda budująca planszę krzyżówki, zepewnia całą logikę krzyżówki
     * Pierwsza iteracja po kolumnach krzyżówki, następnie po wierszach
     * podczas dugirj iteracji dopasowywane są hasła, do wolnych miejsc planszy
     * Wykorzystuje metody z klasy InteliCwDb, do znajdowanie haseł
     * @throws IOException
     */
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

    /**
     * Wstawianie hasła poziomwego do krzyżówki
     * @param tab tablicach charów
     * @param x  od
     * @param y  do na planszy
     * @param number numer hasła
     */
    public void insertWordHorizon(char[] tab, int x, int y, int number) {
        int i = 1;
        setCell(x, y, String.valueOf(number + "."));
        for (int j = 0; j < tab.length; j++) {
            setCell(x, y + i, new StringBuilder().append(tab[j]).toString());
            i++;
        }

    }

    /**
     * Wstawianie hasła poziomwego do krzyżówki
     * @param tab tablicach charów
     * @param x  od
     * @param y  do na planszy
     * @param number numer hasła
     */
    public void insertWordVer(char[] tab, int x, int y, int number) {
        int i = 1;
        setCell(x, y, String.valueOf(number + "."));
        for (int j = 0; j < tab.length; j++) {
            setCell(x + i, y, new StringBuilder().append(tab[j]).toString());
            i++;
        }

    }



    /**
     * Tworzenie wyrażenia regularnego pasującego do planszy krzyżowki, gdzie wstawiamy hasło
     * @param fromy od
     * @param toy do
     * @param x numer wiersza
     * @return wzorzec
     */
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
