package com.diseases.medical.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileCreateNameUtils {


    public static final String numberChar = "0123456789";

    /***
     * 文件名生成工具类
     */

    public static String toCreateName() {

        return getNowDatetoString() + generateNum(10);

    }

    /***
     * 生成日期字符串 yyyyMMddHHmm
     *
     * @author MRC
     * @date 2019年4月16日下午2:19:37
     * @return
     */
    public static String getNowDatetoString() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /***
     * 生成随机数
     * @author MRC
     * @date 2019年4月16日下午2:21:06
     * @param len
     * @return
     */
    public static String generateNum(int len) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < len; i++) {
            sb.append(numberChar.charAt(random.nextInt(numberChar.length())));
        }
        return sb.toString();
    }
}
