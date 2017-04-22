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
            // ��һ����������ļ���������д��ʽ  
            RandomAccessFile randomFile = new RandomAccessFile(this.absoluteFileName, "rw");  
            // �ļ����ȣ��ֽ���  
            long fileLength = randomFile.length();  
            // ��д�ļ�ָ���Ƶ��ļ�β��  
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
                android.os.Environment.MEDIA_MOUNTED); // �ж�sd���Ƿ����  
        if (sdCardExist) {  
            sdDir = Environment.getExternalStorageDirectory();// ��ȡ��Ŀ¼  
        }  
        if (sdDir == null) {
        	return null;
        }
        return sdDir.toString();  
    }  
    
    // �ж��ļ����Ƿ���ڣ�����������򴴽��ļ���
	public static void isDirExist(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("mkdir:" + path);
			file.mkdir();
		}
	}
	// �ж��ļ��Ƿ���ڣ�����������򴴽��ļ�
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
