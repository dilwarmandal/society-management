package com.society.dao;

import com.society.bo.SystemCodesBO;
import com.society.config.exception.ErrorHandler;
import com.society.config.exception.SystemException;
import com.society.config.exception.ValidationCode;
import com.society.entities.system.SystemCode;
import com.society.entities.system.SystemCodesVal;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Repository("utilityDao")
@Transactional
public class UtilityDaoImpl implements UtilityDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Map<String, Object> getDropDownList(SystemCodesBO systemCodesBO) {
        Map<String, Object> finalMap = new HashMap<String, Object>();
        //Fetching code id in respect of dropdown id

        List<SystemCode> systemCodeList = getSystemCodeList(systemCodesBO);
        if (systemCodeList != null && !systemCodeList.isEmpty()) {
            Map<String, String> stateMap = new LinkedHashMap<>();
            systemCodeList.forEach(item ->
                    systemCodesBO.setCodeId(item.getCodeId())
            );
        }
        try {
            List<SystemCodesVal> systemCodeValList = getSystemCodeValList(systemCodesBO);
            if (systemCodeValList != null && !systemCodeValList.isEmpty()) {
                Map<String, String> stateMap = new LinkedHashMap<>();
                systemCodeValList.forEach(item ->
                        stateMap.put(String.valueOf(item.getId().getCodeVal()), item.getCodeDesc())
                );
                finalMap.put("data", stateMap);
            } else {
                throw new SystemException(ValidationCode.STATES_FETCH_ERROR);
            }
        } catch (SystemException e) {
            SystemException.wrap(e, e.getErrorCode());
        }
        finalMap.put(ErrorHandler.class.getSimpleName(), SystemException.errorHandler);
        return finalMap;
    }

    public List<SystemCode> getSystemCodeList(SystemCodesBO systemCodesBO) {
        Criteria criteriaCodeId = sessionFactory.getCurrentSession().createCriteria(SystemCode.class);
        criteriaCodeId.add(Restrictions.eq("codeName", systemCodesBO.getCodeDesc()));
        List<SystemCode> systemCodeList = criteriaCodeId.list();
        return systemCodeList;
    }

    public List<SystemCodesVal> getSystemCodeValList(SystemCodesBO systemCodesBO) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SystemCodesVal.class);
        criteria.add(Restrictions.eq("id.codeId", systemCodesBO.getCodeId()));
        if (systemCodesBO.getCodeVal() > 0) {
            criteria.add(Restrictions.eq("id.codeVal", systemCodesBO.getCodeVal()));
        }
        if (systemCodesBO.getRefId() > 0) {
            criteria.add(Restrictions.eq("codeRefId", systemCodesBO.getRefId()));
        }
        List<SystemCodesVal> systemCodeValList = new ArrayList<>();
        try {
            systemCodeValList = criteria.list();
        } catch (Exception e) {
        }
        return systemCodeValList;
    }

    @Override
    public List<Map<String, Object>> getSystemCodeValMap(SystemCodesBO systemCodesBO) {
        List<SystemCodesVal> systemCodesValList = getSystemCodeValList(systemCodesBO);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (SystemCodesVal systemCodesVal : systemCodesValList) {
            Map<String, Object> linkedHashMap = new HashMap<>();
            linkedHashMap.put("code", systemCodesVal.getId().getCodeVal());
            linkedHashMap.put("name", systemCodesVal.getCodeDesc());
            mapList.add(linkedHashMap);
        }
        return mapList;
    }

    @Override
    public SystemCodesVal getSystemCodesVal(Integer codeId, Integer codeVal) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SystemCodesVal.class);
        criteria.add(Restrictions.eq("id.codeId", codeId));
        criteria.add(Restrictions.eq("id.codeVal", codeVal));
        SystemCodesVal systemCodesVal = new SystemCodesVal();
        try {
            Object object = criteria.uniqueResult();
            if (object != null) {
                systemCodesVal = (SystemCodesVal) object;
            }
        } catch (Exception e) {
        }
        return systemCodesVal;
    }
}