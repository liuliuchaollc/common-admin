package com.zmc.utils.lucene;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.Scorer;

public class TestHighlighter {

	public static void main(String[] args) {
		
		try {
			String text = "The quick brown fox jumps over fox the lazy dog";
			TermQuery query = new TermQuery(new Term("field", "fox"));
			Scorer scorer = new QueryScorer(query);
			Highlighter highlighter = new Highlighter(scorer);
			TokenStream tokenStream = new SimpleAnalyzer().tokenStream("field",new StringReader(text));
			System.out.println(highlighter.getBestFragment(tokenStream,text));
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InvalidTokenOffsetsException e) {
			e.printStackTrace();
		}
	}
}
