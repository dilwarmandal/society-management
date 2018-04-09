package com.society.service;


import com.society.bo.SystemCodesBO;
import com.society.entities.system.SystemCode;
import com.society.entities.system.SystemCodesVal;

import java.util.List;

public interface UtilityService {
    Object getDropDownList(SystemCodesBO systemCodesBO);

    List<SystemCode> getSystemCodeList(SystemCodesBO systemCodesBO);

    List<SystemCodesVal> getSystemCodeValList(SystemCodesBO systemCodesBO);
}
