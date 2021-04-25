package ru.task.iss.dto;
/*
 * Date: 4/25/21
 * Time: 8:05 AM
 * */

import java.time.LocalDate;

/* For retrieving some fields from DB */
public class SecurityHistoryDto {

    private String secId;
    private String regnumber;
    private String name;
    private String emitentTitle;
    private LocalDate tradeDate;
    private Double numTrades;
    private Double open;
    private Double close;

    public SecurityHistoryDto(String secId, String regnumber, String name, String emitentTitle,
                              LocalDate tradeDate, Double numTrades, Double open, Double close) {
        this.secId = secId;
        this.regnumber = regnumber;
        this.name = name;
        this.emitentTitle = emitentTitle;
        this.tradeDate = tradeDate;
        this.numTrades = numTrades;
        this.open = open;
        this.close = close;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmitentTitle() {
        return emitentTitle;
    }

    public void setEmitentTitle(String emitentTitle) {
        this.emitentTitle = emitentTitle;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public Double getNumTrades() {
        return numTrades;
    }

    public void setNumTrades(Double numTrades) {
        this.numTrades = numTrades;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }
}
