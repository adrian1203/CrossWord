package Crossword.sample;

import Crossword.board.Board;
import Crossword.excetion.FileWithEntryException;
import com.itextpdf.kernel.color.Lab;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.print.*;
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

import javax.swing.text.Document;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.pdfbox.*;
import org.apache.pdfbox.pdmodel.PDDocument;

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
    public boolean wascreated = false;
    public TextField pathFileField;
    public Pane panelBoard;
    public Button pdfButton;
    public Alert alert;
    public Pane paneWithClue;


    public void initialize() {
        SpinnerValueFactory<Integer> valueFactorywith = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 20, 14);
        SpinnerValueFactory<Integer> valueFactoryheight = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 20, 14);
        widthBordSpinner.setValueFactory(valueFactorywith);
        heightBordSpinner.setValueFactory(valueFactoryheight);

    }

    public void showAlert(String alertMessage, String tipsMessage) {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ostrzerzenie");
        alert.setHeaderText(alertMessage);
        alert.setContentText(tipsMessage);
        alert.showAndWait();
    }

    public void clearBoard() {
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
                label.setLayoutX(250-(b*12)+5 + (j * 25));
                label.setLayoutY(250-(a*12)+5 + (i * 25));
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

    public void create(ActionEvent actionEvent)  {
        if (pathFileField.getText().equals("")) {
            showAlert("Brak wybranego pliku", "Wybierz plik zawierający hasła do krzyżówki");
            return;
        }
        fileWithKey=new File(pathFileField.getText());
        if(!fileWithKey.isFile()){
            showAlert("Nie można otworzyć wybranego pliku", "Wybierz poprawny plik zawierający hasła do krzyżówki");
            return;
        }
        clearBoard();
        a = widthBordSpinner.getValue();
        b = heightBordSpinner.getValue();
        board = new Board(a, b);
        try {
            board.createBoard(fileWithKey);
        }
       catch (IOException e0){
            showAlert("Nie można otworzyć wybranego pliku", "Wybierz poprawny plik zawierający hasła do krzyżówki");
            return;
       }
       catch (FileWithEntryException e){
           showAlert("Błędne dane w pliku", "Wybierz poprawny plik zawierający hasła do krzyżówki");
           return;
       }

        createBoardLabels();


    }


    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik z hasłem");
        fileWithKey = fileChooser.showOpenDialog(new Stage());
        if(fileWithKey==null){
            return;
        }
        pathFileField.setText(fileWithKey.getAbsolutePath());


    }

    public void open(ActionEvent actionEvent) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik z krzyżówką");
        fileWithBoard = fileChooser.showOpenDialog(new Stage());
        if (fileWithBoard == null) {
            return;
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileWithBoard.getAbsolutePath()));
            board = (Board) in.readObject();
        } catch (Exception e) {
            showAlert("Błąd odczytu pliku", "Wybierz plik zawierający krzyżówkę");
            return;
        }

        clearBoard();
        a = board.getWidth();
        b = board.getHeight();
        widthBordSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, a));
        heightBordSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 25, b));
        createBoardLabels();

    }

    public void save(ActionEvent actionEvent)  {
        if (wascreated == false) {
            showAlert("Brak krzyżówki do zapisu", "Stwórz lub otwórz krzyżówkę");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Board", "*.board");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(new Stage());
        if (file == null) {
            return;
        }
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
            out.writeObject(board);
        }
        catch (Exception e){
            showAlert("Bład zapisu pliku", "Spróbuj jeszcze raz");
        }

    }

    public void print(ActionEvent actionEvent) {


    }

    public void solve(ActionEvent actionEvent) {
        if (wascreated == false) {
            showAlert("Brak krzyżówki", "Stwórz lub otwórz krzyżówkę");
        }
        for (int i = 0; i < a; i++) {
            for (int j = 0; j < b; j++) {
                if (board.board[i][j] != null) {
                    if (!board.board[i][j].matches("\\d.*")) {
                        labels[i][j].setText("  " + board.board[i][j]);
                    }

                }
            }
        }
    }

    public void pdfCreate(ActionEvent actionEvent) throws DocumentException, IOException {
        PrinterJob job=PrinterJob.createPrinterJob();
        job.printPage(panelBoard);
        job.showPageSetupDialog(new Stage());
        job.showPrintDialog(new Stage());
        com.itextpdf.text.Document document=new com.itextpdf.text.Document();
        FileOutputStream byteArrayOutputStream=new FileOutputStream("C:\\Users\\Adrian\\Desktop\\1.pdf");
        com.itextpdf.text.pdf.PdfWriter.getInstance(document,byteArrayOutputStream);
        document.add((Element)panelBoard);
        document.close();
        File file=new File("C:\\Users\\Adrian\\Desktop\\1.pdf");
        Desktop.getDesktop().open(file);
        PDDocument document1=PDDocument.load(file);



       /* com.itextpdf.text.Document document=new com.itextpdf.text.Document();
        File file=new File("C:\\Users\\Adrian\\Desktop\\2.PDFConverter");
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        PdfWriter.getInstance(document,file);
        com.itextpdf.text.PDFConverter.PdfWriter.getInstance(document,byteArrayOutputStream);
        document.open();
        document.add(new Paragraph("chu"));
        document.add()
        document.close();
        Desktop.getDesktop().open(file);*/
       /*ObservableSet<Printer> printer=Printer.getAllPrinters();
        PrinterJob job = PrinterJob.createPrinterJob(Printer.getDefaultPrinter());
        PageLayout pageLayout=new PageLayout(Paper.A4,PageOrientation.LANDSCAPE);
        job.printPage(pageLayout,panelBoard);
        Stage stage=new Stage();
        stage.setTitle("siemka");
        job.showPrintDialog(stage);
        System.out.println("chuju ");*/
      /*  Printer printer = Printer.getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE,
                Printer.MarginType.HARDWARE_MINIMUM);
        PrinterJob job = PrinterJob.createPrinterJob(Printer.getDefaultPrinter());
        boolean b1=job.printPage(pageLayout,panelBoard);
        boolean b2=job.printPage();
        System.out.println(b1+"chuj"+b2);*/



    }
}
