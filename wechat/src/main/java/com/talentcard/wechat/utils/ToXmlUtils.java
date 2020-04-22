package com.talentcard.wechat.utils;

import com.thoughtworks.xstream.XStream;

/**
 * @author: jiangenyong
 * @date: Created in 2020-04-16 14:10
 * @description: TODO
 * @version: 1.0
 */
public class ToXmlUtils {

    private static final XStream xStream = XStreamFactory.getXStream();
    /**
     * 对象转xml
     * @param obj 对象
     * @return
     */
    public static String toXml(Object obj) {
        if(obj!=null){
            xStream.processAnnotations(obj.getClass());
            return xStream.toXML(obj);
        }else{
            return "";
        }

    }

}
