package Crossword.sample;

import Crossword.board.Board;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Controller {


    public TextField text1;
    public Button start;
    public Label[][] labels;
    public Pane panel;
    public Label labe;
    public ListView listbooks;
    public Button checkButton;
    public Board board;
    public int a = 0;
    public int b = 0;
    public TextField SetXY;
    public Button chooseFileButton;
    public Button openButton;
    public Button saveButton;
    public Button printButton;
    public Button solveButton;
    public Spinner<Integer> heightBordSpinner;
    public Spinner<Integer> widthBordSpinner;
    public File fileWithKey;
    public File fileWithBoard;
    public boolean wascreated=false;
    public TextField pathFileField;
    public Pane panelBoard;
    public Button pdfButton;


    public void initialize() {
        SpinnerValueFactory<Integer> valueFactorywith = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 15);
        SpinnerValueFactory<Integer> valueFactoryheight = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, 15);
        widthBordSpinner.setValueFactory(valueFactorywith);
        heightBordSpinner.setValueFactory(valueFactoryheight);
    }
    public void clearBoard(){
        if (wascreated) {
            for (int i = 0; i < a; i++) {
                for (int j = 0; j < b; j++) {
                    labels[i][j].setVisible(false);
                }
            }
        }
    }
    public void createBoardLabels() {
        labels = new Label[a][b];
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                Label label = new Label();
                label.setLayoutX(5 + j * 25);
                label.setLayoutY(5 + i * 25);
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
                labels[i][j] = label;
                panelBoard.getChildren().add(label);
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
                wascreated = true;
            }
        }
    }

    /**
     * Tworzenie planszy krzyżówki w GUI
     * na podstawie strategi w CreateBoard
     *
     * @param actionEvent
     * @throws IOException
     */

    public void create(ActionEvent actionEvent) throws IOException {
        clearBoard();
        a = widthBordSpinner.getValue();
        b = heightBordSpinner.getValue();
        board = new Board(a, b);
        board.createBoard(fileWithKey);
        createBoardLabels();


    }


    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik z hasłem");
        fileWithKey = fileChooser.showOpenDialog(new Stage());
        pathFileField.setText(fileWithKey.getAbsolutePath());


    }

    public void open(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik z krzyżówką");
        fileWithBoard = fileChooser.showOpenDialog(new Stage());
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileWithBoard.getAbsolutePath()));
        board = (Board) in.readObject();
        clearBoard();
        a=board.getWidth();
        b=board.getHeight();
        widthBordSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, a));
        heightBordSpinner.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, b));

        createBoardLabels();

    }

    public void save(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Board", "*.board");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
        out.writeObject(board);
    }

    public void print(ActionEvent actionEvent) {


    }
    public void solve(ActionEvent actionEvent) {
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                if (board.board[i][j] != null) {
                    if (!board.board[i][j].matches("\\d.*")) {
                        labels[i][j].setText(" " + board.board[i][j]);
                    }

                }
            }
        }
    }

    public void pdfCreate(ActionEvent actionEvent) {
    }
}
