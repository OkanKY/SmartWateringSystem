package com.ok.farm.client;

import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

import com.ok.attiribute.Information;

public class BindingPcClient {
   private String url =null;
   private static String METHOD_NAME = null;
   private static String serverURI = "http://service.ok.com/";
   public BindingPcClient(String url,String METHOD_NAME)
   {
	   this.url=url;
	   this.METHOD_NAME=METHOD_NAME;
   }
	public SOAPMessage ManagerBinding() throws Exception
   {
		
		  // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

        // Send SOAP Message to SOAP Server
       // String url = "http://192.168.2.42:8500/ArabaWS";
        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);

        System.out.print("Response SOAP Message:");
        soapResponse.writeTo(System.out);

        soapConnection.close(); 
        return soapResponse;
   }
	 private static SOAPMessage createSOAPRequest() throws Exception {
	        MessageFactory messageFactory = MessageFactory.newInstance();
	        SOAPMessage soapMessage = messageFactory.createMessage();
	        SOAPPart soapPart = soapMessage.getSOAPPart();
	        // SOAP Envelope
	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration("example", serverURI);

	        /* - <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:q0="http://service.ok.com/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
                 - <soapenv:Body>
                     <q0:getInformation  /> 
                   </soapenv:Body>
                 </soapenv:Envelope>
	          */

	        // SOAP Body
	        SOAPBody soapBody = envelope.getBody();
	        SOAPElement soapBodyElem = soapBody.addChildElement(METHOD_NAME, "example");

	        MimeHeaders headers = soapMessage.getMimeHeaders();
	        headers.addHeader("SOAPAction", serverURI  + METHOD_NAME);

	        soapMessage.saveChanges();

	        /* Print the request message */
	        System.out.print("Request SOAP Message:");
	        soapMessage.writeTo(System.out);
	        System.out.println();

	        return soapMessage;
	    }
}
