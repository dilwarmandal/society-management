package com.society.service;

import com.society.bo.SystemCodesBO;
import com.society.config.exception.ApplicationCode;
import com.society.dao.UtilityDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("applicationService")
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    UtilityDao utilityDao;

    @Override
    public List<Map<String, Object>> getCountryList(String countryId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeId(ApplicationCode.COUNTRY.getCodeId());
        if (!StringUtils.equalsIgnoreCase("all", countryId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(countryId));
        }
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getRegionList(String countryId, String stateId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeId(ApplicationCode.STATE.getCodeId());
        if (!StringUtils.equalsIgnoreCase("all", stateId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(stateId));
        }
        systemCodesBO.setRefId(Integer.parseInt(countryId));
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getCityList(String stateId, String cityId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeId(ApplicationCode.CITY.getCodeId());
        if (!StringUtils.equalsIgnoreCase("all", cityId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(cityId));
        }
        systemCodesBO.setRefId(Integer.parseInt(stateId));
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getTitleList(String titleId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeId(ApplicationCode.TITLE.getCodeId());
        if (!StringUtils.equalsIgnoreCase("all", titleId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(titleId));
        }
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getGenderList(String genderId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeId(ApplicationCode.GENDER.getCodeId());
        if (!StringUtils.equalsIgnoreCase("all", genderId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(genderId));
        }
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getTransactionModeList(String transModeId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeId(ApplicationCode.TRANSACTION_MODES.getCodeId());
        if (!StringUtils.equalsIgnoreCase("all", transModeId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(transModeId));
        }
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getTransactionSelectList(String tansSelectId, Integer selectionId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        if (selectionId == 1) {
            systemCodesBO.setCodeId(ApplicationCode.TRANSACTION_CREDIT_SELECTION.getCodeId());
        }
        if (selectionId == 2) {
            systemCodesBO.setCodeId(ApplicationCode.TRANSACTION_DEBIT_SELECTION.getCodeId());
        }
        if (!StringUtils.equalsIgnoreCase("all", tansSelectId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(tansSelectId));
        }
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getTransactionTypeList(String transTypeId) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeId(ApplicationCode.TRANSACTION_TYPES.getCodeId());
        if (!StringUtils.equalsIgnoreCase("all", transTypeId)) {
            systemCodesBO.setCodeVal(Integer.parseInt(transTypeId));
        }
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }

    @Override
    public List<Map<String, Object>> getDropDownListService(SystemCodesBO systemCodesBO) {
        return utilityDao.getSystemCodeValMap(systemCodesBO);
    }
}
