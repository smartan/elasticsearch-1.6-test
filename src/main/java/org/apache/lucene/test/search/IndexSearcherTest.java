package org.apache.lucene.test.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.test.utils.IndexUtil;

import java.io.IOException;

public class IndexSearcherTest {
    public static void main(String[] args) throws IOException, ParseException {
        Directory directory = FSDirectory.open(IndexUtil.getInstance().getIndexPathFile());

        String[] fileArray = directory.listAll();
        for (String file : fileArray){
            System.out.println(file);
        }
        System.out.println("-------------------------------\n");

        IndexReader indexReader = DirectoryReader.open(directory);

        IndexSearcher indexSearcher = new IndexSearcher(indexReader);

//        Query query = new TermQuery(new Term("id", "1"));

        QueryParser queryParser = new QueryParser("name", new StandardAnalyzer());
        Query query = queryParser.parse("James");

        TopDocs topDocs = indexSearcher.search(query, 10);

        System.out.println("totalHits: " + topDocs.totalHits);
        System.out.println("maxScore: " + topDocs.getMaxScore());
        System.out.println("-------------------------------\n");


        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        for(ScoreDoc scoreDoc : scoreDocs){
            System.out.println("doc: " + scoreDoc.doc + ", score: " + scoreDoc.score);

            Document document = indexReader.document(scoreDoc.doc);
            if(null == document){
                continue;
            }

            System.out.println("id :" + document.get("id") + ", name: " + document.get("name"));
            System.out.println("-------------------------------");
        }
    }
}
