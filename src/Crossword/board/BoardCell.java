package Crossword.board;

public class BoardCell {
    BoardCell(){
        this.content=null;
    }
    private String content;

    public void setConntent(String content){
        this.content=content;
    }
    public String getContent(){
        return content;
    }

}
