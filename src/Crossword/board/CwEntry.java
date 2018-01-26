package Crossword.board;

import Crossword.dictionary.Entry;

import java.util.Enumeration;

/**
 * Klasa i jej metody do dlaszego rozwoju ;)
 */
public class CwEntry extends Entry {
    CwEntry(String word, String clue, int x, int y, Dircetion direction) {
        super(word, clue);
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    private int x;
    private int y;

    private enum Dircetion {
        HORIZ,
        VERT;
    }

    private Dircetion direction;

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getDirection() {
        if (this.direction.equals(Dircetion.HORIZ)) return 1;
        else return 0;
    }
}
