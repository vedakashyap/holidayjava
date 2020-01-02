package com.acc;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CLIENT, locale = "fr")
public class InvalidRequestDateException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidRequestDateException(String message) {
		super(message);
	}

}
