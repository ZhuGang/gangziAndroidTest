package com.xiaoka.android.common.utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 文件操作工具类
 *
 * @author Shun
 * @version 2.2
 * @since 2.2
 */
public class XKFileUtil {

    /**
     * 检查是否安装SD卡
     *
     * @return
     */
    public static boolean checkSaveLocationExists() {
        String sDCardStatus = Environment.getExternalStorageState();
        boolean status;
        if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
            status = true;
        } else
            status = false;
        return status;
    }

    /**
     * 创建文件夹
     *
     * @param dir
     */
    public static void mkdir(String dir) {
        File file = new File(dir);
        file.mkdirs();
    }

    /**
     * 检查文件是否存在
     *
     * @param path
     * @return
     */
    public static boolean checkFileExists(String path) {
        boolean status = false;
        if (!TextUtils.isEmpty(path)) {
            return new File(path).exists();
        }
        return status;
    }

    public static void deleteFile(String path) {
        File f = new File(path);
        f.delete();
    }

    /**
     * 创建一个文件
     *
     * @param path      文件路径
     * @param overwrite 是否覆盖
     */
    public static void createFile(String path, boolean overwrite) {
        if (checkFileExists(path)) {
            if (overwrite) {
                deleteFile(path);
                createFile(path);
            }
        } else {
            createFile(path);
        }
    }

    private static void createFile(String path) {
        File file = new File(path);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 储存信息
     *
     * @since 3.0
     * @param path      文件路径
     * @param msg       信息内容
     * @param overwrite 是否覆盖(false=追加)
     */
    public synchronized static void storageMsg(String path, String msg, boolean overwrite) {
        if (!checkSaveLocationExists()) {
            return;
        }
        //创建一个文件,文件存在不覆盖.不存在则创建
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, !overwrite),"UTF-8"));
            bw.write(msg);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                bw = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
