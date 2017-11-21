package com.ismServer.common.util;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 创建文件工具类
 *
 * @author Terry Zhang
 * @date 2017-09-18 九月 21:55
 * @modify
 **/
@Component
public class FileCommonUtil {

	@Value("${file.uploadPath}")
    private String uploadPath;
//    public static final String FILE_PATH = "D://TMP//";
	
	private static String filePath;
	
	@PostConstruct
	public void init(){
		filePath = uploadPath;
	}
    
    public static File getNewFileName(String prefix, String oldFileName) {
        String fileType = oldFileName.substring(oldFileName.lastIndexOf("."));
        String fileName = DateUtil.getCurrentDateTimeWithoutSign() + fileType;
        if(!StringUtils.isEmpty(prefix)) {
            fileName = prefix + "_" + fileName;
        }
        //创建文件目录
        String[] dir = filePath.split("/");
        String fp = "";
        for(String d : dir){
        	fp += d + "/";
        	File f = new File(fp);
        	if(!f.exists()){
        		f.mkdir();
        	}
        }
        File newFile = new File(filePath + fileName);

        return newFile;
    }
    
    public static File getNewFileNameByImageType(String prefix, String oldFileName, String imageType) {
    	String fileType = oldFileName.substring(oldFileName.lastIndexOf("."));
    	String fileName = imageType + fileType;
    	if(!StringUtils.isEmpty(prefix)) {
    		fileName = prefix + "_" + fileName;
    	}
    	//创建文件目录
    	String[] dir = filePath.split("/");
    	String fp = "";
    	for(String d : dir){
    		fp += d + "/";
    		File f = new File(fp);
    		if(!f.exists()){
    			f.mkdir();
    		}
    	}
    	File newFile = new File(filePath + fileName);
    	
    	return newFile;
    }
}
