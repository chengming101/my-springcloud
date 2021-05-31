//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.security.test.provider.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.util.Assert;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

@Slf4j
public final class FileUtil {

    private FileUtil() {
    }

    /**
     * @param desFile 待压缩的文件或文件夹 E:\\uzip\\dist'
     * @param zipFile 压缩后的文件 E:\\uzip\\dist.zip
     */
    public static void zipFiles(String desFile, String zipFile) {
        ZipOutputStream zipOut = null;
        try {
            zipOut = new ZipOutputStream(new File(zipFile));
            zipFiles(new File(desFile), zipOut, "");
        } catch (IOException e) {
            log.error("错误{}", e.getMessage());
        } finally {
            if (zipOut != null) {
                try {
                    zipOut.close();
                } catch (IOException e) {
                    log.error("流关闭失败{}", e.getMessage());
                }
            }
        }

    }

    /**
     * @param file
     * @param zipOut
     * @param basePath 文件初始路径默认为空
     */
    private static void zipFiles(File file, ZipOutputStream zipOut, String basePath) {
        InputStream input = null;
        try {
            if (file.isFile()) {
                basePath = (basePath.length() == 0 ? "" : basePath + File.separator) + file.getName();
                input = new FileInputStream(file);
                zipOut.putNextEntry(new ZipEntry(basePath));
                int len;
                byte[] buf = new byte[1024];
                while ((len = input.read(buf)) != -1) {
                    zipOut.write(buf, 0, len);
                }
            } else {
                File[] files = file.listFiles();
                Assert.state(files != null, "文件为空目录");
                basePath = (basePath.length() == 0 ? "" : basePath + File.separator) + file.getName();
                for (File t : files) {
                    zipFiles(t, zipOut, basePath);
                }
            }
        } catch (FileNotFoundException e) {
            log.error("创建输入流失败，文件路径{}，错误信息{}", file.getAbsolutePath(), e.getMessage());
        } catch (IOException e) {
            log.error("文件压缩失败{}", e.getMessage());
        } finally {
            try {
                if (input != null)
                    input.close();
            } catch (IOException e) {
                log.error("input流关闭失败", e.getMessage());
            }
        }
    }

    public static void unZipFiles(String zipPath, String descDir) throws Exception {
        unZipFiles(new File(zipPath), descDir);
    }

    public static void unZipFiles(File zipFile, String descDir) throws IOException {
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            Assert.state(pathFile.mkdirs(), "无法创建目录/文件：" + pathFile);
        }

        ZipFile zip = new ZipFile(zipFile);
        Enumeration entries = zip.getEntries();

        while (true) {
            InputStream in;
            String outPath;
            do {
                if (!entries.hasMoreElements()) {
                    log.info("*******************解压完毕********************");
                    return;
                }

                ZipEntry entry = (ZipEntry) entries.nextElement();
                entry.setUnixMode(644);
                String zipEntryName = entry.getName();
                log.debug(zipEntryName);
                in = zip.getInputStream(entry);
                outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
                File file = new File(outPath.substring(0, outPath.lastIndexOf("/")));
                if (!file.exists()) {
                    Assert.state(file.mkdirs(), "无法创建目录/文件：" + file.getAbsolutePath());
                }
            } while ((new File(outPath)).isDirectory());

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf = new byte[1024];
            IOUtils.copy(in, out);
//            int len;
//            while((len = in.read(buf)) > 0) {
//                out.write(buf, 0, len);
//            }

            in.close();
            out.close();
        }
    }

    public static void unZipFiles(File zipFile, String descDir, String fileName) throws IOException {
        File pathFile = new File(descDir);
        if (!pathFile.exists()) {
            Assert.state(pathFile.mkdirs(), "无法创建目录/文件：" + pathFile);
        }

        ZipFile zip = new ZipFile(zipFile);
        Enumeration entries = zip.getEntries();

        while (true) {
            InputStream in;
            String outPath;
            do {
                if (!entries.hasMoreElements()) {
                    log.info("*******************解压完毕********************");
                    return;
                }

                ZipEntry entry = (ZipEntry) entries.nextElement();
                entry.setUnixMode(644);
                in = zip.getInputStream(entry);
                outPath = (descDir + fileName).replaceAll("\\*", "/");
                File file = new File(outPath.substring(0, outPath.lastIndexOf("/")));
                if (!file.exists()) {
                    Assert.state(file.mkdirs(), "无法创建目录/文件：" + file.getAbsolutePath());
                }
            } while ((new File(outPath)).isDirectory());

            OutputStream out = new FileOutputStream(outPath);
            byte[] buf = new byte[1024];

            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            in.close();
            out.close();
        }
    }

    public static File getFile(byte[] bfile, String filePath, String fileName) throws IOException {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;

        File var7;
        try {
            File dir = new File(filePath);
            if (!dir.isDirectory() || !dir.exists()) {
                Assert.state(dir.mkdirs(), "无法创建目录：" + dir.getAbsolutePath());
            }

            File file = new File(filePath + File.separator + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
            var7 = file;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException var17) {
                    log.error("无法正常关闭流", var17);
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException var16) {
                    log.error("无法正常关闭流", var16);
                }
            }

        }

        return var7;
    }

    public static void backupDist(String distPath, String tmpPath) throws IOException {
        copyDist(distPath, tmpPath);
    }

    private static void copyDist(String sourceDir, String destDir) throws IOException {
        File sourceFile = new File(sourceDir);
        if (!sourceFile.exists()) {
            Assert.state(sourceFile.mkdirs(), "无法创建源目录：" + sourceDir);
        }

        File destFile = new File(destDir);
        if (!destFile.exists()) {
            Assert.state(destFile.mkdirs(), "无法创建目的目录：" + destFile);
        }

        FileUtils.copyDirectory(sourceFile, destFile, true);
    }

    public static void deleteOriginalDist(String distPath) throws IOException {
        FileUtils.forceDelete(new File(distPath));
    }

    public static void restoreDist(String tmpPath, String distPath) throws IOException {
        copyDist(tmpPath, distPath);
    }
}
