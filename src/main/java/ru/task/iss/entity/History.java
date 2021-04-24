package ru.task.iss.entity;
/*
 * Date: 4/20/21
 * Time: 7:33 PM
 * */


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.server.core.Relation;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "history")
@Relation(value = "history", collectionRelation = "history")
public class History {

    // fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long id;

    private String boardId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate tradeDate;

    private String shortname;

    @Column(name = "sec_id_fk", insertable = false, updatable = false)
    private String secId;

    private Double numTrades;

    private Double value;

    private Double open;

    private Double low;

    private Double high;

    private Double legalClosePrice;

    private Double waPrice;

    private Double close;

    private Double volume;

    private Double marketPrice2;

    private Double marketPrice3;

    private Double admittedQuote;

    @Column(name = "mp2_val_trd")
    private Double mp2ValTrd;

    @Column(name = "market_price3_trades_value")
    private Double marketPrice3TradesValue;

    private Double admittedValue;

    private Double waVal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sec_id_fk", referencedColumnName = "sec_id")
    private Security security;

    // constructor
    public History() {
    }

    // getter/setters
    public void setSecurity(Security security) {
        this.security = security;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return "History{" +
                "boardId='" + boardId + '\'' +
                ", secId='" + secId + '\'' +
                '}';
    }
}
