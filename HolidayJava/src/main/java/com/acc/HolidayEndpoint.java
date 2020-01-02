package com.acc;

import java.util.Date;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.acc.domain2.HolidayRequest;
import com.acc.domain2.HolidayResponse;

@Endpoint
public class HolidayEndpoint {
	
	
	private HumanResourceService humanResourceService;

	@Autowired
	public HolidayEndpoint(HumanResourceService humanResourceService) {
		this.humanResourceService = humanResourceService;
	}

    @PayloadRoot(namespace="http://mycompany.com/hr/schemas",localPart="HolidayRequest")
	@ResponsePayload
	public HolidayResponse handleHolidayRequest(@RequestPayload HolidayRequest holidayRequest) throws Exception{
		
    	XMLGregorianCalendar inputdate = holidayRequest.getHoliday().getStartDate();
    	if(inputdate == null || !inputdate.isValid() || inputdate.toString().isEmpty()) {
    		throw new InvalidRequestDateException("start Date is Invalid");
    	}
    	inputdate = holidayRequest.getHoliday().getEndDate();
    	if(inputdate == null || !inputdate.isValid() || inputdate.toString().isEmpty()) {
    		throw new InvalidRequestDateException("end Date is Invalid");
    	}
		Date startdate = holidayRequest.getHoliday().getStartDate().toGregorianCalendar().getTime();
		Date enddate = holidayRequest.getHoliday().getEndDate().toGregorianCalendar().getTime();
		HolidayResponse response = new HolidayResponse();
		if(enddate.before(startdate)) {
			response.setHolidayStatus("Rejected");
			
		}
		else {
		
		humanResourceService.bookholiday(startdate,enddate,holidayRequest.getEmployee().getFirstName());
		response.setHolidayStatus("Approved");
		}
		XMLGregorianCalendar today = DatatypeFactory.newInstance().newXMLGregorianCalendar("2019-02-22");
		response.setStatusDate(today);
		
		return response;
	}

}

