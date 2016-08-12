package com.devguerrilla.quickstart.cxf.countrylookup;
 
import com.viettel.logistic.cms.dto.AppParamsDTO;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
 
@WebService(targetNamespace = "http://pms.logistic.viettel.vn")
public interface CountryLookupService {
    @WebMethod(operationName = "getAppParamsName")
    @WebResult(name="parName", targetNamespace = "http://pms.logistic.viettel.vn")
    String getCountryName(@WebParam(name = "countryCode")String countryCode);
    @WebMethod(operationName = "getAppParam")
    @WebResult(name="appParamDTO", targetNamespace = "http://pms.logistic.viettel.vn")
    AppParamsDTO getAppParam(Long parId);
    @WebMethod(operationName = "getAll")
    @WebResult(name="listAppParam", targetNamespace = "http://pms.logistic.viettel.vn")
    public List<AppParamsDTO> getAll();
}