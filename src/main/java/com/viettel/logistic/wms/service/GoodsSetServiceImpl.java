/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.viettel.logistic.wms.service;

import com.viettel.logistic.wms.dto.GoodsSetDTO;
import com.viettel.vfw5.base.dto.ResultDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ngocnd6
 * @version 1.0
 * @since 08-Apr-15 2:43 PM
 */
@WebService(endpointInterface = "com.viettel.logistic.wms.service.GoodsSetService")
public class GoodsSetServiceImpl implements GoodsSetService {

    @Autowired
    BaseFWServiceInterface goodsSetBusiness;

    @Override
    public String updateGoodsSet(GoodsSetDTO goodsSetDTO) {
        return goodsSetBusiness.update(goodsSetDTO);
    }

    @Override
    public String deleteGoodsSet(Long id) {
        return goodsSetBusiness.delete(id);
    }

    @Override
    public String deleteListGoodsSet(List<GoodsSetDTO> goodsSetListDTO) {
        return goodsSetBusiness.delete(goodsSetListDTO);
    }

    @Override
    public GoodsSetDTO findGoodsSetById(Long id) {
        if (id != null && id > 0) {
            return (GoodsSetDTO) goodsSetBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<GoodsSetDTO> getListGoodsSetDTO(GoodsSetDTO goodsSetDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
        if (goodsSetDTO != null) {
            return goodsSetBusiness.search(goodsSetDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertGoodsSet(GoodsSetDTO goodsSetDTO) {
        return goodsSetBusiness.createObject(goodsSetDTO);
    }

    @Override
    public String insertOrUpdateListGoodsSet(List<GoodsSetDTO> goodsSetDTO) {
        return goodsSetBusiness.insertList(goodsSetDTO);
    }

}
