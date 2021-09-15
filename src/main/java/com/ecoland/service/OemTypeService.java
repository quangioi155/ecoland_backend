package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.OemTypes;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.FormOemTypeRequest;
import com.ecoland.model.request.system.SearchOemTypeRequest;
import com.ecoland.model.response.system.FormOemTypeResponse;
import com.ecoland.model.response.system.SearchOemTypeResponse;
import com.ecoland.repository.OemTypeRepository;
import com.ecoland.repository.dao.OemTypeDao;

/**
 * @class OemTypeService
 * 
 * @service of oem type function
 * 
 * @author ITSG - HoanNNC
 */
@Service
public class OemTypeService {

    @Autowired
    private CommonService commonService;

    @Autowired
    private OemTypeDao oemTypeDao;

    @Autowired
    private OemTypeRepository oemTypeRepository;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public ResultPageResponse searchOemType(SearchOemTypeRequest req) {
        int totalRecord = oemTypeDao.getTotalRecord(req);
        List<SearchOemTypeResponse> data = oemTypeDao.getOemTypeList(req);
        ResultPageResponse response = new ResultPageResponse(totalRecord, data, null, req.getPageNo());

        return response;
    }

    public int softDeleteOemType(int id) {
        Optional<OemTypes> opItem = oemTypeRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND);
        }
        // check foreign key constraint
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            OemTypes itemRefer = (OemTypes)session.get(OemTypes.class, id);
            session.delete(itemRefer);
            session.flush();
            tx.rollback();
        }catch (Exception e) {
            // TODO: handle exception
            tx.rollback();
            throw new HibernateException(e.getCause());
        }
        finally {
            session.close();
        }
        OemTypes itemDel = opItem.get();
        itemDel.setDeleteFlag(Constants.DELETE_TRUE);
        oemTypeRepository.save(itemDel);
        return itemDel.getId();
    }

    public int onChangeOemType(FormOemTypeRequest req) {
        if (req.getId() != null) {
            return updateOemType(req);
        } else {
            return registOemType(req);
        }
    }

    private int registOemType(FormOemTypeRequest req) {
        if (oemTypeRepository.countByOemNameAndDeleteFlag(req.getOemName(), Constants.DELETE_NONE) > 0) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        OemTypes item = new OemTypes();
        item.setOemName(req.getOemName());
        item.setSortNo(req.getSortNo());
        item.setCreateAt(new Timestamp(new Date().getTime()));
        item.setUpdatedAt(new Timestamp(new Date().getTime()));
        item.setCreatedBy(commonService.idUserAccountLogin());
        item.setUpdatedBy(0);
        item.setDeleteFlag(Constants.DELETE_NONE);

        return oemTypeRepository.save(item).getId();
    }

    private int updateOemType(FormOemTypeRequest req) {
        Optional<OemTypes> opItem = oemTypeRepository.findByIdAndDeleteFlag(req.getId(), Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        OemTypes item = opItem.get();
        if (!item.getOemName().trim().equals(req.getOemName().trim())
                && oemTypeRepository.countByOemNameAndDeleteFlag(req.getOemName(), Constants.DELETE_NONE) > 0) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        item.setOemName(req.getOemName());
        item.setSortNo(req.getSortNo());
        item.setUpdatedAt(new Timestamp(new Date().getTime()));
        item.setUpdatedBy(commonService.idUserAccountLogin());

        return oemTypeRepository.save(item).getId();
    }

    public FormOemTypeResponse getOemTypeDetail(int id) {
        Optional<OemTypes> opItem = oemTypeRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        OemTypes detail = opItem.get();
        FormOemTypeResponse resDetail = new FormOemTypeResponse();
        resDetail.setId(detail.getId());
        resDetail.setOemName(detail.getOemName());
        resDetail.setSortNo(detail.getSortNo());

        return resDetail;
    }
}
