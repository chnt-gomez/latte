package com.go.kchin.model;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.Log;

import com.go.kchin.R;
import com.go.kchin.adapters.QuickSaleAdapter;
import com.go.kchin.interfaces.MainMVP;
import com.go.kchin.model.database.Sale;
import com.go.kchin.model.database.SaleTicket;
import com.go.kchin.util.dialog.number.Number;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by MAV1GA on 17/02/2017.
 */

public class PDFBuilder {

    public static File buildPurchaseOrder(Context context, String fileName,
                                          List<PurchaseOrder> orders, Resources res) throws DocumentException, IOException{
        Document document = new Document();
        File file = new File (getReportsStorageDir("kchin_reports").getPath(),fileName);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        //Title
        document.add(title(res.getString(R.string.purchase_orders)));

        //Date
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd, MMMM, yyyy");
        DateTime date = DateTime.now();
        document.add(subTitle(fmt.print(date)));

        //Blank space
        document.add(new Paragraph("\n"));

        //Create the table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{10,1});

        //Populate the Table
        for (PurchaseOrder p : orders){
            PdfPCell[] cells = twoColumnRow(p.getPurchaseName(), Number.floatToStringAsNumber(p.getExistences()));
                table.addCell(cells[0]);
                table.addCell(cells[1]);
        }

        document.add(table);

        document.add(new Paragraph("\n"));
        document.add(new Paragraph("End of document."));

        document.close();
        return file;

    }

    private static Element title(String titleMessage){
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        Font font = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD, BaseColor.DARK_GRAY);
        PdfPCell cell = new PdfPCell(new Phrase(titleMessage, font));
        cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        return table;
    }

    private static Element subTitle(String subTitleMessage){
        PdfPTable table = new PdfPTable(1);
        table.setWidthPercentage(100);
        Font font = new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC, BaseColor.GRAY);
        PdfPCell cell = new PdfPCell(new Phrase(subTitleMessage, font));
        cell.setBorderColorBottom(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.BOTTOM);
        table.addCell(cell);
        return table;
    }

    private static PdfPCell[] twoColumnRow(String cell1Arg, String cell2Arg){
        Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.GRAY);
        PdfPCell cell1 = new PdfPCell();
        cell1.setPhrase(new Phrase(cell1Arg, font));
        cell1.setBorderColorBottom(BaseColor.LIGHT_GRAY);
        cell1.setBorder(Rectangle.BOTTOM);
        PdfPCell cell2 = new PdfPCell();
        cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell2.setPhrase(new Phrase(cell2Arg, font));
        cell2.setBorderColorBottom(BaseColor.LIGHT_GRAY);
        cell2.setBorder(Rectangle.BOTTOM);
        return new PdfPCell[]{cell1, cell2};
    }


    private static File getReportsStorageDir(String albumName){
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), albumName);
        if (!file.mkdirs()){
            Log.e("PDFBuilder", "Directory not created");
        }
        return file;
    }

    public static File buildSalesReport(Context context, String fileName, QuickSaleAdapter adapter,
                                        Resources res, MainMVP.QuickReportPresenterOps presenter,
                                        DateTime dateTime)
            throws IOException, DocumentException {
        Document document = new Document();
        File file = new File (getReportsStorageDir("kchin_reports").getPath(),fileName);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        //Title
        document.add(title(res.getString(R.string.sale_report)));

        //Date
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd, MMMM, yyyy");
        document.add(subTitle(fmt.print(dateTime)));

        //Blank space
        document.add(new Paragraph("\n"));

        //Create the table
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new int[]{10,3});

        //Populate the Table

        //Total sales
        PdfPCell[] totalSalesCells = twoColumnRow(res.getString(R.string.total_sales),
                adapter.getTotalSales());
        table.addCell(totalSalesCells[0]); table.addCell(totalSalesCells[1]);

        //Purchases and expenses
        PdfPCell[] purchasesCells = twoColumnRow(res.getString(R.string.purchases),
                adapter.getTotalPurchases());
        table.addCell(purchasesCells[0]); table.addCell(purchasesCells[1]);

        //Total earnings
        PdfPCell[] totalCells = twoColumnRow(res.getString(R.string.total_earnings),
                adapter.getTotalEarnings());
        table.addCell(totalCells[0]); table.addCell(totalCells[1]);


        document.add(new Paragraph("\n"));

        document.add(table);

        document.add(new Paragraph("\n"));

        //Details on sales
        document.add(subTitle(res.getString(R.string.recorded_ticket_id)+": "+
                adapter.getFormattedTickets()));

        PdfPTable detailSalesTable = new PdfPTable(2);
        detailSalesTable.setWidthPercentage(100);

        //Build details on sales
        final List<SaleTicket> saleTickets = presenter.getDaySaleTickets(dateTime);
        for (SaleTicket saleTicket : saleTickets){
            PdfPCell headerCell[] = twoColumnRow(res.getString(R.string.ticket_id) +
                    " "+String.valueOf(saleTicket.getId()),
                    Number.floatToStringAsPrice(saleTicket.saleTotal, true));
            detailSalesTable.addCell(headerCell[0]);
            detailSalesTable.addCell(headerCell[1]);
            List<Sale> sales = presenter.getSalesInTicket(saleTicket);
            for (Sale sale : sales){
                PdfPCell itemCells[] = twoColumnRow(sale.product.productName,
                        Number.floatToStringAsPrice(sale.saleTotal, true));
                detailSalesTable.addCell(itemCells[0]);
                detailSalesTable.addCell(itemCells[1]);
            }
        }
        document.add(detailSalesTable);

        document.add(new Paragraph("End of document."));

        document.close();
        return file;
    }
}