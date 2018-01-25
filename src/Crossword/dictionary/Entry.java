package Crossword.dictionary;

public class Entry implements Comparable<Entry>{
    private String word;
    private String clue;
    public Entry(String word, String clue){
        this.word=word;
        this.clue=clue;
    }
    public String getWord(){

        return word;
    }

    public String getClue() {
        return clue;
    }
    public int compareTo(Entry o){
        return this.word.compareTo(o.word);
    }
}
