package eu.michalszyba.adrwaybill.util;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import eu.michalszyba.adrwaybill.model.Company;
import lombok.Setter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Setter
public class PDFGenerator {

    private List<Company> companies;

    public void  generate(HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        fontTitle.setSize(20);

        Paragraph paragraph = new Paragraph("LIST OF COMPANIES", fontTitle);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(paragraph);

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new int[]{3, 3, 3});
        table.setSpacingBefore(5);

        PdfPCell cell = new PdfPCell();

        // Setting the background color and padding
        cell.setBackgroundColor(CMYKColor.MAGENTA);
        cell.setPadding(5);

        // Creating font
        // Setting font style and size
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setColor(CMYKColor.WHITE);

        // Adding headings in the created table cell/ header
        // Adding Cell to table
        cell.setPhrase(new Phrase("ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Company Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("City", font));
        table.addCell(cell);

        for (Company company : companies) {
            table.addCell(String.valueOf(company.getId()));
            table.addCell(company.getCompanyName());
            table.addCell(company.getCity());
        }

        document.add(table);

        document.close();
    }
}
