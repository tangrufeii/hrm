package cn.itsource.utils;

import java.util.Random;

/**
 * 工具类
 */
public class MyTools {

    public static void main(String[] args) {
        System.out.println(getRandomNum(4));
        System.out.println(getRandomNum(8));
    }

    /**
     * @Description: 获取指定位数的随机整数
     */
    public synchronized static String getRandomNum(int num) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= num; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

}
