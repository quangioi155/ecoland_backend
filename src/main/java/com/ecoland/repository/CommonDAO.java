package com.ecoland.repository;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ecoland.common.Constants;

/**
 * 
 * @author thaotv@its-global.vn
 * 
 */
@Repository
public class CommonDAO {
    private static final Logger logger = LoggerFactory.getLogger(CommonDAO.class);
    @Autowired
    private EntityManager entityManager;

    /**
     * 
     * @author thaotv@its-global.vn method update updated_at, updated_by,
     *         delete_flag in table
     */
    public void sqlCommonDAO(String table, Long recordId, String action, Integer idLogin) {
	logger.info("-- Update table " + table + " --");
	Session session = entityManager.unwrap(Session.class);
	StringBuilder sql = new StringBuilder();
	sql.append("UPDATE ");
	sql.append(table);
	sql.append(" SET ");
	if (action == Constants.DELETE) {
	    sql.append(" delete_flag =:deleteFlag ,");
	    sql.append(sqlAddpend());
	} else if (action == Constants.UPDATE) {
	    sql.append(sqlAddpend());
	}
	sql.append(" WHERE id =:recordId");
	NativeQuery<?> query = session.createNativeQuery(sql.toString());
	if (action == Constants.DELETE) {
	    query.setParameter("deleteFlag", Constants.DELETE_TRUE);
	    setParam(query, idLogin);
	} else if (action == Constants.UPDATE) {
	    setParam(query, idLogin);
	}
	query.setParameter("recordId", recordId);
	query.executeUpdate();
	session.close();
    }

    /**
     * 
     * @author thaotv@its-global.vn method addpend string
     */
    public String sqlAddpend() {
	StringBuilder sql = new StringBuilder();
	sql.append(" updated_by =:updateBy ,");
	sql.append(" updated_at =:updateAt ");
	return sql.toString();
    }

    /**
     * 
     * @author thaotv@its-global.vn method set param
     */
    public void setParam(NativeQuery<?> query, Integer idLogin) {
	query.setParameter("updateBy", idLogin);
	query.setParameter("updateAt", new Timestamp(new Date().getTime()));
    }
}
