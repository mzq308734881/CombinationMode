package pack;

import java.io.ByteArrayInputStream;  
import java.io.File;  
import java.io.InputStream;  
import javax.xml.transform.Source;  
import javax.xml.transform.stream.StreamSource;  
import javax.xml.validation.Schema;  
import javax.xml.validation.SchemaFactory;  
import javax.xml.validation.Validator;  
  

//import org.apache.log4j.Logger;  
import org.xml.sax.SAXException; 

import com.sun.istack.internal.logging.Logger;

public class XMLSchema {
	 
    private static final Logger logger = Logger.getLogger(XMLSchema.class);  
  
    private static final String SCHEMALANG = "http://www.w3.org/2001/XMLSchema";  
	/** 
	 * 
     * SchemaУ��xml�ļ� 
     * @param xmlPath xml�ַ��� 
     * @param xsdPath xsd�ļ�·�� 
     * @return xml�ļ��Ƿ����xsd����Ĺ��� 
     * 
     */  
    public static boolean xmlStringValidate(String xmlStr, String xsdPath) {  
        boolean flag = false;  
        try {  
            SchemaFactory factory = SchemaFactory.newInstance(SCHEMALANG);  
            File schemaLocation = new File(xsdPath);  
            Schema schema = factory.newSchema(schemaLocation);  
            Validator validator = schema.newValidator();  
            InputStream is = new ByteArrayInputStream(xmlStr.getBytes());  
            Source source = new StreamSource(is);  
            try {  
                validator.validate(source);  
                flag = true;  
            } catch (SAXException ex) {  
                logger.info(ex.getMessage());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return flag;  
    }  
      
    /**
     *  
     * SchemaУ��xml�ļ� 
     * @param xmlPath xml�ļ�·�� 
     * @param xsdPath xsd�ļ�·�� 
     * @return xml�ļ��Ƿ����xsd����Ĺ��� 
     * 
     */  
    public static boolean xmlFileValidate(String xmlPath, String xsdPath) {  
        boolean flag = false;  
        try {  
            SchemaFactory factory = SchemaFactory.newInstance(SCHEMALANG);  
            File schemaLocation = new File(xsdPath);  
            Schema schema = factory.newSchema(schemaLocation);  
            Validator validator = schema.newValidator();  
            Source source = new StreamSource(xmlPath);    
            try {  
                validator.validate(source);  
                flag = true;  
            } catch (SAXException ex) {  
                logger.info(ex.getMessage());  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return flag;  
    } 
    	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.printf("xml�Ƿ����xsd�淶��"+xmlFileValidate("F:\\Eclipse IDE for Java EE Developers\\���ı�XML��д\\bin\\pack\\Test1.xml","F:\\Eclipse IDE for Java EE Developers\\���ı�XML��д\\bin\\pack\\xsdFile.xsd"));
		
		System.out.printf("xml�Ƿ����xsd�淶��"+xmlStringValidate("<root><person><name>mzq</name><age>25</age></person><person><name>xml</name><age>56</age></person></root>","F:\\Eclipse IDE for Java EE Developers\\���ı�XML��д\\bin\\pack\\xsdFile.xsd"));		
	}
}
