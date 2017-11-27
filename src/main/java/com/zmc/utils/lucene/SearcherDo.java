package com.zmc.utils.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import com.zmc.common.entity.YcSearchEntity;

public class SearcherDo {

	public static void search(String indexDir, String q) throws Exception {

		Directory dir = FSDirectory.open(Paths.get(indexDir)); // 获取要查询的路径，也就是索引所在的位置
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(); // 使用中文分词器
		QueryParser parser = new QueryParser("desc", analyzer); // 查询解析器
		Query query = parser.parse(q); // 通过解析要查询的String，获取查询对象

		long startTime = System.currentTimeMillis(); // 记录索引开始时间
		TopDocs docs = searcher.search(query, 10);// 开始查询，查询前10条数据，将记录保存在docs中
		long endTime = System.currentTimeMillis(); // 记录索引结束时间
		System.out.println("匹配" + q + "共耗时" + (endTime - startTime) + "毫秒");
		System.out.println("查询到" + docs.totalHits + "条记录");

		for (ScoreDoc scoreDoc : docs.scoreDocs) { // 取出每条查询结果
			Document doc = searcher.doc(scoreDoc.doc); // scoreDoc.doc相当于docID,根据这个docID来获取文档
			System.out.println(doc.get("city"));
			System.out.println(doc.get("desc"));
			String desc = doc.get("desc");
		}
		reader.close();
	}


	/**
	 * 特定分析器进行检索
	 * @param indexDir
	 * @param q
	 * @throws Exception
	 */
	public static void searchForFile(String indexDir, String q) throws Exception {

		Directory dir = FSDirectory.open(Paths.get(indexDir)); // 获取要查询的路径，也就是索引所在的位置
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(); // 使用中文分词器
		QueryParser parser = new QueryParser("contents", analyzer); // 查询解析器
		Query query = parser.parse(q); // 通过解析要查询的String，获取查询对象
		
		
		
		long startTime = System.currentTimeMillis(); // 记录索引开始时间
		TopDocs docs = searcher.search(query, 100);// 开始查询，查询前10条数据，将记录保存在docs中
		
		long endTime = System.currentTimeMillis(); // 记录索引结束时间
		System.out.println("匹配" + q + "共耗时" + (endTime - startTime) + "毫秒");
		System.out.println("查询到" + docs.totalHits + "条记录");
		TokenStream tokenStream=null;  
		for (ScoreDoc scoreDoc : docs.scoreDocs) { // 取出每条查询结果
			Document doc = searcher.doc(scoreDoc.doc); // scoreDoc.doc相当于docID,根据这个docID来获取文档
			//System.out.println(doc.get("filename"));
			
			 String title=doc.get("filename");  
             String mark=doc.get("contents");  
             //加亮处理  
             SimpleHTMLFormatter simplehtml=new SimpleHTMLFormatter("<font color='red'>", "</font>");  
             Highlighter highlighter = new Highlighter(simplehtml,new QueryScorer(query));    
            /* if(title!=null){  
                     tokenStream = analyzer.tokenStream("filename",new StringReader(title));      
                     String highLightText = highlighter.getBestFragment(tokenStream, title);  
                    System.out.println(highLightText); 
             }else{  
             }  */
             //加亮处理  
             if(mark!=null){  
                 tokenStream = analyzer.tokenStream("contents",new StringReader(mark));      
                 String[] highLightText = highlighter.getBestFragments(tokenStream, mark, 10);//.getBestFragment(tokenStream, mark); 
                 for(String one:highLightText){
                	 
                	  
                 }
             }else{  
            	 System.out.println(mark+".................");
             }  
		}
		reader.close();
	}
	public  List<YcSearchEntity> getTopDocs(TopDocs td){
		
		
		return null;
		
		
	}
	
	public  List<YcSearchEntity> ycSearchForFile(String indexDir, String q) throws Exception {
		
		IndexSearcher searcher = getSearcher(indexDir);
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(); // 使用中文分词器
		QueryParser parser = new QueryParser("contents", analyzer); // 查询解析器
		Query query = parser.parse(q); // 通过解析要查询的String，获取查询对象
		
		long startTime = System.currentTimeMillis(); // 记录索引开始时间
		TopDocs docs = searcher.search(query, 100);// 开始查询，查询前10条数据，将记录保存在docs中
		
		long endTime = System.currentTimeMillis(); // 记录索引结束时间
		System.out.println("匹配" + q + "共耗时" + (endTime - startTime) + "毫秒");
		System.out.println("查询到" + docs.totalHits + "条记录");
		
		
		TokenStream tokenStream=null;  
		List<YcSearchEntity> list = new ArrayList<YcSearchEntity>();
		for (ScoreDoc scoreDoc : docs.scoreDocs) { // 取出每条查询结果
			Document doc = searcher.doc(scoreDoc.doc); // scoreDoc.doc相当于docID,根据这个docID来获取文档
			 String title=doc.get("filename");  
             String mark=doc.get("contents"); 
             
             //加亮处理  
             SimpleHTMLFormatter simplehtml=new SimpleHTMLFormatter("<font color='red'>", "</font>");  
             Highlighter highlighter = new Highlighter(simplehtml,new QueryScorer(query));    
            /* if(title!=null){  
                     tokenStream = analyzer.tokenStream("filename",new StringReader(title));      
                     String highLightText = highlighter.getBestFragment(tokenStream, title);  
                    System.out.println(highLightText); 
             }else{  
             }  */
             //加亮处理  
             if(mark!=null){  
                 tokenStream = analyzer.tokenStream("contents",new StringReader(mark));      
                 String[] highLightText = highlighter.getBestFragments(tokenStream, mark, 10);//.getBestFragment(tokenStream, mark); 
                 for(String one:highLightText){
                	 YcSearchEntity yce = new YcSearchEntity();
                     yce.setTitle(title);
                	 yce.setContent(one); 
                	 list.add(yce);
                 }
             } 
		}
		return list;
	}
	
