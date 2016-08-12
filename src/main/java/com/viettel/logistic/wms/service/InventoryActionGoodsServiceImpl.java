
/*
* Copyright (C) 2011 Viettel Telecom. All rights reserved.
* VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*/
package com.viettel.logistic.wms.service;
import com.viettel.logistic.wms.dto.InventoryActionGoodsDTO;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import java.util.Locale;
import javax.jws.WebService;
import java.util.ArrayList;
import com.viettel.vfw5.base.pojo.ConditionBean;
import com.viettel.vfw5.base.utils.ParamUtils;
import com.viettel.vfw5.base.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.viettel.vfw5.base.dto.ResultDTO;

/**
* @author hongdq4
* @version 1.0
* @since 6/9/2015 11:28 PM
*/
@WebService(endpointInterface = "com.viettel.logistic.wms.service.InventoryActionGoodsService")
public class InventoryActionGoodsServiceImpl implements InventoryActionGoodsService {
    @Autowired
    BaseFWServiceInterface inventoryActionGoodsBusiness;
    
   
    @Override
    public String updateInventoryActionGoods(InventoryActionGoodsDTO inventoryActionGoodsDTO) {
        return inventoryActionGoodsBusiness.update(inventoryActionGoodsDTO);
    }

    @Override
    public String deleteInventoryActionGoods(Long id) {
        return inventoryActionGoodsBusiness.delete(id);
    }

    @Override
    public String deleteListInventoryActionGoods(List<InventoryActionGoodsDTO> inventoryActionGoodsListDTO) {
        return inventoryActionGoodsBusiness.delete(inventoryActionGoodsListDTO);
    }

    @Override
    public InventoryActionGoodsDTO findInventoryActionGoodsById(Long id) {
        if (id != null && id > 0) {
            return (InventoryActionGoodsDTO)inventoryActionGoodsBusiness.findById(id).toDTO();
        }
        return null;
    }

    @Override
    public List<InventoryActionGoodsDTO> getListInventoryActionGoodsDTO(InventoryActionGoodsDTO inventoryActionGoodsDTO, int rowStart, int maxRow, String sortType, String sortFieldList) {
         if (inventoryActionGoodsDTO != null ) {
            return inventoryActionGoodsBusiness.search(inventoryActionGoodsDTO, rowStart, maxRow, sortType, sortFieldList);
        }
        return null;
    }

    @Override
    public ResultDTO insertInventoryActionGoods(InventoryActionGoodsDTO inventoryActionGoodsDTO) {
        return inventoryActionGoodsBusiness.createObject(inventoryActionGoodsDTO);
    }
    @Override
    public String insertOrUpdateListInventoryActionGoods(List<InventoryActionGoodsDTO> inventoryActionGoodsDTO) {
         return inventoryActionGoodsBusiness.insertList(inventoryActionGoodsDTO);
    }

    @Override
    public List<String> getSequenseInventoryActionGoods(String seqName, int... size) {
        int number = ( size[0] > 0 ? size[0] : 1 );
       
        return inventoryActionGoodsBusiness.getListSequense(seqName, number);
    }
    
       @Override
    public List<InventoryActionGoodsDTO> getListInventoryActionGoodsByCondition(List<ConditionBean> lstCondition, int rowStart, int maxRow, String sortType, String sortFieldList) {
        List<InventoryActionGoodsDTO> lstInventoryActionGoods = new ArrayList<>();
        for (ConditionBean con : lstCondition) {
            if (con.getType().equalsIgnoreCase(ParamUtils.TYPE_DATE)) {
                con.setField(StringUtils.formatFunction("trunc", con.getField()));
            } else if (con.getType().equalsIgnoreCase(ParamUtils.NUMBER)) {
                con.setType(ParamUtils.TYPE_NUMBER);
            } else {
                String value = StringUtils.formatLike(con.getValue());
                value.toLowerCase();
                con.setValue(value);
                con.setField(StringUtils.formatFunction("lower",con.getField()));
            }
            con.setOperator(StringUtils.convertTypeOperator(con.getOperator()));

        }
        lstInventoryActionGoods = inventoryActionGoodsBusiness.searchByConditionBean(lstCondition, rowStart, maxRow, sortType, sortFieldList);
        return lstInventoryActionGoods;
    }

}
