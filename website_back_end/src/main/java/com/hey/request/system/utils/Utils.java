package com.hey.request.system.utils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

  /**
   * 生成一个可以嵌入数据库的当前时间。
   *
   * @return datetime
   */
  public static String GenerateDatetime() {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return simpleDateFormat.format(new Date());
  }

  /**
   * 获取之前的日期。
   *
   * @param pass 距离现在几天
   * @return datetime
   */
  public static String GeneratePassDatetime(Integer pass) {

    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - pass);
    Date today = calendar.getTime();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

    return format.format(today);
  }


  /**
   * 生成随机字符串数字
   *
   * @param i 表示生成的长度
   * @return 随机生成的字符串
   */
  public static String generatorNumber(Integer i) {
    String hashCode = String.valueOf(Math.abs(UUID.randomUUID().toString().hashCode()));
    return hashCode.substring(hashCode.length() - i);
  }

  public static boolean isMail(String str) {
    String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
    Pattern p;
    Matcher m;
    p = Pattern.compile(regEx1);
    m = p.matcher(str);
    return m.matches();
  }

  public static Integer authenticationLevel(String authentications) {
    int level;
    if ("root".equals(authentications)) {
      level = 0;
    } else if ("admin".equals(authentications)) {
      level = 1;
    } else if ("tempAdmin".equals(authentications)) {
      level = 2;
    } else if ("other".equals(authentications)) {
      level = 3;
    }else {
      level = 100;
    }
    return level;
  }

  public static Short productType(Long productId) {
    String productIdStr = String.valueOf(productId);
    String productIndex = productIdStr.substring(0, 1);
    switch (productIndex) {
      case "1":
        return 1; //书籍

      case "4":
        return 2; //电影

      case "7":
        return 3; //音乐

      default:
        return 0;
    }
  }

  public static List<Integer> stringToNumberList(String str) {
    List<Integer> numbers = new ArrayList<>();
    String[] split = str.split(",");
    for (String s : split) {
      numbers.add(Integer.valueOf(s));
    }
    return numbers;
  }
}
