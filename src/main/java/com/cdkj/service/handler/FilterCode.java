/**
 * @Title FilterCode.java 
 * @Package com.cdkj.service.handler 
 * @Description 
 * @author xieyj  
 * @date 2016年12月14日 下午1:14:50 
 * @version V1.0   
 */
package com.cdkj.service.handler;

import java.io.File;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/** 
 * @author: xieyj 
 * @since: 2016年12月14日 下午1:14:50 
 * @history:
 */
public class FilterCode {

    public boolean isVisitNeedToken(String functionCode) {
        boolean result = false;
        SAXReader reader = new SAXReader();
        Document document;
        try {
            File file = new File("function_code.xml");
            document = reader.read(file);
            Element root = document.getRootElement();
            Iterator iter = root.elementIterator("code");

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        boolean result = false;
        SAXReader reader = new SAXReader();
        Document document;
        try {
            File file = new File("function_code.xml");
            document = reader.read(file);
            Element root = document.getRootElement();
            Iterator iter = root.elementIterator("code");

        } catch (DocumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
