package Crossword.dictionary;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.regex.*;

public class InteliCwDB extends CwDB {
    public InteliCwDB(String filename) throws IOException {
        super(filename);
        //System.out.print("Konstruktor pochodny");
    }

    /**
     * Znajdowanie wszytskich haseł pasujących to wzorca
     * @param pattern
     * @return
     */
    public LinkedList<Entry> findAll(String pattern) {
        Pattern pat = Pattern.compile(pattern);
        LinkedList<Entry> list = new LinkedList<Entry>();
        for (Entry x : this.dict) {
            Matcher matcher = pat.matcher(x.getWord());
            if (matcher.find() == true) {
                list.add(x);
            }
        }
        return list;
    }

    /**
     * Losowe wyszukiwanie hasła, brak jakich kolwiek narzuconych wymiarów
     * @return
     */
    public Entry getRandom() {
        Random generator = new Random();
        int i=generator.nextInt(dict.size());
        return dict.get(i);
    }

    ;

    /**
     * Losowe szukania hasła, dla zadanej długości
     * @param lenght dlugość hasła
     * @return wzraca hasło
     */
    public Entry getRandom(int lenght) {
        LinkedList<Entry> list = new LinkedList<Entry>();
        for (Entry x : this.dict) {
            if (x.getWord().length()==lenght) {
                list.add(x);
            }
        }
        if(list.size()==0){return new Entry("", "");}
        int i=(new Random().nextInt(list.size()));
        return list.get(i);

    }

    /**
     * Losowe szukanie haslła, które jest jest nie dłuższe niż zadana długość
     * @param lenght max długość hasła
     * @return zwraca hasło
     */
    public Entry getRandomLessThen(int lenght) {
        LinkedList<Entry> list = new LinkedList<Entry>();
        for (Entry x : this.dict) {
            if (x.getWord().length()<=lenght) {
                list.add(x);
            }
        }
        if(list.size()==0){return new Entry("", "");}
        int i=(new Random().nextInt(list.size()));
        return list.get(i);

    }

    ;

    /**
     * Losowe wyszukiwanie hasła pasującego to zadanego wzorca
     * @param pattern zadany wzorzec hasła
     * @return zwraca hasło
     */
    public Entry getRandom(String pattern) {
        LinkedList<Entry> list=new LinkedList<Entry>();
        list=findAll(pattern);
        if(list.size()==0){return new Entry("", "");}
        int i=(new Random().nextInt(list.size()));
        return list.get(i);

    }



    public void add(String word, String clue) {
        Entry tmp=new Entry(word, clue);
        this.dict.add(new Entry(word,clue));
       /* int i=0;
        if(dict.size()==0){
            this.dict.add(tmp);
        }
        for(Entry x:dict){
            System.out.println(x.compareTo(tmp));
            if(x.compareTo(tmp)<=0){
                this.dict.add(i,tmp);
                i++;
            }

            //System.out.println(i);
        }*/
        Collections.sort(dict);
    }


}
