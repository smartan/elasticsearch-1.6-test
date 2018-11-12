package org.apache.lucene.test.index;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.test.utils.IndexUtil;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.Map;

public class IndexWriterTest {
    public static void main(String[] args) throws IOException {
        Directory directory = FSDirectory.open(IndexUtil.getInstance().getIndexPathFile());

        IndexWriter indexWriter = new IndexWriter(directory, new IndexWriterConfig(Version.LUCENE_4_10_4, new StandardAnalyzer()));
        Document document = new Document();

        FieldType fieldType = new FieldType();
        fieldType.setIndexed(true);
        fieldType.setStored(true);

        Field field = new Field("id", "1", fieldType);
        document.add(field);

        field = new TextField("name", "James Bron", Field.Store.YES);
        document.add(field);

        indexWriter.addDocument(document);
        indexWriter.commit();

        Map<String, String> commitData = indexWriter.getCommitData();
        for(Map.Entry entry : commitData.entrySet()){
            System.out.println("key: " + entry.getKey());
            System.out.println("value: " + entry.getValue());
        }

        int numDocs = indexWriter.numDocs();

        System.out.println(numDocs);
    }

}
