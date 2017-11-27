package com.zmc.utils.poi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.http.client.BufferingClientHttpRequestFactory;

public class WordFileDeal implements OfficeFileDealI{

	private String filePath;
	private File file;
	
	public WordFileDeal(String filePath) {
		super();
		this.filePath = filePath;
	}
	
	
	public WordFileDeal(File file) {
		super();
		this.file = file;
	}


	@Override
	public String getContentText() {

		try{
			if(null == file){
				
				file = new File(filePath);
			}
		if(!file.exists() || file.isDirectory()){
			return "";
		}
		if(file.getName().toUpperCase().endsWith(".DOC")){
			
			 
			InputStream in = new FileInputStream(file);  
			BufferedInputStream bis = new BufferedInputStream(in);
			WordExtractor wdoc = new WordExtractor(bis);  
			in.close();
			return wdoc.getText();
			
		}else if(file.getName().toUpperCase().endsWith(".DOCX")){
			
			//得到.docx文件提取器 
			XWPFWordExtractor docx = new XWPFWordExtractor(POIXMLDocument.openPackage(file.getAbsolutePath())); 
			return docx.getText(); 
		}}catch(Exception e){
			
			e.printStackTrace();
		}
		return "";
		
	}

}
