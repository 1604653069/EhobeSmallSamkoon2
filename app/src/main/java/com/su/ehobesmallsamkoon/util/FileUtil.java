package com.su.ehobesmallsamkoon.util;

import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.su.ehobesmallsamkoon.R;
import com.su.ehobesmallsamkoon.base.BaseApplication;

import java.io.File;
import java.math.BigDecimal;

/**
 * Glide缓存工具类
 */
public class FileUtil {

    /**
     * 可根据项目需求自行更改
     */
    public static final String APP_PATH = BaseApplication.getInstance().getResources().getString(R.string.app_name);
    public static final String IMAGE_CACHE_PATH = "imageCache";


    /**
     * 是否存在SD卡
     *
     * @return
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return TextUtils.equals(status, Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取应用SD卡缓存的根路径
     *
     * @return
     */
    public static File getExternalStorageDir() {
        File RootDir;
        //判断是否有SD卡
        if (hasSDCard()) {
            RootDir = Environment.getExternalStorageDirectory();
            return RootDir;
        } else {
            ToastUtil.s("没有发现SD卡");
        }
        return null;
    }

    public static String getExternalStoragePath() {
        String path = "";
        //判断是否有SD卡
        if (hasSDCard()) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
            return path;
        }
        return path;
    }


    /**
     * 获取应用SD卡缓存的根路径
     *
     * @return
     */
    public static File getAppRootDir() {
        File RootDir;
        //判断是否有SD卡
        if (hasSDCard()) {
            RootDir = new File(Environment.getExternalStorageDirectory(), APP_PATH);
            if (!RootDir.exists())
                RootDir.mkdirs();
            return RootDir;
        } else {
            ToastUtil.s("没有发现SD卡");
        }
        return null;
    }

    /**
     * Glide 缓存路径
     *
     * @return
     */
    public static File getGlideCacheDir() {
        File file = new File(getDiskCacheDir(), IMAGE_CACHE_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;
    }

    /**
     * 缓存路径
     * 卸载应用时会一并删除
     * /data/data/包名/cache
     * /mnt/sdcard/Android/data/包名/cache
     *
     * @return
     */
    public static File getDiskCacheDir() {
        File dir = null;
        if (hasSDCard()) {
            dir = BaseApplication.getInstance().getExternalCacheDir();
        } else {
            dir = BaseApplication.getInstance().getCacheDir();
        }
        return dir;
    }

    /**
     * Files路径
     * 卸载应用时会一并删除
     * /data/data/包名/files
     * /mnt/sdcard/Android/data/包名/files
     *
     * @return
     */
    public static File getDiskFilesDir() {
        File dir = null;
        if (hasSDCard()) {
            dir = BaseApplication.getInstance().getExternalFilesDir(null);
        } else {
            dir = BaseApplication.getInstance().getFilesDir();
        }
        return dir;
    }


    public static File getAppDir(String dirName) {
        if (getAppRootDir() != null) {
            File file = new File(getAppRootDir(), dirName);
            if (!file.exists())
                file.mkdirs();
            return file;
        }
        return null;
    }


    /**
     * 获取应用SD卡缓存的根路径
     *
     * @return
     */
    public static File getRootDir() {
        File RootDir;
        String status = Environment.getExternalStorageState();
        //判断是否有SD卡
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            RootDir = new File(Environment.getExternalStorageDirectory(), APP_PATH);
            if (!RootDir.exists())
                RootDir.mkdirs();
            return RootDir;
        } else {
            ToastUtil.s("没有发现SD卡");
        }
        return null;
    }


    /**
     * 保存bitmap为图片
     *
     * @param bitmap   要保存的bitmap
     * @param dirFile  要保存的文件夹
     * @param saveName 要保存的文件名称
     */
//    public static void saveBitmap2File(Context context, Bitmap bitmap, File dirFile, String saveName) {
//        ImageUtil.saveBitmap2File(context,bitmap, dirFile, saveName);
//        // 通知图库更新
//      //  BaseApplication.getInstance().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(dirFile, saveName))));
//    }

    /**
     * 清除图片磁盘缓存
     */
    public static void clearImageDiskCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(BaseApplication.getInstance()).clearDiskCache();
                    }
                }).start();
            } else {
                Glide.get(BaseApplication.getInstance()).clearDiskCache();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清除图片内存缓存
     */
    public static void clearImageMemoryCache() {
        try {
            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
                Glide.get(BaseApplication.getInstance()).clearMemory();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 清除图片所有缓存
     */
    public static void clearImageAllCache() {
        clearImageDiskCache();
        clearImageMemoryCache();
        deleteFolderFile(getGlideCacheDir().getPath(), true);
    }



    /**
     * 获取Glide造成的缓存大小
     *
     * @return CacheSize
     */
    public static String getCacheSize() {
        try {
            return getFormatSize(getFolderSize(getGlideCacheDir()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取指定文件夹内所有文件大小的和
     *
     * @param file file
     * @return size
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (File aFileList : fileList) {
                if (aFileList.isDirectory()) {
                    size = size + getFolderSize(aFileList);
                } else {
                    size = size + aFileList.length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 删除指定目录下的文件，这里用于缓存的删除
     *
     * @param filePath       filePath
     * @param deleteThisPath deleteThisPath
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {
                        file.delete();
                    } else {
                        if (file.listFiles().length == 0) {
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 格式化单位
     *
     * @param size size
     * @return size
     */
    public static String getFormatSize(double size) {

        double kiloByte = size / 1024;
        //        if (kiloByte < 1) {
        //            return size + "Byte";
        //        }

        double megaByte = kiloByte / 1024;
        //        if (megaByte < 1) {
        //            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
        //            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        //        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);

        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

//    private boolean writeResponseBodyToDisk(ResponseBody body) {
//        try {
//            //判断文件夹是否存在
//            File files = new File(APP_PATH+"");//跟目录一个文件夹
//            if (!files.exists()) {
//                //不存在就创建出来
//                files.mkdirs();
//            }
//            //创建一个文件
//            File futureStudioIconFile = new File(SD_HOME_DIR + "download.jpg");
//            //初始化输入流
//            InputStream inputStream = null;
//            //初始化输出流
//            OutputStream outputStream = null;
//            try {
//                //设置每次读写的字节
//                byte[] fileReader = new byte[4096];
//                long fileSize = body.contentLength();
//                long fileSizeDownloaded = 0;
//                //请求返回的字节流
//                inputStream = body.byteStream();
//                //创建输出流
//                outputStream = new FileOutputStream(futureStudioIconFile);
//                //进行读取操作
//                while (true) {
//                    int read = inputStream.read(fileReader);
//                    if (read == -1) {
//                        break;
//                    }
//                    //进行写入操作
//                    outputStream.write(fileReader, 0, read);
//                    fileSizeDownloaded += read;
//                }
//
//                //刷新
//                outputStream.flush();
//                return true;
//            } catch (IOException e) {
//                return false;
//            } finally {
//                if (inputStream != null) {
//                    //关闭输入流
//                    inputStream.close();
//                }
//                if (outputStream != null) {
//                    //关闭输出流
//                    outputStream.close();
//                }
//            }
//        } catch (IOException e) {
//            return false;
//        }
//    }

}