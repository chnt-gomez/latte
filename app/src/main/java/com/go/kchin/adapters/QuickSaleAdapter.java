package com.go.kchin.adapters;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by MAV1GA on 25/01/2017.
 */
public class QuickSaleAdapter {

    private String totalSales;
    private String totalPurchases;
    private String totalEarnings;
    private long[] ticketIds;
    private DateTime date;

    public String getTotalSales() {
        return totalSales;
    }

    public String getFormattedDate(){
       DateTimeFormatter fmt = DateTimeFormat.forPattern("dd, MMMM, yyyy");
        return fmt.print(date);
    }

    public void setTotalSales(String totalSales) {
        this.totalSales = totalSales;
    }

    public String getTotalPurchases() {
        return totalPurchases;
    }

    public void setTotalPurchases(String totalPurchases) {
        this.totalPurchases = totalPurchases;
    }

    public String getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(String totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public long[] getTicketIds() {
        return ticketIds;
    }

    public void setTicketIds(long[] ticketIds) {
        this.ticketIds = ticketIds;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public String getFormattedTickets() {
        if (ticketIds[0] != -1) {
            if (ticketIds[1] != -1){
                return String.valueOf(ticketIds[0]+ " - "+ticketIds[1]);
            }
            return String.valueOf(ticketIds[0]);
        }else{
            return " -- - --";
        }
    }
}
