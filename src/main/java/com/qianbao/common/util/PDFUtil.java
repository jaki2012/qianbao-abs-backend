package com.qianbao.common.util;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author lijiechu
 * @create on 17/9/26
 * @description PDF处理工具类
 */
@Service
public class PDFUtil {

    @Autowired
    private UserinfoUtil userinfoUtil;

    private String publicKey;
    private String privateKey;

    public void signature(){

        try {
            Map<String, Object> keyMap = SignatureUtil.generateKeyPair();
            publicKey = SignatureUtil.getPublicKey(keyMap);
            privateKey = SignatureUtil.getPrivateKey(keyMap);
            System.err.println("公钥: \n\r" + publicKey);
            System.err.println("私钥: \n\r" + privateKey);
            String source = "user" + userinfoUtil.getUserID();

            byte[] data = source.getBytes();
            // 公钥加密
            byte[] encodedData = SignatureUtil.encryptByPublicKey(data, publicKey);
            System.err.println("加密后文字：\r\n" + new String(encodedData));

            // 私钥解密
            // 注意下方是encodeddata 而不是data ！
            byte[] decodedData = SignatureUtil.decryptByPrivateKey(encodedData, privateKey);
            System.err.println("解密后文字：\r\n" + new String(decodedData));
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            // 创建一个
            PdfReader pdfReader = new PdfReader("/Users/lijiechu/Desktop/ABS协议准备/认购协议——上海银行.pdf");
            PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream("/Users/lijiechu/Desktop/ABS协议准备/认购协议——上海银行2.pdf"));
            // 使用本地字体解决中英文显示问题
            BaseFont basefont = BaseFont.createFont("agreementFiles/HYQH.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

            Font font = new Font(basefont, 10);
            font.setStyle(Font.BOLD);
            // 页数是从最后一页开始的
            int lastPageIndex = pdfReader.getNumberOfPages();

            //获得pdfStamper在当前页的上层打印内容.也就是说 这些内容会覆盖在原先的pdf内容之上.
            PdfContentByte over = pdfStamper.getOverContent(lastPageIndex);
            //用pdfReader获得当前页字典对象.包含了该页的一些数据.比如该页的坐标轴信息.
            PdfDictionary pdfDictionary = pdfReader.getPageN(lastPageIndex);
            //拿到mediaBox 里面放着该页pdf的大小信息.
            PdfObject po =  pdfDictionary.get(new PdfName("MediaBox"));
            //po是一个数组对象 里面包含了该页pdf的坐标轴范围.
            PdfArray pa = (PdfArray) po;
            System.out.println(pa.size());
            // x轴最大值
            float xMax = pa.getAsNumber(2).floatValue();
            // y轴最大值
            float yMax = pa.getAsNumber(3).floatValue();


            over.beginText();
            over.setFontAndSize(font.getBaseFont(), 10);
            over.setColorFill(BaseColor.RED);
            over.setTextMatrix((float) 0.35 * xMax, (float) 0.05 * yMax);
            over.showText("甲方：" + "bdasfads32412312321ads");
            over.setTextMatrix((float) 0.35 * xMax, (float) 0.02 * yMax);
            over.showText("乙方：" + "adsfdafe3241as213asda1");
            over.endText();

            pdfStamper.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e){
            e.printStackTrace();
        }

    }
}
