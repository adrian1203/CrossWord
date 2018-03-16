package Crossword.test;

import Crossword.board.Board;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


import java.awt.*;
import java.io.*;
import java.nio.file.Files;

public class PDFConverter {
    public PDFConverter(){};
    public void creat() throws IOException, DocumentException {
        Document document=new Document();
        FileOutputStream byteArrayOutputStream=new FileOutputStream("C:\\Users\\Adrian\\Desktop\\1.pdf");
        com.itextpdf.text.pdf.PdfWriter.getInstance(document,byteArrayOutputStream);

        document.open();
        document.add(new Paragraph("Krzyżówka"));
        document.add(new Paragraph("Krzyżówka"));
        document.addTitle("Krzyżówka");
        document.close();
        File file=new File("C:\\Users\\Adrian\\Desktop\\1.pdf");
        Desktop.getDesktop().open(file);


    }
    public static void main(String[] args) throws IOException, DocumentException {
        PDFConverter pdfConverter=new PDFConverter();
        pdfConverter.creat();





}}
