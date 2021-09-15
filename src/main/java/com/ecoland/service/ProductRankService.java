package com.ecoland.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecoland.common.CommonService;
import com.ecoland.common.Constants;
import com.ecoland.entity.ProductRanks;
import com.ecoland.exception.RecordNotFoundException;
import com.ecoland.model.ResultPageResponse;
import com.ecoland.model.request.system.FormRankRequest;
import com.ecoland.model.request.system.SearchRankRequest;
import com.ecoland.model.response.DropdownListResponse;
import com.ecoland.model.response.system.FormRankResponse;
import com.ecoland.model.response.system.SearchRankResponse;
import com.ecoland.repository.ProductRanksRepository;
import com.ecoland.repository.dao.ProductRankDao;

/**
 * @class ProductRankService
 * 
 * @service of product rank function
 * 
 * @author ITSG - HoanNNC
 */
@Service
@Transactional
public class ProductRankService {

    private static final Logger logger = LoggerFactory.getLogger(ProductRankService.class);

    @Autowired
    private ProductRankDao productRankDao;

    @Autowired
    private ProductRanksRepository pRankReposistory;

    @Autowired
    private CommonService commonService;

    /**
     * 
     * @author thaotv@its-global.vn
     * @return Product rank drop down list
     */
    @Cacheable(cacheNames = "productRankDropdownList")
    public List<DropdownListResponse> productRankDropDownList() {
        logger.info("-- Get list dropdown product rank --");
        return pRankReposistory.productRankDropDownList();
    }

    public ResultPageResponse searchRank(SearchRankRequest req) {
        int totalRecord = productRankDao.getTotalRecord(req);
        List<SearchRankResponse> data = productRankDao.getRankList(req);
        ResultPageResponse response = new ResultPageResponse(totalRecord, data, null, req.getPageNo());

        return response;
    }

    public int softDeleteRank(int id) {
        Optional<ProductRanks> opItem = pRankReposistory.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException(Constants.RECORD_NOT_FOUND);
        }
        ProductRanks itemDel = opItem.get();
        itemDel.setDeleteFlag(Constants.DELETE_TRUE);

        pRankReposistory.save(itemDel);
        return itemDel.getId();
    }

    public int onChangeRank(FormRankRequest req) {
        if (req.getId() != null) {
            return updateRank(req);
        } else {
            return registRank(req);
        }
    }

    private int registRank(FormRankRequest req) {
        if (pRankReposistory.countByProductRankNameAndDeleteFlag(req.getProductRankName(), Constants.DELETE_NONE) > 0) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        Integer idLogin = commonService.idUserAccountLogin();

        ProductRanks rank = new ProductRanks();
        rank.setProductRankName(req.getProductRankName().trim());
        rank.setProductSize(req.getSize());
        rank.setWeight(req.getWeight());
        rank.setPriceNotax(req.getPriceNotax());
        rank.setCreateAt(new Timestamp(new Date().getTime()));
        rank.setUpdatedAt(new Timestamp(new Date().getTime()));
        rank.setCreatedBy(idLogin);
        rank.setUpdatedBy(0);
        rank.setDeleteFlag(0);

        return pRankReposistory.save(rank).getId();
    }

    private int updateRank(FormRankRequest req) {
        Optional<ProductRanks> opItem = pRankReposistory.findByIdAndDeleteFlag(req.getId(), Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        ProductRanks rank = opItem.get();
        if (!rank.getProductRankName().trim().equals(req.getProductRankName().trim()) && pRankReposistory
                .countByProductRankNameAndDeleteFlag(req.getProductRankName(), Constants.DELETE_NONE) > 0) {
            throw new DuplicateKeyException(Constants.RECORD_ALREADY_EXISTS);
        }
        rank.setProductRankName(req.getProductRankName().trim());
        rank.setProductSize(req.getSize());
        rank.setWeight(req.getWeight());
        rank.setPriceNotax(req.getPriceNotax());
        rank.setUpdatedAt(new Timestamp(new Date().getTime()));
        rank.setUpdatedBy(commonService.idUserAccountLogin());

        return pRankReposistory.save(rank).getId();
    }

    public FormRankResponse getRankDetail(int id) {
        Optional<ProductRanks> opItem = pRankReposistory.findByIdAndDeleteFlag(id, Constants.DELETE_NONE);
        if (opItem.isEmpty()) {
            throw new RecordNotFoundException("record not found");
        }
        ProductRanks detail = opItem.get();

        FormRankResponse resDetail = new FormRankResponse();
        resDetail.setId(detail.getId());
        resDetail.setProductRankName(detail.getProductRankName());
        resDetail.setSize(detail.getProductSize());
        resDetail.setWeight(detail.getWeight());
        resDetail.setPriceNotax(detail.getPriceNotax());

        return resDetail;
    }
}
