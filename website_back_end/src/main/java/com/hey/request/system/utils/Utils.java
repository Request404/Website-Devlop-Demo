package com.admin.enterprise.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

  /**
   * 生成一个可以嵌入数据库的当前时间。
   * @return datetime
   */
  public final static String GenerateDatetime() {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return simpleDateFormat.format(new Date());
  }
}
