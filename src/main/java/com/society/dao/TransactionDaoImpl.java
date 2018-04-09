package com.society.dao;

import com.society.util.HibernateUtil;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository("transactionDao")
@Transactional
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private HibernateUtil hibernateUtil;

    @Override
    public Map<String, BigDecimal> getAccountInformation(Integer transactionId) {
        String hql = "SELECT COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 THEN TRANS_AMOUNT END),0) as Credit," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 THEN TRANS_AMOUNT END),0) as Debit," +
                "COALESCE(SUM(CASE WHEN TRANS_MODE_VAL =1 AND TRANS_TYPE_VAL =1  THEN TRANS_AMOUNT END),0) - " +
                "COALESCE(SUM(CASE WHEN TRANS_MODE_VAL =1 AND TRANS_TYPE_VAL =2 THEN TRANS_AMOUNT END),0) as Cash," +
                "COALESCE(SUM(CASE WHEN TRANS_MODE_VAL =2 AND TRANS_TYPE_VAL =1  THEN TRANS_AMOUNT END),0) - " +
                "COALESCE(SUM(CASE WHEN TRANS_MODE_VAL =2 AND TRANS_TYPE_VAL =2 THEN TRANS_AMOUNT END),0) as Bank FROM Transaction WHERE TRANS_STATUS = 1";
        if (transactionId != null) {
            hql += "AND TRANS_ID <> " + transactionId;
        }
        List<Object[]> objects = hibernateUtil.fetchAll(hql);
        Object[] object = objects.get(0);
        Map<String, BigDecimal> accountMap = new HashMap<>();
        accountMap.put("credit", (BigDecimal) object[0]);
        accountMap.put("debit", (BigDecimal) object[1]);
        accountMap.put("cash", (BigDecimal) object[2]);
        accountMap.put("bank", (BigDecimal) object[3]);
        return accountMap;
    }

    @Override
    public Object[] getMonthlyTransactionByType(Integer transType) {
        String sql = "SELECT  " +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=1  THEN TRANS_AMOUNT END),0) AS January," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=2  THEN TRANS_AMOUNT END),0) AS February," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=3  THEN TRANS_AMOUNT END),0) AS March," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=4  THEN TRANS_AMOUNT END),0) AS April," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=5  THEN TRANS_AMOUNT END),0) AS May," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=6  THEN TRANS_AMOUNT END),0) AS June," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=7  THEN TRANS_AMOUNT END),0) AS July," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=8  THEN TRANS_AMOUNT END),0) AS August," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=9  THEN TRANS_AMOUNT END),0) AS September," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=10  THEN TRANS_AMOUNT END),0) AS October," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=11 THEN TRANS_AMOUNT END),0) AS November," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =:txnType AND month(TRANS_DATE)=12  THEN TRANS_AMOUNT END),0) AS December" +
                " FROM transaction WHERE TRANS_STATUS = 1 AND YEAR(TRANS_DATE) = YEAR(CURDATE()) GROUP BY YEAR(CURDATE())";
        SQLQuery sqlQuery = sessionFactory.getCurrentSession().createSQLQuery(sql);
        sqlQuery.setInteger("txnType", transType);
        List<Object[]> objects = sqlQuery.list();
        return objects.isEmpty() ? null : objects.get(0);
    }

    @Override
    public Object[] getMonthTransactionAmount() {
        String sql = "SELECT " +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=1  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=1  THEN TRANS_AMOUNT END),0) as January," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=2  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=2  THEN TRANS_AMOUNT END),0) as February," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=3  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=3  THEN TRANS_AMOUNT END),0) as March," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=4  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=4  THEN TRANS_AMOUNT END),0) as April," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=5  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=5  THEN TRANS_AMOUNT END),0) as May," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=6  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=6  THEN TRANS_AMOUNT END),0) as June," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=7  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=7  THEN TRANS_AMOUNT END),0) as July," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=8  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=8  THEN TRANS_AMOUNT END),0) as August," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=9  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=9  THEN TRANS_AMOUNT END),0) as September," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=10  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=10  THEN TRANS_AMOUNT END),0) as October," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=11 THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=11 THEN TRANS_AMOUNT END),0) as November," +
                "COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =1 AND month(TRANS_DATE)=12  THEN TRANS_AMOUNT END),0) - COALESCE(SUM(CASE WHEN TRANS_TYPE_VAL =2 AND month(TRANS_DATE)=12  THEN TRANS_AMOUNT END),0) as December" +
                " FROM transaction WHERE TRANS_STATUS = 1 AND YEAR(TRANS_DATE) = YEAR(CURDATE())" +
                " GROUP BY Year(CURDATE())";
        List<Object[]> objects = hibernateUtil.fetchAll(sql);
        return objects.get(0);
    }
}