package com.zmc.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zmc.common.entity.Response;
import com.zmc.common.entity.YcPageEntity;
import com.zmc.common.entity.YcResource;
import com.zmc.common.entity.YcSearchEntity;
import com.zmc.service.YcSearchService;
import com.zmc.utils.lucene.ConstantLucene;

@Controller
@RequestMapping("ycsearch")
public class YcSearchController {
    @Autowired
    private YcSearchService ycSearch;

    /**
     * ycResourceView-view
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ycsearch-view.html",method = RequestMethod.GET)
    public String ycSearchView(Model model) throws Exception {

        return "ycsearch/ycsearch_view";
    }

    @RequestMapping(value = "/{pageSize}/{pageIndex}/ycsearch",method = RequestMethod.POST)
    @ResponseBody
    public  Response ycSearchByPage(@RequestParam(value = "keys", required = true) String keys,
    		@PathVariable int pageIndex ,
    		@PathVariable int pageSize ) {  
  
    	Response response = new Response();
    	try {
    		YcPageEntity ype = ycSearch.searchPageNum(ConstantLucene.indexPath,keys,pageIndex,pageSize);
    		if(ype == null){
    			return response.failure();
    		}
    		return response.success(ype);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return response.failure();
    }  
    
    @RequestMapping(value = "/ycsearch",method = RequestMethod.POST)
    @ResponseBody
    public  Response ycSearch(@RequestParam(value = "keys", required = true) String keys ) {  
  
    	Response response = new Response();
    	try {
    		List<YcSearchEntity> list = ycSearch.searchByKey(ConstantLucene.indexPath,keys);
    		if(list == null || list.isEmpty()){
    			return response.failure();
    		}
    		YcPageEntity ype = new YcPageEntity();
    		ype.setPageSize(ConstantLucene.sysPageCount);
    		ype.setAllSize(list.size());
    		ype.setPagecount(list.size() % ConstantLucene.sysPageCount > 0 ? list.size() % ConstantLucene.sysPageCount:list.size() % ConstantLucene.sysPageCount + 1 );
    		ype.setList(list);
    		return response.success(ype);
    		
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return response.failure();
    }  
    
    
    @RequestMapping(value = "/{fileName}/downloadfile",method = RequestMethod.GET)
    @ResponseBody
    public  Response downloadFile(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) {  
  
        try{
        	
        	if(fileName == null || fileName.isEmpty()){
        		
        		return null;
        	}
	    	String name = java.net.URLDecoder.decode(fileName,"UTF-8");
	        File file = new File(ConstantLucene.DataDirPath+name);
	        if(!file.exists()){
	        
	        	return null;
	        }
	        if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
    	        String filename=new String(name.getBytes("GBK"),"iso-8859-1");
    	      //  String filename=file.getName();//new String(name.getBytes("GBK"),"iso-8859-1");
    	        //response.setHeader("Content-Disposition", arg1);
                response.addHeader("Content-Disposition",
                        "attachment;fileName=" + filename);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }catch(Exception e){
        	
        	e.printStackTrace();
        }
        return null;
    }
}
