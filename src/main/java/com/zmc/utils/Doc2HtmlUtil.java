package com.zmc.utils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class Doc2HtmlUtil{
	
	 public static int office2PDF(String sourceFile, String destFile) {
	          
		 String OpenOffice_HOME = "C:/Program Files (x86)/OpenOffice 4/";
	     if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '/') {
	     
	    	 OpenOffice_HOME += "/";
	        
	     }
	        Process pro = null;

	        try {
	            File inputFile = new File(sourceFile);
	            if (!inputFile.exists()) {
	                return -1;// 找不到源文件, 则返回-1
	            }
	            // 如果目标路径不存在, 则新建该路径
	            File outputFile = new File(destFile);
	            if (!outputFile.getParentFile().exists()) {
	                outputFile.getParentFile().mkdirs();
	            }
	            // 启动OpenOffice的服务
	            String command = OpenOffice_HOME  
	                    + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;StarOffice.ServiceManager\" -nofirststartwizard"; 
	            pro = Runtime.getRuntime().exec(command);
	            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
	            connection.connect();

	            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
	            converter.convert(inputFile, outputFile);
	           // converter.convert(inputFile,xml,outputFile,pdf);
	            connection.disconnect();
	            // 封闭OpenOffice服务的进程
	            pro.destroy();

	            return 0;
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	            return -1;
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            pro.destroy();
	        }

	        return 1;
	    }
	 
	 public static void main(String[] args) {
		 
		// String sourceFile = "H:\\mydata\\fileup\\配置管理暂行办法.doc";
		 String sourceFile = "H:\\mydata\\fileup\\信息部--制度目录梳理2017.10.24.xls";
		 String destFile = "H:\\mydata\\fileup\\pdfdest.pdf";
		 int i =  Doc2HtmlUtil.office2PDF(sourceFile, destFile);
		 System.out.println(i);
	    }

}
