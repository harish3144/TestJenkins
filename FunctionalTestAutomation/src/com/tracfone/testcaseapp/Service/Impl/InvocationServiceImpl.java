package com.tracfone.testcaseapp.Service.Impl;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPConstants;
import javax.xml.soap.SOAPException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;

import com.tracfone.testcaseapp.DAO.ServiceDAO;
import com.tracfone.testcaseapp.DAO.Impl.ServiceDAOImpl;
import com.tracfone.testcaseapp.Service.InvocationService;

public class InvocationServiceImpl implements InvocationService {

	public String invokeService(String env, int service, String endPoint, String request) {
		StreamSource source = new StreamSource(new StringReader(request));
		StreamResult result = new StreamResult(new StringWriter());
		ServiceDAO serviceDAO = new ServiceDAOImpl();
		String serverContext = serviceDAO.getEnvContext(env, service);
		System.out.println("URL >>>>" + serverContext + endPoint);
		// try {
		// final MessageFactory msgFactory =
		// MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL);
		// final WebServiceMessageFactory newSoapMessageFactory = new
		// SaajSoapMessageFactory(msgFactory);
		WebServiceTemplate serviceTemplate = new WebServiceTemplate();// newSoapMessageFactory);
		serviceTemplate.setDefaultUri(serverContext + endPoint);
		serviceTemplate.sendSourceAndReceiveToResult(source, result);
		// } catch (SOAPException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		return result.getWriter().toString();
	}

}
