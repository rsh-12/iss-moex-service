package ru.task.iss.entity;
/*
 * Date: 4/20/21
 * Time: 4:49 PM
 * */

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "securities")
public class Security {

    @Column(unique = true, nullable = false)
    private Integer id;

    @Id
    @Size(max = 64)
    @NotBlank
    @Column(unique = true, nullable = false, name = "sec_id")
    private String secId;

    @Size(max = 64)
    private String shortname;

    @Size(max = 128)
    private String regnumber;

    @Size(max = 128)
    private String name;

    @Size(max = 128)
    private String isin;

    private Integer isTraded;

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
    @Column(name = "`group`")
    private String group;

    @Size(max = 4)
    private String primaryBoardid;

    @Size(max = 4)
    @Column(name = "marketprice_boardid")
    private String marketPriceBoardid;

    @OneToMany(mappedBy = "security", fetch = FetchType.LAZY,
            cascade = {CascadeType.REMOVE})
    private List<History> history;

    public Security() {
    }

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

    public List<History> getHistory() {
        return history;
    }

    public void setHistory(List<History> history) {
        this.history = history;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Security security = (Security) o;

        if (!id.equals(security.id)) return false;
        if (!Objects.equals(secId, security.secId)) return false;
        return Objects.equals(shortname, security.shortname);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (secId != null ? secId.hashCode() : 0);
        result = 31 * result + (shortname != null ? shortname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Security{" +
                "id=" + id +
                ", secId='" + secId + '\'' +
                ", shortname='" + shortname + '\'' +
                '}';
    }
}
