package org.apache.lucene.test.utils;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class IndexUtil {
    
    private static class IndexUtilInstance{
        private static final IndexUtil instance = new IndexUtil();
    }

    @Getter
    @Setter
    private String name;

    public static IndexUtil getInstance(){
        return IndexUtilInstance.instance;
    }

    public File getIndexPathFile() {
        String indexPath = System.getProperty("user.dir") + File.separator + "data";

        File indexPathFile = new File(indexPath);
        if(!indexPathFile.exists()){
            boolean success = indexPathFile.mkdir();
            if(!success){
                log.info("create index directory failed!");
                throw new RuntimeException();
            }
        }
        return indexPathFile;
    }
}
