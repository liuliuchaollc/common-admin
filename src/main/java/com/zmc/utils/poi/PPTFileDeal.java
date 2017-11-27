package com.zmc.utils.poi;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hslf.HSLFSlideShow;
import org.apache.poi.hslf.extractor.PowerPointExtractor;
import org.apache.poi.hslf.model.Slide;
import org.apache.poi.hslf.model.TextRun;
import org.apache.poi.hslf.usermodel.SlideShow;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.xslf.extractor.XSLFPowerPointExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;
public class PPTFileDeal implements OfficeFileDealI {

	private String filePath;
	private File file;
	
	public PPTFileDeal(String filePath) {
		super();
		this.filePath = filePath;
	}
	
	
	public PPTFileDeal(File file) {
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
		if(file.getName().toUpperCase().endsWith(".PPT")){
			
			return getTextFromPPT(file.getAbsolutePath());
			
		}else if(file.getName().toUpperCase().endsWith(".PPTX")){

			return getTextFromPPTx(file.getAbsolutePath());
		}}catch(Exception e){
			
			e.printStackTrace();
		}
		return "";
		
	}
	
	 /**
     * @param args
     */
    public String getTextFromPPT(String path) {

        StringBuffer content = new StringBuffer("");
        try {

            SlideShow ss = new SlideShow(new HSLFSlideShow(path));// path为文件的全路径名称，建立SlideShow
            Slide[] slides = ss.getSlides();// 获得每一张幻灯片
            for (int i = 0; i < slides.length; i++) {
                TextRun[] t = slides[i].getTextRuns();// 为了取得幻灯片的文字内容，建立TextRun
                for (int j = 0; j < t.length; j++) {
                    content.append(t[j].getText());// 这里会将文字内容加到content中去
                }
                content.append(slides[i].getTitle());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return content.toString();

    }

    public String getTextFromPPTx(String path) {
    	
        try {
        	
			return new XSLFPowerPointExtractor(POIXMLDocument.openPackage(path)).getText();
		
        } catch (XmlException e) {
			e.printStackTrace();
		} catch (OpenXML4JException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
    }

}
