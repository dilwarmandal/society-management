package com.society.service;

import com.society.bo.SystemCodesBO;

import java.util.List;
import java.util.Map;

public interface ApplicationService {
    List<Map<String, Object>> getCountryList(String countryId);

    List<Map<String, Object>> getRegionList(String countryId, String stateId);

    List<Map<String, Object>> getCityList(String stateId, String cityId);

    List<Map<String, Object>> getTitleList(String titleId);

    List<Map<String, Object>> getGenderList(String genderId);

    List<Map<String, Object>> getTransactionModeList(String transModeId);

    List<Map<String, Object>> getTransactionSelectList(String tansSelectId, Integer selectionId);

    List<Map<String, Object>> getTransactionTypeList(String transTypeId);

    List<Map<String, Object>> getDropDownListService(SystemCodesBO systemCodesBO);

}
