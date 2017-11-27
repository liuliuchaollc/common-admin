package com.zmc.web.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zmc.common.entity.Response;
import com.zmc.common.entity.User;
import com.zmc.common.entity.YcResource;
import com.zmc.service.YcResourceService;
import com.zmc.utils.lucene.ConstantLucene;
import com.zmc.utils.lucene.IndexGeneratorTask;
import com.zmc.web.bind.annotation.CurrentUser;

@Controller
@RequestMapping("ycresource")
public class YcResourceController {
    @Autowired
    private YcResourceService ycResourceService;

    /**
     * ycResourceView-view
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/ycresource-view.html",method = RequestMethod.GET)
    public String ycResourceView(Model model) throws Exception {
        List<YcResource> roles = ycResourceService.findAllYcResources();
        model.addAttribute("ycresources",roles);
        return "ycresource/ycresource_view";
    }

    @RequestMapping(value = "/fileupload",method = RequestMethod.POST)
    @ResponseBody
    public  Response upload(@RequestParam(value = "files", required = false) MultipartFile files) {  
  
    	Response response = new Response();
            File f = new File(ConstantLucene.DataDirPath+files.getOriginalFilename());  
            try {  
                FileUtils.copyInputStreamToFile(files.getInputStream(),f );  
                
                ExecutorService executorService = Executors.newCachedThreadPool(); 
                IndexGeneratorTask igt = new IndexGeneratorTask(ConstantLucene.DataDirPath,f.getName(),ConstantLucene.indexPath);
                executorService.submit(igt);
                
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
               
            return response.success();
  
    }  
    
    @RequestMapping(value = "/{ycresourceid}/downloadfile2",method = RequestMethod.GET)
    @ResponseBody
    public  ResponseEntity<byte[]> downloadFile2(@PathVariable String ycresourceid) {  
  
    	ResponseEntity<byte[]> re = null;
        try{
	    	YcResource ycr = ycResourceService.findYcResourceById(Long.parseLong(ycresourceid));
	    	if(null == ycr || ycr.getName() == null || ycr.getName().isEmpty()){
	    	
	    		return null;
	    	}
	    	String name = ycr.getName();
	        File file = new File("H:/mydata/fileup/"+name);
	        if(!file.exists()){
	        
	        	return null;
	        }
        
	        HttpHeaders headers=new HttpHeaders();
	            	
	        //String filename=URLEncoder.encode(name, "UTF-8");//为了解决中文名称乱码问题 
	            	
	        String filename=new String(name.getBytes("utf-8"),"utf-8");
	            	
	        byte[] by=FileUtils.readFileToByteArray(file);
	            	
	        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
	            	
	        //URLEncoder.encode(filename, "UTF-8")
	            	
	        headers.setContentDispositionFormData("attachment",filename);
	            	
	        headers.setContentLength(by.length);
	        
	        re=new ResponseEntity<byte[]>(by, headers, HttpStatus.CREATED);
            	
        } catch (Exception e) {
        	
        	e.printStackTrace();
        	return re;
        }
		return re;
    }  
    
    @RequestMapping(value = "/{ycresourceid}/downloadfile",method = RequestMethod.GET)
    @ResponseBody
    public  Response downloadFile(@PathVariable String ycresourceid, HttpServletRequest request, HttpServletResponse response) {  
  
        try{
	    	YcResource ycr = ycResourceService.findYcResourceById(Long.parseLong(ycresourceid));
	    	if(null == ycr || ycr.getName() == null || ycr.getName().isEmpty()){
	    	
	    		return null;
	    	}
	    	String name = ycr.getName();
	        File file = new File("H:/mydata/fileup/"+name);
	        if(!file.exists()){
	        
	        	return null;
	        }
	        if (file.exists()) {
                response.setContentType("application/force-download");// 设置强制下载不打开
    	        String filename=new String(name.getBytes("GBK"),"iso-8859-1");
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
    
    /**
     * ycResource-create
     * @param role
     * @param user
     * @return
     */
    @RequestMapping(value = "/ycresource-create",method = RequestMethod.POST)
    @ResponseBody
    public Response ycResourceAdd(YcResource role, @CurrentUser User user){
        Response response = new Response();
        if (StringUtils.isEmpty(role.getName())){
            return  response.failure("角色名称不能为空");
        }
        try {
            role.setCreate_by(user.getUsername());
            Date time = new Date();
            role.setCreate_time(time);
            role.setUpdate_time(time);
            role.setUpdate_by(user.getUsername());
            ycResourceService.addYcResource(role);
            return response.success(role);
        } catch (Exception e) {
            e.printStackTrace();
            return response.failure("添加失败");
        }

    }

    /**
     * YcResource-delete
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/delete",method = RequestMethod.GET)
    @ResponseBody
    public Response ycResourceDelete(@PathVariable String id){
        Response response = new Response();
        Long ycResourceId;
        try {
        	ycResourceId = Long.valueOf(id);
        }catch (NumberFormatException e){
            e.printStackTrace();
            return response.failure("无效的ID");
        }

        try {
            Boolean result = ycResourceService.deleteYcResourceById(ycResourceId);
            if (result){
                return response.success();
            }else {
                return response.failure("失败失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return response.failure("删除失败");
        }

    }

    @RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
    @ResponseBody
    public Response ycResourceUpdate(@PathVariable String id,YcResource role){
        Response response = new Response();
        if (StringUtils.isEmpty(role.getName())){
            return response.failure("角色名称不能为空");
        }

        try {
            role.setUpdate_time(new Date());
            role.setUpdate_by((String) SecurityUtils.getSubject().getPrincipal());
            Boolean result = ycResourceService.updateYcResource(role);
            if (result){
                return response.success(role);
            }else {
                return response.failure("更新失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return response.failure("更新异常");
        }

    }
}
