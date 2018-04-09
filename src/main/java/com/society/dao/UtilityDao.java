package com.society.dao;

import com.society.bo.SystemCodesBO;
import com.society.entities.system.SystemCode;
import com.society.entities.system.SystemCodesVal;

import java.util.List;
import java.util.Map;

public interface UtilityDao {
    Map<String, Object> getDropDownList(SystemCodesBO systemCodesBO);

    List<SystemCode> getSystemCodeList(SystemCodesBO systemCodesBO);

    List<SystemCodesVal> getSystemCodeValList(SystemCodesBO systemCodesBO);

    List<Map<String, Object>> getSystemCodeValMap(SystemCodesBO systemCodesBO);

    SystemCodesVal getSystemCodesVal(Integer codeId, Integer codeVal);

}
