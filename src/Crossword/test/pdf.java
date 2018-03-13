package Crossword.test;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class pdf {
    public static void main(String[] args) throws IOException, DocumentException {
        Document document=new Document();
        File file=new File("C:\\Users\\Adrian\\Desktop\\2.pdf");
        com.itextpdf.text.pdf.PdfWriter.getInstance(document,new FileOutputStream("C:\\Users\\Adrian\\Desktop\\2.pdf"));
        document.open();
        document.add(new Paragraph("chu"));
        document.close();
        Desktop.getDesktop().open(file);


    }
}
