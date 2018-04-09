package com.society.entities.system;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the system_codes_val database table.
 */
@Embeddable
public class SystemCodesValPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "CODE_ID", insertable = false, updatable = false)
    private Integer codeId;

    @Column(name = "CODE_VAL")
    private Integer codeVal;

    public SystemCodesValPK() {
    }

    public int getCodeId() {
        return this.codeId;
    }

    public void setCodeId(Integer codeId) {
        this.codeId = codeId;
    }

    public Integer getCodeVal() {
        return this.codeVal;
    }

    public void setCodeVal(Integer codeVal) {
        this.codeVal = codeVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SystemCodesValPK that = (SystemCodesValPK) o;

        if (codeId != null ? !codeId.equals(that.codeId) : that.codeId != null) return false;
        return codeVal != null ? codeVal.equals(that.codeVal) : that.codeVal == null;
    }

    @Override
    public int hashCode() {
        int result = codeId != null ? codeId.hashCode() : 0;
        result = 31 * result + (codeVal != null ? codeVal.hashCode() : 0);
        return result;
    }
}