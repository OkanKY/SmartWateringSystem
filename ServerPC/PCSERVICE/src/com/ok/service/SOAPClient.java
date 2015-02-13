package com.ok.service;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


public class SOAPClient {
	 public static void main(String args[]) throws Exception {
	        // Create SOAP Connection
	        SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
	        SOAPConnection soapConnection = soapConnectionFactory.createConnection();

	        // Send SOAP Message to SOAP Server
	        String url = "http://192.168.2.42:8500/ArabaWS";
	        SOAPMessage soapResponse = soapConnection.call(createSOAPRequest(), url);
	        System.out.println(" ne "+soapResponse.getSOAPBody().getTextContent());
	        // print SOAP Response
	        System.out.print("Response SOAP Message:");
	        soapResponse.writeTo(System.out);

	        soapConnection.close();
	    }

	    private static SOAPMessage createSOAPRequest() throws Exception {
	        MessageFactory messageFactory = MessageFactory.newInstance();
	        SOAPMessage soapMessage = messageFactory.createMessage();
	        SOAPPart soapPart = soapMessage.getSOAPPart();

	        String serverURI = "http://farm.ok.com/";

	        // SOAP Envelope
	        SOAPEnvelope envelope = soapPart.getEnvelope();
	        envelope.addNamespaceDeclaration("example", serverURI);

	        /*
	        - <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:q0="http://farm.ok.com/" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"> 
                - <soapenv:Body> 
                  - <q0:getUserList> 
                     <UserName>x</UserName> 
                     <Password>1</Password> 
                    </q0:getUserList> 
                 </soapenv:Body> 
              </soapenv:Envelope>

	         */

	        // SOAP Body
	        SOAPBody soapBody = envelope.getBody();
	        SOAPElement soapBodyElem = soapBody.addChildElement("getUserList", "example");
	        SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("UserName");
	        soapBodyElem1.addTextNode("x");
	        SOAPElement soapBodyElem2 = soapBodyElem.addChildElement("Password");
	        soapBodyElem2.addTextNode("1");

	        MimeHeaders headers = soapMessage.getMimeHeaders();
	        headers.addHeader("SOAPAction", serverURI  + "getUserList");

	        soapMessage.saveChanges();

	        /* Print the request message */
	        System.out.print("Request SOAP Message:");
	        soapMessage.writeTo(System.out);
	        System.out.println();

	        return soapMessage;
	    }

	}