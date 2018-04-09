package com.society.entities.system;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the system_codes database table.
 */
@Entity
@Table(name = "statement")
@NamedQuery(name = "Statement.findAll", query = "SELECT s FROM Statement s")
public class Statement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STATEMENT_ID")
    private Integer statementId;

    @Column(name = "MONTH")
    private Integer month;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    public Integer getStatementId() {
        return statementId;
    }

    public void setStatementId(Integer statementId) {
        this.statementId = statementId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}