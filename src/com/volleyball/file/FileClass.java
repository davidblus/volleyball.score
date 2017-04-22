package com.volleyball.file;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.os.Environment;

public class FileClass {
	public String absoluteFileName;
	
	public FileClass(String absoluteFileName) {
		this.absoluteFileName = absoluteFileName;
	}

    public boolean writeAppend(String content) {  
        try {  
            // 打开一个随机访问文件流，按读写方式  
            RandomAccessFile randomFile = new RandomAccessFile(this.absoluteFileName, "rw");  
            // 文件长度，字节数  
            long fileLength = randomFile.length();  
            // 将写文件指针移到文件尾。  
            randomFile.seek(fileLength);  
            randomFile.writeBytes(content);  
            randomFile.close();  
            return true;
        } catch (IOException e) {  
            e.printStackTrace();  
            return false;
        }  
    }  
	
    public static String getSDPath() {  
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(  
                android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在  
        if (sdCardExist) {  
            sdDir = Environment.getExternalStorageDirectory();// 获取跟目录  
        }  
        if (sdDir == null) {
        	return null;
        }
        return sdDir.toString();  
    }  
    
    // 判断文件夹是否存在，如果不存在则创建文件夹
	public static void isDirExist(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("mkdir:" + path);
			file.mkdir();
		}
	}
	// 判断文件是否存在，如果不存在则创建文件
	public static void isFileExist(String absoluteFileName) {
		File file = new File(absoluteFileName);
		if (!file.exists()) {
			try {
				System.out.println("create file:" + absoluteFileName);
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
