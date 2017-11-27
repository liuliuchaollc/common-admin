package com.zmc.utils.freemarker;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class CreateWord {

	public void createDoc() throws Exception {
		
		System.out.println("正在生成word,请稍后……");
		/* 在整个应用的生命周期中，这个工作你应该只做一次。 */
		/* 创建和调整配置。 */
		Configuration cfg = new Configuration();
		cfg.setClassForTemplateLoading(getClass(), "/com/zmc/freemarker");
		/* 在整个应用的生命周期中，这个工作你可以执行多次 */
		/* 获取或创建模板 */
		Template temp = cfg.getTemplate("template.ftl");
		// 读取图片
		Base64 b64Encoder = new Base64();
		File file = new File("D:\\wordtest\\tx.jpg");
		FileInputStream fis = new FileInputStream(file);
		byte[] imgData = new byte[fis.available()];
		fis.read(imgData);
		fis.close();
		String imgDataStr = b64Encoder.encodeAsString(imgData);
		
		/* 创建数据模型 */
		//此处便是内容填充（内容可由数据库读取）
		Map<String, Object> root = new HashMap<String, Object>();
		root.put("imgData", imgDataStr);
		root.put("模板名称", "tablemiaoTest");
		root.put("模板编号", "编号89757");
		root.put("模板风格", "简简单单的我,简简单单的活");
		root.put("模板联系人", "tablemiao");
		root.put("模板联系电话", 123456);
		root.put("模板创建时间", new SimpleDateFormat("yyyy年MM月dd日").format(new Date()));
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 1);
		root.put("模板完成时间", new SimpleDateFormat("yyyy年MM月dd日").format(c.getTime()));
		root.put("模板历时", "1天");
		root.put("模伴简述", "测试功能模板，以实际需求为准！");
		
		//读取第二张图片  多张图片依次处理
		File fileTwo = new File("D:\\wordtest\\bz.jpg");
		FileInputStream fisTwo = new FileInputStream(fileTwo);
		byte[] imgDataTwo = new byte[fisTwo.available()];
		fisTwo.read(imgDataTwo);
		fisTwo.close();
	    String imgDataStrTwo = b64Encoder.encodeAsString(imgDataTwo);
		root.put("desc",imgDataStrTwo.trim());
		
		/* 将模板和数据模型合并 */
		// 输出文档路径及名称
		File outFile = new File("D:/wordtest/tx.doc");
		Writer out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		temp.process(root, out);
		out.flush();
		out.close();
		
		System.out.println("word创建完成!");
	}
	
	 public static void main(String[] args) throws Exception {
		
		 CreateWord cw = new CreateWord();
		 cw.createDoc();
	}
}
