package Crossword.pdfDocument;

import Crossword.board.Board;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.awt.*;
import java.io.*;

public class PDFConverter {
    public Document document;

    public PDFConverter() {
    }


    public void creatCrossword(File file, Board board) throws IOException, DocumentException {
        document = new Document();
        FileOutputStream byteArrayOutputStream = new FileOutputStream(file.getAbsolutePath());
        com.itextpdf.text.pdf.PdfWriter.getInstance(document, byteArrayOutputStream);
        document.open();
        Paragraph title = new Paragraph("KRZYŻÓWKA \n\n");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(""));
        document.addTitle("Krzyżówka");
        createTable(board);
        createListsofClue(board);
        document.close();
        Desktop.getDesktop().open(file);


    }

    public void createTable(Board board) throws DocumentException {
        for (int i = 0; i < board.getWidth(); i++) {
            PdfPTable pdfPTable2 = new PdfPTable(board.getHeight());
            for (int j = 0; j < board.getHeight(); j++) {
                PdfPCell pdfPCell2 = new PdfPCell();
                pdfPCell2.setBackgroundColor(BaseColor.LIGHT_GRAY);
                pdfPCell2.setBorderColor(BaseColor.LIGHT_GRAY);
                pdfPCell2.setMinimumHeight(23);
                if (board.getCell(i, j) != null) {
                    if (board.getCell(i, j).split("[0-9]").length > 1) {
                        pdfPCell2.addElement(new Paragraph(board.getCell(i, j)));
                        pdfPCell2.setBackgroundColor(BaseColor.YELLOW);
                    } else {
                        pdfPCell2.setBackgroundColor(BaseColor.WHITE);
                    }
                }
                pdfPTable2.addCell(pdfPCell2);
            }
            document.add(pdfPTable2);
        }

    }

    public void createListsofClue(Board board) throws DocumentException {
        document.add(new Paragraph("\n PYTANIA:\n"));
        int number = 1;
        for (String x : board.clue) {
            document.add(new Paragraph(number + ". " + x));
            number += 1;
        }
    }

}
