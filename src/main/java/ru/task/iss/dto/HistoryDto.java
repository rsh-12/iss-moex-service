package ru.task.iss.dto;
/*
 * Date: 4/24/21
 * Time: 9:18 AM
 * */

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Digits;
import java.time.LocalDate;

public class HistoryDto {

    private String boardId;

    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate tradeDate;

    private String shortname;

    private String secId;

    @Digits(integer = 12, fraction = 4)
    private Double numTrades;

    @Digits(integer = 12, fraction = 4)
    private Double value;

    @Digits(integer = 12, fraction = 4)
    private Double open;

    @Digits(integer = 12, fraction = 4)
    private Double low;

    @Digits(integer = 12, fraction = 4)
    private Double high;

    @Digits(integer = 12, fraction = 4)
    private Double legalClosePrice;

    @Digits(integer = 12, fraction = 4)
    private Double waPrice;

    @Digits(integer = 12, fraction = 4)
    private Double close;

    @Digits(integer = 12, fraction = 4)
    private Double volume;

    @Digits(integer = 12, fraction = 4)
    private Double marketPrice2;

    @Digits(integer = 12, fraction = 4)
    private Double marketPrice3;

    @Digits(integer = 12, fraction = 4)
    private Double admittedQuote;

    @Digits(integer = 12, fraction = 4)
    private Double mp2ValTrd;

    @Digits(integer = 12, fraction = 4)
    private Double marketPrice3TradesValue;

    @Digits(integer = 12, fraction = 4)
    private Double admittedValue;

    @Digits(integer = 12, fraction = 4)
    private Double waVal;

    public String getBoardId() {
        return boardId;
    }

    public void setBoardId(String boardId) {
        this.boardId = boardId;
    }

    public LocalDate getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(LocalDate tradeDate) {
        this.tradeDate = tradeDate;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Double getNumTrades() {
        return numTrades;
    }

    public void setNumTrades(Double numTrades) {
        this.numTrades = numTrades;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Double getOpen() {
        return open;
    }

    public void setOpen(Double open) {
        this.open = open;
    }

    public Double getLow() {
        return low;
    }

    public void setLow(Double low) {
        this.low = low;
    }

    public Double getHigh() {
        return high;
    }

    public void setHigh(Double high) {
        this.high = high;
    }

    public Double getLegalClosePrice() {
        return legalClosePrice;
    }

    public void setLegalClosePrice(Double legalClosePrice) {
        this.legalClosePrice = legalClosePrice;
    }

    public Double getWaPrice() {
        return waPrice;
    }

    public void setWaPrice(Double waPrice) {
        this.waPrice = waPrice;
    }

    public Double getClose() {
        return close;
    }

    public void setClose(Double close) {
        this.close = close;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(Double volume) {
        this.volume = volume;
    }

    public Double getMarketPrice2() {
        return marketPrice2;
    }

    public void setMarketPrice2(Double marketPrice2) {
        this.marketPrice2 = marketPrice2;
    }

    public Double getMarketPrice3() {
        return marketPrice3;
    }

    public void setMarketPrice3(Double marketPrice3) {
        this.marketPrice3 = marketPrice3;
    }

    public Double getAdmittedQuote() {
        return admittedQuote;
    }

    public void setAdmittedQuote(Double admittedQuote) {
        this.admittedQuote = admittedQuote;
    }

    public Double getMp2ValTrd() {
        return mp2ValTrd;
    }

    public void setMp2ValTrd(Double mp2ValTrd) {
        this.mp2ValTrd = mp2ValTrd;
    }

    public Double getMarketPrice3TradesValue() {
        return marketPrice3TradesValue;
    }

    public void setMarketPrice3TradesValue(Double marketPrice3TradesValue) {
        this.marketPrice3TradesValue = marketPrice3TradesValue;
    }

    public Double getAdmittedValue() {
        return admittedValue;
    }

    public void setAdmittedValue(Double admittedValue) {
        this.admittedValue = admittedValue;
    }

    public Double getWaVal() {
        return waVal;
    }

    public void setWaVal(Double waVal) {
        this.waVal = waVal;
    }

    @Override
    public String toString() {
        return "HistoryDto{" +
                "shortname='" + shortname + '\'' +
                ", secId='" + secId + '\'' +
                '}';
    }
}
