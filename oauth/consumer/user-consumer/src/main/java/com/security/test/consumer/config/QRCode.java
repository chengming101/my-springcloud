package com.security.test.consumer.config;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class QRCode {

    public static void main(String[] args) throws IOException, WriterException {
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        String str = "https://snailclimb.gitee.io/javaguide/";
        File file1 = new File("C:\\GSP\\Client\\nginx\\html\\dist\\img\\login_bg_right_bottom.c57770e8.jpg");

        BarcodeFormat barcodeFormat = BarcodeFormat.QR_CODE;
        int width = 150;
        int heigth = 150;

        Map map = new HashMap<>();
        map.put(EncodeHintType.CHARACTER_SET, Charset.forName("utf-8"));
        map.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        map.put(EncodeHintType.MARGIN, 1);
        BitMatrix encode = multiFormatWriter.encode(str, barcodeFormat, width, heigth, map);
        BufferedImage image = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);

//        Image image1 = ImageIO.read(file1);
//        Graphics2D g = image.createGraphics();
//        int cenX = image.getMinX()+image.getWidth() / 2;
//        int cenY = image.getMinY()+image.getHeight() / 2;
//        g.drawImage(image1, cenX, cenY, 50, 59, null);
//
//        g.dispose();
//        image.flush();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < heigth; y++) {
                image.setRGB(x, y, encode.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
            }

        }

        File file = new File("d:/test.png");
        System.out.println((ImageIO.write(image, "png", file)) ? "成功" : "失败");
    }

}
