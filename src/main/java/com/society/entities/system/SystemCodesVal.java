package com.society.entities.system;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


/**
 * The persistent class for the system_codes_val database table.
 */
@Entity
@Table(name = "system_codes_val")
@NamedQuery(name = "SystemCodesVal.findAll", query = "SELECT s FROM SystemCodesVal s")
public class SystemCodesVal implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private SystemCodesValPK id;

    @Column(name = "CODE_DESC")
    private String codeDesc;

    @Column(name = "CODE_REF_ID")
    private Integer codeRefId;

    @Column(name = "CODE_SORT_DESC")
    private String codeSortDesc;

    @Column(name = "LAST_UPDATED_DATE")
    private Timestamp lastUpdatedDate;

    //bi-directional many-to-one association to SystemCode
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CODE_ID", insertable = false, updatable = false)
    private SystemCode systemCode;

    public SystemCodesVal() {
    }

    public SystemCodesValPK getId() {
        return this.id;
    }

    public void setId(SystemCodesValPK id) {
        this.id = id;
    }

    public String getCodeDesc() {
        return this.codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public Integer getCodeRefId() {
        return this.codeRefId;
    }

    public void setCodeRefId(Integer codeRefId) {
        this.codeRefId = codeRefId;
    }

    public String getCodeSortDesc() {
        return this.codeSortDesc;
    }

    public void setCodeSortDesc(String codeSortDesc) {
        this.codeSortDesc = codeSortDesc;
    }

    public Timestamp getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public SystemCode getSystemCode() {
        return this.systemCode;
    }

    public void setSystemCode(SystemCode systemCode) {
        this.systemCode = systemCode;
    }

}