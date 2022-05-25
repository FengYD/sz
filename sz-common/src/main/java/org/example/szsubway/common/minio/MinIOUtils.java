package org.example.szsubway.common.minio;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author fengyadong
 * @date 2022/5/25 16:49
 * @Description
 */
public class MinIOUtils {


    public static String getSuffix(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            String suffix = fileName.substring(index + 1);
            if (!suffix.isEmpty()) {
                return suffix;
            }
        }
        throw new IllegalArgumentException("非法文件名称：" + fileName);
    }

    public static String getFolder() {
        LocalDate localDate = LocalDate.now();
        String[] folder = new String[]{
                String.valueOf(localDate.getYear()),
                String.valueOf(localDate.getMonthValue()),
                String.valueOf(localDate.getDayOfMonth())
        };
        return String.join("/", folder);
    }

    public static String genFileName(String fileName) {
        return UUID.randomUUID().toString().replace("-", "") + "." + getSuffix(fileName);
    }

    private MinIOUtils() {
    }

}