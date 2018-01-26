package Crossword.sample;

import Crossword.board.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Controller {


    public TextField text1;
    public Button start;
    public Label[][] Labels;
    public Pane panel;
    public Label labe;
    public ListView listbooks;
    public Button checkButton;
    public Board board;
    public int a=0;
    public int b=0;
    public TextField SetXY;

    /**
     * Tworzenie planszy krzyżówki w GUI
     * na podstawie strategi w CreateBoard
     * @param actionEvent
     * @throws IOException
     */
    public void create(ActionEvent actionEvent) throws IOException {
        if(!SetXY.getText().isEmpty()){
            a=Integer.parseInt(SetXY.getText());
            b=Integer.parseInt(SetXY.getText());
        }else{
            a=12;
            b=12;
        }
        board = new Board(a, b);
        board.createBoard();
        Labels = new Label[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                Label label = new Label();
                label.setLayoutX(40 + j * 25);
                label.setLayoutY(80 + i * 25);
                label.setPrefWidth(25);
                label.setPrefHeight(25);
                if (board.board[i][j] == null) {
                    label.setStyle("-fx-background-color: darkgray");
                } else {
                    if (board.board[i][j].matches("\\d.*")) {
                        label.setStyle("-fx-border-color: darkgray;-fx-background-color: yellow;-fx-text-alignment: center");
                        label.setText(" " + board.board[i][j]);
                    } else {
                        label.setStyle("-fx-border-color: darkgray;-fx-background-color: royalblue;-fx-text-alignment: center");
                        label.setText("  ");
                    }
                }
                Labels[i][j] = label;
                panel.getChildren().add(label);
                listbooks.getItems().clear();
                List<String> list = new LinkedList<>();
                int number = 1;
                for (String x : board.clue) {
                    list.add(number + ". " + x);
                    number++;
                }
                ObservableList<String> itms = FXCollections.observableArrayList(list);
                listbooks.setVisible(true);
                listbooks.setItems(itms);
                checkButton.setVisible(true);


            }
        }
    }

    /**
     * Wyświetlanie haseł krzyżówki w GUI
     * @param actionEvent
     */
    public void check(ActionEvent actionEvent) {
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                if (board.board[i][j] != null) {
                    if (!board.board[i][j].matches("\\d.*")) {
                        Labels[i][j].setText(" " + board.board[i][j]);
                    }

                }
            }
        }
    }
}
