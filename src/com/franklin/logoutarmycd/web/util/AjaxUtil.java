package com.franklin.logoutarmycd.web.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.franklin.logoutarmycd.util.CloseStreamUtils;

public class AjaxUtil {
	
	static private void responseDataToClient(HttpServletResponse response, String contentType, String content){
		response.setContentType(contentType);          
		response.setHeader("Cache-Control", "no-cache");
	
		PrintWriter out = null;
	    try{
		    out = response.getWriter();
		    out.print(content);
	    } catch( IOException e){
	    	throw new RuntimeException(e);
	    } finally {
	    	CloseStreamUtils.closeWriter(out);
	    }
	}
	
	/**
	 * Response the XML data to the client.
	 * 
	 * @param response
	 * @param xml
	 */
	static public void responseXmlData(HttpServletResponse response, String xml){
		responseDataToClient(response, "text/xml; charset=UTF-8", xml);
	}
	
	/**
	 * Response the html data to the client.
	 * 
	 * @param response
	 * @param html
	 */
	static public void responseHTMLData(HttpServletResponse response, String html){          
		responseDataToClient(response, "text/html; charset=UTF-8", html);
	}
}
