package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.Introductions;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.FormIntroductionRequest;
import com.ecoland.model.request.system.SearchIntroductionRequest;
import com.ecoland.model.response.system.FormIntroductionResponse;
import com.ecoland.model.response.system.SearchIntroductionResponse;
import com.ecoland.repository.IntroductionRepository;
import com.ecoland.repository.dao.IntroductionDAO;

/**
 * @class IntroductionService
 * 
 * @service of introduction function
 * 
 * @author ITSG - HoanNNC
 */
@Service
public class IntroductionService {
    private static final Logger logger = LoggerFactory.getLogger(IntroductionService.class);
    
    @Autowired
    private CommonService commonService;

    @Autowired
    private IntroductionDAO introductionDAO;
    
    @Autowired
    private IntroductionRepository introductionRepository;
    
    @Autowired
    private SessionFactory sessionFactory;
    
    public ResultPageResponse searchIntroduction(SearchIntroductionRequest req) {
        logger.info("-- get list introduction --");
        int totalRecord = introductionDAO.getTotalRecord(req);
        List<SearchIntroductionResponse> data = introductionDAO.getIntroductionList(req);
        ResultPageResponse response = new ResultPageResponse(totalRecord, data, null, req.getPageNo());

        return response;
    }
    
    public int softDeleteIntroduction(int id) {
        logger.info("-- soft delete introduction --");
        Optional<Introductions> opItem = introductionRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND);
        }
        // check foreign key constraint
        Session session = this.sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Introductions itemRefer = (Introductions)session.get(Introductions.class, id);
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
        Introductions itemDel = opItem.get();
        itemDel.setDeleteFlag(Constants.DELETE_TRUE);

        introductionRepository.save(itemDel);
        return itemDel.getId();
    }
    
    public int onChangeIntroduction(FormIntroductionRequest req) {
        if (req.getId() != null) {
            return updateIntroduction(req);
        } else {
            return registIntroduction(req);
        }
    }

    private int registIntroduction(FormIntroductionRequest req) {
        logger.info("-- regist introduction --");
        if (introductionRepository.countByIntroductionNameAndDeleteFlag(req.getIntroductionName(), Constants.DELETE_NONE) > 0) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        Introductions item = new Introductions();
        item.setIntroductionName(req.getIntroductionName());
        item.setIntroductionShortName(req.getIntroductionShortName());
        item.setIntroductionNameKana(req.getIntroductionNameKana());
        item.setPostalCode(req.getPostalCode());
        item.setAddress1(req.getAddress1());
        item.setAddress2(req.getAddress2());
        item.setAddress3(req.getAddress3());
        item.setTel(req.getFax());
        item.setFax(req.getFax());
        item.setPayTiming(req.getPayTiming());
        item.setPayUnit(req.getPayUnit());
        item.setPayAmount(req.getPayAmount());
        item.setPayPercent(req.getPayPercent());
        item.setDiscountUnit(req.getDiscountUnit());
        item.setDiscount(req.getDiscount());
        item.setDiscountPercent(req.getDiscountPercent());
        item.setSortNo(req.getSortNo());
        item.setCreateAt(new Timestamp(new Date().getTime()));
        item.setUpdatedAt(new Timestamp(new Date().getTime()));
        item.setCreatedBy(commonService.idUserAccountLogin());
        item.setUpdatedBy(0);
        item.setDeleteFlag(Constants.DELETE_NONE);

        return introductionRepository.save(item).getId();
    }

    private int updateIntroduction(FormIntroductionRequest req) {
        logger.info("-- update introduction --");
        Optional<Introductions> opItem = introductionRepository.findByIdAndDeleteFlag(req.getId(), Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        Introductions item = opItem.get();
        if (!item.getIntroductionName().trim().equals(req.getIntroductionName().trim())
                && introductionRepository.countByIntroductionNameAndDeleteFlag(req.getIntroductionName(), Constants.DELETE_NONE) > 0) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        item.setIntroductionName(req.getIntroductionName());
        item.setIntroductionShortName(req.getIntroductionShortName());
        item.setIntroductionNameKana(req.getIntroductionNameKana());
        item.setPostalCode(req.getPostalCode());
        item.setAddress1(req.getAddress1());
        item.setAddress2(req.getAddress2());
        item.setAddress3(req.getAddress3());
        item.setTel(req.getFax());
        item.setFax(req.getFax());
        item.setPayTiming(req.getPayTiming());
        item.setPayUnit(req.getPayUnit());
        item.setPayAmount(req.getPayAmount());
        item.setPayPercent(req.getPayPercent());
        item.setDiscountUnit(req.getDiscountUnit());
        item.setDiscount(req.getDiscount());
        item.setDiscountPercent(req.getDiscountPercent());
        item.setSortNo(req.getSortNo());
        item.setUpdatedAt(new Timestamp(new Date().getTime()));
        item.setUpdatedBy(commonService.idUserAccountLogin());

        return introductionRepository.save(item).getId();
    }
    
    public FormIntroductionResponse getIntroductionDetail(int id) {
        logger.info("-- get detail introduction --");
        Optional<Introductions> opItem = introductionRepository.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        Introductions detail = opItem.get();
        FormIntroductionResponse resDetail = new FormIntroductionResponse();
        resDetail.setId(detail.getId());
        resDetail.setIntroductionName(detail.getIntroductionName());
        resDetail.setIntroductionShortName(detail.getIntroductionShortName());
        resDetail.setIntroductionNameKana(detail.getIntroductionNameKana());
        resDetail.setPostalCode(detail.getPostalCode());
        resDetail.setAddress1(detail.getAddress1());
        resDetail.setAddress2(detail.getAddress2());
        resDetail.setAddress3(detail.getAddress3());
        resDetail.setTel(detail.getFax());
        resDetail.setFax(detail.getFax());
        resDetail.setPayTiming(detail.getPayTiming());
        resDetail.setPayUnit(detail.getPayUnit());
        resDetail.setPayAmount(detail.getPayAmount());
        resDetail.setPayPercent(detail.getPayPercent());
        resDetail.setDiscountUnit(detail.getDiscountUnit());
        resDetail.setDiscount(detail.getDiscount());
        resDetail.setDiscountPercent(detail.getDiscountPercent());
        resDetail.setSortNo(detail.getSortNo());

        return resDetail;
    }
}
