package ru.task.iss.dto;
/*
 * Date: 4/23/21
 * Time: 7:25 PM
 * */

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

public class SecurityDto {

    @Digits(integer = 12, fraction = 0)
    private Integer id;

    @Size(max = 64)
    private String secId;

    @Size(max = 64)
    private String shortname;

    @Size(max = 128)
    private String regnumber;

    @Size(max = 128)
    private String name;

    @Size(max = 128)
    private String isin;

    @Digits(integer = 1, fraction = 0)
    private Integer isTraded;

    @Digits(integer = 12, fraction = 0)
    private Integer emitentId;

    @Size(max = 128)
    private String emitentTitle;

    @Size(max = 64)
    private String emitentInn;

    @Size(max = 64)
    private String emitentOkpo;

    @Size(max = 128)
    private String gosreg;

    @Size(max = 64)
    private String type;

    @Size(max = 64)
    private String group;

    @Size(max = 4)
    private String primaryBoardid;

    @Size(max = 4)
    private String marketPriceBoardid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
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

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Integer getIsTraded() {
        return isTraded;
    }

    public void setIsTraded(Integer isTraded) {
        this.isTraded = isTraded;
    }

    public Integer getEmitentId() {
        return emitentId;
    }

    public void setEmitentId(Integer emitentId) {
        this.emitentId = emitentId;
    }

    public String getEmitentTitle() {
        return emitentTitle;
    }

    public void setEmitentTitle(String emitentTitle) {
        this.emitentTitle = emitentTitle;
    }

    public String getEmitentInn() {
        return emitentInn;
    }

    public void setEmitentInn(String emitentInn) {
        this.emitentInn = emitentInn;
    }

    public String getEmitentOkpo() {
        return emitentOkpo;
    }

    public void setEmitentOkpo(String emitentOkpo) {
        this.emitentOkpo = emitentOkpo;
    }

    public String getGosreg() {
        return gosreg;
    }

    public void setGosreg(String gosreg) {
        this.gosreg = gosreg;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getPrimaryBoardid() {
        return primaryBoardid;
    }

    public void setPrimaryBoardid(String primaryBoardid) {
        this.primaryBoardid = primaryBoardid;
    }

    public String getMarketPriceBoardid() {
        return marketPriceBoardid;
    }

    public void setMarketPriceBoardid(String marketPriceBoardid) {
        this.marketPriceBoardid = marketPriceBoardid;
    }

    @Override
    public String toString() {
        return "SecurityDto{" +
                "id=" + id +
                ", secId='" + secId + '\'' +
                ", shortname='" + shortname + '\'' +
                '}';
    }
}
