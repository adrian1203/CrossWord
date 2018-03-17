package Crossword.dictionary;

import Crossword.exception.FileWithEntryException;

import java.io.*;
import java.util.LinkedList;

public class CwDB {
    public LinkedList<Entry> dict;

    public CwDB(String filename) throws IOException, FileWithEntryException {
        this.dict = new LinkedList<Entry>();
        createDB(filename);

    }

    public void add(String word, String clue) {
        Entry tmp = new Entry(word, clue);
        this.dict.addFirst(new Entry(word, clue));

    }

    public Entry get(String word) {
        return new Entry("g", "o");
    }

    public void remove(String word) {
    }

    public void saveDB(String filename) {
    }

    public int getSize() {
        return 1;
    }

    protected void createDB(String filename) throws IOException, FileWithEntryException {
        BufferedReader br = new BufferedReader(new FileReader(filename));
        String tmp = br.readLine();
        do {
            String word = tmp;
            String clue = br.readLine();
            String[] parts = word.split(" ");
            if (parts.length > 1) {
                throw new FileWithEntryException();
            }
            add(word, clue);
        } while ((tmp = br.readLine()) != null);
        br.close();
    }

}
