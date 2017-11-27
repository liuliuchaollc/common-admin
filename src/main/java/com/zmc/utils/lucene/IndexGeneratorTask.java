package com.zmc.utils.lucene;

import java.io.File;
import java.nio.file.Paths;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Test;

import com.zmc.utils.poi.ExcelFileDeal;
import com.zmc.utils.poi.PPTFileDeal;
import com.zmc.utils.poi.WordFileDeal;

public class IndexGeneratorTask implements Runnable{

	private String dataDir;
	private String fileName;
	private String indexDir;
	
	private Directory dir; // 存放索引的位置
	private Integer ids[] = { 1, 2, 3 }; // 用来标识文档
	private String citys[] = { "上海", "南京", "青岛" };
	private String descs[] = { "上海是个繁华的城市。", "南京是一个有文化的城市。", "青岛是一个美丽的城市。" };

	
	
	public IndexGeneratorTask() {
		super();
	}

	public IndexGeneratorTask(String dataDir, String fileName, String indexDir) {
		super();
		this.dataDir = dataDir;
		this.fileName = fileName;
		this.indexDir = indexDir;
	}

	// 生成索引
	@Test
	public void index(String indexDir) throws Exception {
		dir = FSDirectory.open(Paths.get(indexDir));
		IndexWriter writer = getWriter();
		for (int i = 0; i < ids.length; i++) {
			Document doc = new Document();
			doc.add(new IntField("id", ids[i], Store.YES));
			doc.add(new StringField("city", citys[i], Store.YES));
			doc.add(new TextField("desc", descs[i], Store.YES));
			writer.addDocument(doc); // 添加文档
		}
		writer.close(); // close了才真正写到文档中
	}

	// 获取IndexWriter实例
	private IndexWriter getWriter() throws Exception {
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();// 使用中文分词器
		IndexWriterConfig config = new IndexWriterConfig(analyzer); // 将标准分词器配到写索引的配置中
		IndexWriter writer = new IndexWriter(dir, config); // 实例化写索引对象
		return writer;
	}
	
	// 获取IndexWriter实例
	private IndexWriter getWriterForFile(String indexDir,boolean isCreate) throws Exception {
		java.nio.file.Path fi = Paths.get(indexDir);;
		Directory dir = new SimpleFSDirectory(fi);
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		IndexWriterConfig config = new IndexWriterConfig(analyzer); // 将标准分词器配到写索引的配置中
		if(isCreate){
			
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		}else{
			config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			
		}
		IndexWriter writer = new IndexWriter(dir, config); // 实例化写索引对象
		
		return writer;
	}

	// 生成索引为目录
		@Test
		public void indexForDir(String dataDir,String indexDir) throws Exception {

			 File[] files = new File(dataDir).listFiles();  
			 //写入IndexWriter  
				IndexWriter iw = getWriterForFile(indexDir,true) ;
		        for (int i = 0; i < files.length; i++) {  
		            Document doc = new Document();  
		            String text = "";
		          //得到.doc文件提取器
					if(files[i].getName().toUpperCase().endsWith(".DOC") || files[i].getName().toUpperCase().endsWith(".DOCX")){
						
						WordFileDeal wfd = new WordFileDeal(files[i]);
						text = wfd.getContentText();
						
					}else if(files[i].getName().toUpperCase().endsWith(".XLS") || files[i].getName().toUpperCase().endsWith(".XLSX")){
						 
						ExcelFileDeal efd = new ExcelFileDeal(files[i]);
						text = efd.getContentText();
						
					}else if(files[i].getName().toUpperCase().endsWith(".PPT") || files[i].getName().toUpperCase().endsWith(".PPTX")){
						PPTFileDeal pptfd = new PPTFileDeal(files[i]);
						text = pptfd.getContentText();
					}else{
						continue;
					}
		            //创建Field对象，并放入doc对象中  
					doc.add(new TextField("contents",text,Field.Store.YES));
					System.out.println(text);
					doc.add(new TextField("filename",files[i].getName(),Field.Store.YES));
		           
					iw.addDocument(doc);
					iw.commit();
		        }
		        iw.close();
		}
		
		// 生成索引为文件
				@Test
				public void indexForFile(String dataDir,String fileName,String indexDir) throws Exception {

					String filePath = "";
					if(dataDir == null|| fileName == null || dataDir.isEmpty() || fileName.isEmpty()){
						return ;
					}
					if(dataDir.endsWith("/")){
						filePath = dataDir + fileName;
					}else{
						filePath = dataDir + "/" + fileName;
					}
					File fileP = new File(filePath);
					 //写入IndexWriter  
							IndexWriter iw = getWriterForFile(indexDir,false) ;
				            Document doc = new Document();  
				            String text = "";
				          //得到.doc文件提取器
							if(fileP.getName().toUpperCase().endsWith(".DOC") || fileP.getName().toUpperCase().endsWith(".DOCX")){
								
								WordFileDeal wfd = new WordFileDeal(fileP);
								text = wfd.getContentText();
								
							}else if(fileP.getName().toUpperCase().endsWith(".XLS") || fileP.getName().toUpperCase().endsWith(".XLSX")){
								 
								ExcelFileDeal efd = new ExcelFileDeal(fileP);
								text = efd.getContentText();
								
							}else if(fileP.getName().toUpperCase().endsWith(".PPT") || fileP.getName().toUpperCase().endsWith(".PPTX")){
								PPTFileDeal pptfd = new PPTFileDeal(fileP);
								text = pptfd.getContentText();
							} 
							
				            //创建Field对象，并放入doc对象中  
							doc.add(new TextField("contents",text,Field.Store.YES));
							System.out.println(">>>>>>>>>文件内容："+text);
							doc.add(new TextField("filename",fileP.getName(),Field.Store.YES));
							iw.addDocument(doc);
							iw.commit();
							iw.close();
				}
	public static void main(String[] args) throws Exception {
		
		//new IndexGenerator().index(ConstantLucene.indexPath);
		
		/*File[] files = new File(ConstantLucene.DataDirPath).listFiles();  
        for (int i = 0; i < files.length; i++) {  
        	System.out.println(files[i].getName());
        }*/
		
		IndexGeneratorTask ig = new IndexGeneratorTask();
		ig.indexForDir(ConstantLucene.DataDirPath,ConstantLucene.indexPath);
	}

	@Override
	public void run() {

			try {
				this.indexForFile(this.dataDir, this.fileName, this.indexDir);
				
			} catch (Exception e) {

				e.printStackTrace();
			}
		
		
	}
}