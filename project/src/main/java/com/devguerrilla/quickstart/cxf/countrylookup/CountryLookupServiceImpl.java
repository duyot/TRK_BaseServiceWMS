package com.devguerrilla.quickstart.cxf.countrylookup;

import com.viettel.logistic.cms.dto.AppParamsDTO;
import com.viettel.logistic.cms.model.AppParams;
import com.viettel.vfw5.base.service.BaseFWServiceInterface;
import java.util.List;
import javax.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;

@WebService(endpointInterface = "com.devguerrilla.quickstart.cxf.countrylookup.CountryLookupService")
public class CountryLookupServiceImpl implements CountryLookupService {

    @Autowired
    BaseFWServiceInterface appParamsBusiness;

    @Override
    public String getCountryName(String countryCode) {
        AppParams appParams =  (AppParams)appParamsBusiness.findById(1L);
        return appParams.getParName();
    }

    @Override
    public AppParamsDTO getAppParam(Long parId) {
        if (parId != null && parId > 0) {
            return (AppParamsDTO)(appParamsBusiness.findById(parId)).toDTO();
        }
        return null;
    }

    @Override
    public List<AppParamsDTO> getAll() {
         return appParamsBusiness.getAll();
    }
}
