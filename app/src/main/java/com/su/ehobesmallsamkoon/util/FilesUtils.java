package com.su.ehobesmallsamkoon.util;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import me.jessyan.autosize.utils.LogUtils;

public class FilesUtils {
    public static final String pathCrash = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash/";
    public static final String pathCopyCrash = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crashCopy/";
    public static final String pathSLog = Environment.getExternalStorageDirectory().getAbsolutePath() + "/serialport/";
    public static final String pathSLogEasy = Environment.getExternalStorageDirectory().getAbsolutePath() + "/serialporteasy/";

    private static final long maxFile = 53687091200L; //50M

    public static String readSaveFile(String filePath) {
        FileInputStream inputStream;
        StringBuilder sb = new StringBuilder("");
        try {
            inputStream = new FileInputStream(filePath);
            byte temp[] = new byte[1024];

            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
    public static void saveCrashInfo2File(String error) {
        try {
            long timestamp = System.currentTimeMillis();
            String time = TimeUtils.getDateToString(timestamp, "yyyy-MM-dd HH:mm:ss");
            String fileName = "crash-" + time + "-" + timestamp + ".txt";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                File dir = new File(pathCrash);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                FileOutputStream fos = new FileOutputStream(pathCrash + fileName);
                fos.write(error.getBytes());
                fos.close();
                File dir2 = new File(pathCopyCrash);
                if (!dir2.exists()) {
                    dir2.mkdirs();
                }
                FileOutputStream fos2 = new FileOutputStream(pathCopyCrash + fileName);
                fos2.write(error.getBytes());
                fos2.close();
            }
            return;
        } catch (Exception e) {

        }
    }
    public static void saveSLogEasyToFile(final String receiveData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                StringBuffer sb = new StringBuffer();
                try {
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        String time = formatter.format(new Date());
                        sb.append(time);
                        sb.append("  ----------:  ");
                        sb.append(receiveData);
                        sb.append("  ----------;  ");
                        sb.append("\r\n");
                        String fileName = TimeUtils.getYear() + "-" + (TimeUtils.getMonth() + 1) + "-" + TimeUtils.getDay() + ".log";
                        File dir = new File(pathSLogEasy);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(pathSLogEasy + fileName, true);
                        fos.write(sb.toString().getBytes());
                        fos.flush();
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    public static void saveSLogToFile(final String receiveData) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
                StringBuffer sb = new StringBuffer();
                try {
                    if (Environment.getExternalStorageState().equals(
                            Environment.MEDIA_MOUNTED)) {
                        String time = formatter.format(new Date());
                        sb.append(time);
                        sb.append("  ----------:  ");
                        sb.append(receiveData);
                        sb.append("  ----------;  ");
                        sb.append("\r\n");
                        String fileName = TimeUtils.getYear() + "-" + (TimeUtils.getMonth() + 1) + "-" + TimeUtils.getDay() + ".log";
                        File dir = new File(pathSLog);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }
                        FileOutputStream fos = new FileOutputStream(pathSLog + fileName, true);
                        fos.write(sb.toString().getBytes());
                        fos.flush();
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public static void deleteIfToMax(){
        File file = new File(pathSLog);
        if(file.exists()){
            long size = getDirSize(file);
            LogUtils.e("size:"+size);
            if(size>maxFile){
                LogUtils.e("todelete");
                deleteFile(file);
            }else
            deleteFileApk(file);
        }
    }
    public static boolean deleteFile(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return false;
        }

        if (dirFile.isFile()) {
            return dirFile.delete();
        } else {

            for (File file : dirFile.listFiles()) {
                deleteFile(file);
            }
        }

        return dirFile.delete();
    }

    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        if(files==null)
            return 0;
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 如果遇到目录则通过递归调用继续统计
            }
        }
        return dirSize;
    }
    public static void deleteFileApk(File dirFile) {
        // 如果dir对应的文件不存在，则退出
        if (!dirFile.exists()) {
            return;
        }
        if (!dirFile.isFile()) {
            File[] files = dirFile.listFiles();
            if(files==null)
                return ;
            for (File file : files) {
                String name  = file.getName();
                if(name.contains(".apk")){
                    file.delete();
                }
            }
        }


    }
}
