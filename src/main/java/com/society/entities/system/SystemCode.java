package com.society.entities.system;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;


/**
 * The persistent class for the system_codes database table.
 */
@Entity
@Table(name = "system_codes")
@NamedQuery(name = "SystemCode.findAll", query = "SELECT s FROM SystemCode s")
public class SystemCode implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CODE_ID")
    private Integer codeId;

    @Column(name = "CODE_NAME")
    private String codeName;

    @Column(name = "LAST_UPDATED_DATE")
    private Timestamp lastUpdatedDate;

    //bi-directional many-to-one association to SystemCodesVal
    @OneToMany(mappedBy = "systemCode")
    private Set<SystemCodesVal> systemCodesVals;

    public SystemCode() {
    }

    public Integer getCodeId() {
        return this.codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public String getCodeName() {
        return this.codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public Timestamp getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Timestamp lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Set<SystemCodesVal> getSystemCodesVals() {
        return this.systemCodesVals;
    }

    public void setSystemCodesVals(Set<SystemCodesVal> systemCodesVals) {
        this.systemCodesVals = systemCodesVals;
    }

    public SystemCodesVal addSystemCodesVal(SystemCodesVal systemCodesVal) {
        getSystemCodesVals().add(systemCodesVal);
        systemCodesVal.setSystemCode(this);

        return systemCodesVal;
    }

    public SystemCodesVal removeSystemCodesVal(SystemCodesVal systemCodesVal) {
        getSystemCodesVals().remove(systemCodesVal);
        systemCodesVal.setSystemCode(null);

        return systemCodesVal;
    }

}