	/**
	 * 关键词检索
	 * @param indexDir
	 * @param q
	 * @throws Exception
	 */
	public  void searchKeyForFile(String indexDir, String q) throws Exception {

		IndexSearcher searcher = getSearcher(indexDir);
		Query query =  new TermQuery(new Term("contents",q));
		long startTime = System.currentTimeMillis(); // 记录索引开始时间
		TopDocs docs = searcher.search(query, 100);// 开始查询，查询前10条数据，将记录保存在docs中
		long endTime = System.currentTimeMillis(); // 记录索引结束时间
		System.out.println("匹配" + q + "共耗时" + (endTime - startTime) + "毫秒");
		System.out.println("查询到" + docs.totalHits + "条记录");

		for (ScoreDoc scoreDoc : docs.scoreDocs) { // 取出每条查询结果
			Document doc = searcher.doc(scoreDoc.doc); // scoreDoc.doc相当于docID,根据这个docID来获取文档
			System.out.println(doc.get("filename"));
		}
	}
	
	public IndexSearcher getSearcher(String indexDir) throws IOException{
		if(indexDir == null || indexDir.isEmpty()){
			return null;
		}
		Directory dir = FSDirectory.open(Paths.get(indexDir)); // 获取要查询的路径，也就是索引所在的位置
		IndexReader reader = DirectoryReader.open(dir);
		IndexSearcher searcher = new IndexSearcher(reader);
		
		return searcher;
	}
	
	/** 
	 * 根据页码和分页大小获取上一次的最后一个scoredocs 
	 * @param pageIndex 
	 * @param pageSize 
	 * @param query 
	 * @param searcher 
	 * @return 
	 * @throws IOException 
	 */  
	private ScoreDoc getLastScoreDoc(int pageIndex,int pageSize,Query query,IndexSearcher searcher) throws IOException {  
	    if(pageIndex==1)return null;//如果是第一页就返回空  
	    int num = pageSize*(pageIndex-1);//获取上一页的最后数量  
	    TopDocs tds = searcher.search(query, num);  
	    return tds.scoreDocs[num-1];  
	}  
	  
	public List<YcSearchEntity> searchPageByAfter(String indexDir,String keys,int pageIndex,int pageSize) throws Exception {  
	    try {  
	        IndexSearcher searcher = getSearcher(indexDir); 
	        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer(); // 使用中文分词器
			QueryParser parser = new QueryParser("contents", analyzer); // 查询解析器
	        Query query = parser.parse(keys);  
	        //获取上一页的最后一个元素  
	        ScoreDoc lastSd = getLastScoreDoc(pageIndex, pageSize, query, searcher);  
	        //通过最后一个元素去搜索下一页的元素  
	        TopDocs tds = searcher.searchAfter(lastSd,query, pageSize);  
	        TokenStream tokenStream=null;  
			List<YcSearchEntity> list = new ArrayList<YcSearchEntity>();
			for (ScoreDoc scoreDoc : tds.scoreDocs) { // 取出每条查询结果
				Document doc = searcher.doc(scoreDoc.doc); // scoreDoc.doc相当于docID,根据这个docID来获取文档
				 String title=doc.get("filename");  
	             String mark=doc.get("contents"); 
	             
	             //加亮处理  
	             SimpleHTMLFormatter simplehtml=new SimpleHTMLFormatter("<font color='red'>", "</font>");  
	             Highlighter highlighter = new Highlighter(simplehtml,new QueryScorer(query));    
	            /* if(title!=null){  
	                     tokenStream = analyzer.tokenStream("filename",new StringReader(title));      
	                     String highLightText = highlighter.getBestFragment(tokenStream, title);  
	                    System.out.println(highLightText); 
	             }else{  
	             }  */
	             //加亮处理  
	             if(mark!=null){  
	                 tokenStream = analyzer.tokenStream("contents",new StringReader(mark));      
	                 String[] highLightText = highlighter.getBestFragments(tokenStream, mark, 10);//.getBestFragment(tokenStream, mark); 
	                 for(String one:highLightText){
	                	 YcSearchEntity yce = new YcSearchEntity();
	                     yce.setTitle(title);
	                	 yce.setContent(one); 
	                	 list.add(yce);
	                 }
	             } 
			}
			return list;
	    } catch (Exception e) {  
	        e.printStackTrace();  
	        return null;  
	    }
	}  
	
	public static void main(String[] args) {
		
		IndexGeneratorTask ig = new IndexGeneratorTask();
		try {
			ig.indexForDir(ConstantLucene.DataDirPath,ConstantLucene.indexPath);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		String q = "展示该供应商"; // 查询这个字符
		try {
			searchForFile(ConstantLucene.indexPath, q);
			//searchKeyForFile(ConstantLucene.indexPath, q);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}