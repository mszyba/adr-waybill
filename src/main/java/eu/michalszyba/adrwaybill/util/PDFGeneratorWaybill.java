package eu.michalszyba.adrwaybill.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import eu.michalszyba.adrwaybill.model.Company;
import eu.michalszyba.adrwaybill.model.ShippedItem;
import eu.michalszyba.adrwaybill.model.Waybill;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class PDFGeneratorWaybill {

    private Waybill waybill;

    public void  generate(HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();


        Paragraph p1 = new Paragraph(new Chunk(
                "Company: ",
                FontFactory.getFont(FontFactory.HELVETICA, 16)));

        p1.add(Chunk.NEWLINE);
        p1.add(new Chunk(waybill.getCompanyName()));
        p1.add(Chunk.NEWLINE);
        p1.add(waybill.getCompanyAddress());
        p1.add(Chunk.NEWLINE);
        p1.add(new Phrase(waybill.getCompanyCity()));
        p1.add(Chunk.NEWLINE);

        document.add(p1);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3, 3});
        table.setSpacingBefore(5);

        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding
        cell.setBackgroundColor(CMYKColor.LIGHT_GRAY);
        cell.setPadding(5);

        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
        // Adding Cell to table
        cell.setPhrase(new Phrase("UN", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("UN Desc", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Class", font));
        table.addCell(cell);

        for (ShippedItem shippedItem : waybill.getShippedItems()) {
            table.addCell(shippedItem.getUnNumber());
            table.addCell(shippedItem.getUnNameAndDescription());
            table.addCell(shippedItem.getUnClass());
        }

        document.add(table);

        document.close();
    }

    public void setWaybill(Waybill waybill) {
        this.waybill = waybill;
    }
}
