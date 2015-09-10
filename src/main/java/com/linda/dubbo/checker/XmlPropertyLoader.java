package com.linda.dubbo.checker;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public class XmlPropertyLoader {
	
	private static Logger logger = Logger.getLogger(XmlPropertyLoader.class);
	
	@SuppressWarnings("resource")
	public static Properties loadXML(final String file,final String replaceVersionKey,final String replaceVersionValue){
		try{
			XMLReader reader = XMLReaderFactory.createXMLReader();
			FileInputStream fis = new FileInputStream(new File(file));
			logger.info("loaded xml:"+file+" replace:"+replaceVersionKey+" replaceVersion:"+replaceVersionValue);
			InputSource inputSource = new InputSource(fis);
			final Properties properties = new Properties();
			reader.setContentHandler(new ContentHandler() {
				@Override
				public void startPrefixMapping(String prefix, String uri) throws SAXException {}
				
				@Override
				public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
					if(qName.equals("dubbo:service")){
						int interfIndex = atts.getIndex("interface");
						String service = null;
						String version = null;
						if(interfIndex>=0){
							service = atts.getValue(interfIndex);
							int versionIndex = atts.getIndex("version");
							if(versionIndex>=0){
								version = atts.getValue(versionIndex);
							}
							if(service!=null&&version!=null){
								if(replaceVersionKey!=null){
									if(replaceVersionValue!=null){
										version = version.replace(replaceVersionKey, replaceVersionValue);
									}else{
										version = version.replace(replaceVersionKey, "");
									}
								}
								properties.put(service, version);
							}
						}
					}
				}
				
				@Override
				public void startDocument() throws SAXException {}
				
				@Override
				public void skippedEntity(String name) throws SAXException {}
				
				@Override
				public void setDocumentLocator(Locator locator) {}
				
				@Override
				public void processingInstruction(String target, String data) throws SAXException {}
				
				@Override
				public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {}
				
				@Override
				public void endPrefixMapping(String prefix) throws SAXException {}
				
				@Override
				public void endElement(String uri, String localName, String qName) throws SAXException {}
				
				@Override
				public void endDocument() throws SAXException {}
				
				@Override
				public void characters(char[] ch, int start, int length) throws SAXException {}
			});
			reader.setErrorHandler(new ErrorHandler() {
				
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					logger.error("warning parse xml error "+file,exception);
				}
				
				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					logger.error("fatalError parse xml error "+file,exception);
				}
				
				@Override
				public void error(SAXParseException exception) throws SAXException {
					logger.error("error parse xml error "+file,exception);
				}
			});
			reader.parse(inputSource);
			fis.close();
			logger.info("load xml services and versions:"+JSONUtils.toJSON(properties));
			return properties;
		}catch(Exception e){
			logger.error("load xml "+file+" "+e.getMessage(),e);
		}
		return null;
	}
	
	public static void main(String[] args) {
		String file = "D:/vbiz-dubbo-provider.xml";
		Properties properties = XmlPropertyLoader.loadXML(file,"${dubbo.version.suffix}","_stable");
		logger.info("result:"+JSONUtils.toJSON(properties));
	}

}
