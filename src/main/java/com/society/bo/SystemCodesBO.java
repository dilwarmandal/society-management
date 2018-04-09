package com.society.bo;

public class SystemCodesBO {
    int codeId;
    String codeDesc;
    int codeVal;
    int refId;
    String description;

    public SystemCodesBO() {
    }

    public SystemCodesBO(int codeId, int codeVal) {
        this.codeId = codeId;
        this.codeVal = codeVal;
    }

    public SystemCodesBO(int codeId, int codeVal, int refId) {
        this.codeId = codeId;
        this.codeVal = codeVal;
        this.refId = refId;
    }


    public int getCodeId() {
        return codeId;
    }

    public void setCodeId(int codeId) {
        this.codeId = codeId;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public int getCodeVal() {
        return codeVal;
    }

    public void setCodeVal(int codeVal) {
        this.codeVal = codeVal;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
