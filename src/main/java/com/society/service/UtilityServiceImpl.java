package com.society.service;

import com.society.bo.SystemCodesBO;
import com.society.dao.UtilityDao;
import com.society.entities.system.SystemCode;
import com.society.entities.system.SystemCodesVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("utilityService")
@Transactional
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    UtilityDao utilityDao;

    @Override
    public Object getDropDownList(SystemCodesBO systemCodesBO) {
        return utilityDao.getDropDownList(systemCodesBO);
    }

    @Override
    public List<SystemCode> getSystemCodeList(SystemCodesBO systemCodesBO) {
        return utilityDao.getSystemCodeList(systemCodesBO);
    }

    @Override
    public List<SystemCodesVal> getSystemCodeValList(SystemCodesBO systemCodesBO) {
        return utilityDao.getSystemCodeValList(systemCodesBO);
    }
}
