package controller;

import java.io.IOException;
import java.text.DecimalFormat;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/TicketPDFServlet")
public class TicketPDFServlet extends HttpServlet {

    private static final DecimalFormat MONEY = new DecimalFormat("0.00");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {

        HttpSession session = req.getSession(false);
        if (session == null) {
            res.setContentType("text/plain");
            res.getWriter().write("No ticket data found.");
            return;
        }

        String[] seatNos = (String[]) session.getAttribute("seatNos");
        String[] names = (String[]) session.getAttribute("names");
        String[] ages = (String[]) session.getAttribute("ages");
        String[] genders = (String[]) session.getAttribute("genders");
        String boardingName = (String) session.getAttribute("boardingName");
        String droppingName = (String) session.getAttribute("droppingName");
        Double seatPrice = (Double) session.getAttribute("seatPrice");
        String bookingRef = (String) session.getAttribute("bookingRef");

        if (seatNos == null || names == null || ages == null || genders == null
                || names.length != seatNos.length
                || ages.length != seatNos.length
                || genders.length != seatNos.length
                || seatNos.length == 0) {
            res.setContentType("text/plain");
            res.getWriter().write("No valid ticket data found.");
            return;
        }

        res.setContentType("application/pdf");
        res.setHeader("Content-Disposition", "attachment; filename=Bus_Ticket_" +
                (bookingRef != null ? bookingRef : "Details") + ".pdf");

        try {
            Document doc = new Document(PageSize.A4, 36, 36, 30, 30);
            PdfWriter.getInstance(doc, res.getOutputStream());
            doc.open();

            // Fonts
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.WHITE);
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.WHITE);
            Font sectionLabelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, new BaseColor(22, 122, 74));
            Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            Font tableHeaderFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, BaseColor.WHITE);
            Font tableValueFont = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
            Font totalFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.BLACK);
            Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 9, new BaseColor(90, 120, 90));

            // Header block
            PdfPTable header = new PdfPTable(1);
            header.setWidthPercentage(100);

            PdfPCell headerCell = new PdfPCell();
            headerCell.setBackgroundColor(new BaseColor(22, 122, 74));
            headerCell.setBorder(Rectangle.NO_BORDER);
            headerCell.setPadding(12f);

            Paragraph title = new Paragraph("BUS E-TICKET", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            headerCell.addElement(title);

            Paragraph subtitle = new Paragraph("Safe Journey | Digital Ticket Copy", subTitleFont);
            subtitle.setAlignment(Element.ALIGN_CENTER);
            headerCell.addElement(subtitle);

            header.addCell(headerCell);
            doc.add(header);
            doc.add(Chunk.NEWLINE);

            // Booking and route info
            PdfPTable info = new PdfPTable(2);
            info.setWidthPercentage(100);
            info.setSpacingAfter(10f);
            info.setWidths(new float[] { 1f, 1f });

            info.addCell(infoLabelCell("Booking Ref", sectionLabelFont));
            info.addCell(infoValueCell(safe(bookingRef, "N/A"), valueFont));

            info.addCell(infoLabelCell("From (Boarding)", sectionLabelFont));
            info.addCell(infoValueCell(safe(boardingName, "-"), valueFont));

            info.addCell(infoLabelCell("To (Dropping)", sectionLabelFont));
            info.addCell(infoValueCell(safe(droppingName, "-"), valueFont));

            info.addCell(infoLabelCell("No. of Passengers", sectionLabelFont));
            info.addCell(infoValueCell(String.valueOf(seatNos.length), valueFont));

            doc.add(info);

            // Passenger table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setWidths(new float[] { 1f, 3f, 1f, 1.5f });
            table.setSpacingBefore(6f);

            table.addCell(tableHeaderCell("Seat No", tableHeaderFont));
            table.addCell(tableHeaderCell("Passenger Name", tableHeaderFont));
            table.addCell(tableHeaderCell("Age", tableHeaderFont));
            table.addCell(tableHeaderCell("Gender", tableHeaderFont));

            for (int i = 0; i < seatNos.length; i++) {
                table.addCell(tableValueCell(safe(seatNos[i], "-"), tableValueFont, i));
                table.addCell(tableValueCell(safe(names[i], "-"), tableValueFont, i));
                table.addCell(tableValueCell(safe(ages[i], "-"), tableValueFont, i));
                table.addCell(tableValueCell(safe(genders[i], "-"), tableValueFont, i));
            }

            doc.add(table);
            doc.add(Chunk.NEWLINE);

            // Fare summary
            double pricePerSeat = seatPrice != null ? seatPrice : 0.0;
            double total = pricePerSeat * seatNos.length;

            PdfPTable fare = new PdfPTable(2);
            fare.setWidthPercentage(45);
            fare.setHorizontalAlignment(Element.ALIGN_RIGHT);
            fare.setWidths(new float[] { 2f, 1f });

            fare.addCell(summaryLabelCell("Price / Seat", sectionLabelFont));
            fare.addCell(summaryValueCell("Rs " + MONEY.format(pricePerSeat), valueFont));

            fare.addCell(summaryLabelCell("Passengers", sectionLabelFont));
            fare.addCell(summaryValueCell(String.valueOf(seatNos.length), valueFont));

            fare.addCell(summaryLabelCell("Total Fare", totalFont));
            fare.addCell(summaryValueCell("Rs " + MONEY.format(total), totalFont));

            doc.add(fare);
            doc.add(Chunk.NEWLINE);

            Paragraph footer = new Paragraph(
                    "Please carry a valid ID proof during travel. This is a system-generated ticket.",
                    footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            doc.add(footer);

            doc.close();
        } catch (Exception e) {
            throw new IOException("Failed to generate ticket PDF", e);
        }
    }

    private static String safe(String value, String fallback) {
        return (value == null || value.trim().isEmpty()) ? fallback : value.trim();
    }

    private static PdfPCell infoLabelCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorderColor(new BaseColor(198, 224, 207));
        cell.setBackgroundColor(new BaseColor(239, 250, 243));
        cell.setPadding(8f);
        return cell;
    }

    private static PdfPCell infoValueCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorderColor(new BaseColor(198, 224, 207));
        cell.setPadding(8f);
        return cell;
    }

    private static PdfPCell tableHeaderCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBackgroundColor(new BaseColor(34, 139, 34));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setPadding(8f);
        return cell;
    }

    private static PdfPCell tableValueCell(String text, Font font, int row) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(7f);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        if (row % 2 == 0) {
            cell.setBackgroundColor(new BaseColor(246, 255, 248));
        }
        return cell;
    }

    private static PdfPCell summaryLabelCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorderColor(new BaseColor(198, 224, 207));
        cell.setPadding(7f);
        return cell;
    }

    private static PdfPCell summaryValueCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorderColor(new BaseColor(198, 224, 207));
        cell.setPadding(7f);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        return cell;
    }
}