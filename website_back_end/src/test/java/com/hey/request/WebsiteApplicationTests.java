package com.hey.request;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hey.request.security.utils.JwtConfig;
import com.hey.request.system.entity.DBooks;
import com.hey.request.system.entity.UScore;
import com.hey.request.system.mapper.DBooksMapper;
import com.hey.request.system.service.DBooksService;
import com.hey.request.system.service.UScoreService;
import com.hey.request.system.service.UserService;
import com.hey.request.system.utils.Q;
import com.hey.request.system.utils.Utils;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Insert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
class WebsiteApplicationTests {

//  @Autowired
//  private DBooksMapper dBooksMapper;

  @Autowired
  private UserService userService;

  @Autowired
  private DBooksService dBooksService;

  @Autowired
  private UScoreService uScoreService;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void mailSendTest() throws MessagingException {
    long l = System.currentTimeMillis() + 1800;
    Date date = new Date(System.currentTimeMillis() + 1800);
    System.out.println(System.currentTimeMillis());
    System.out.println(l);
    System.out.println(date);
  }

  @Test
  void cutPageTest() {
    Q q = userService.searchAllAdminUser(0, 0, "Client System");
    System.out.println(q);
  }

  @Test
  void cutIntTest() {
    String datetime = Utils.GenerateDatetime();
    String substring = datetime.substring(0, 10);
    System.out.println(substring);
  }

  @Test
  void cutStringTest() {
    List<Integer> numbers = new ArrayList<>();
    String a = "0,0,0,0,0,0,0";
    String[] split = a.split(",");
    for (String s : split) {
      numbers.add(Integer.valueOf(s));
    }
    System.out.println(numbers);
  }

  @Test
  void addBook() {
    DBooks dBooks = new DBooks();
    dBooks.setName("时间简史");
    dBooks.setTitle("物理与时间，维度与空间");
    dBooks.setTages("科技");
    dBooks.setAuthor("霍金");
    dBooks.setCountry("美国");
    dBooks.setReleaseDate("1978-01-03");
    dBooks.setIntroduction("霍金成名作");
    dBooks.setCoverUrl("https://www.request404.com");
    dBooks.setPublisher("霍金");
    dBooks.setIsbn("001");
    dBooks.setPrice(0.01);
    dBooks.setPriceSource("当当");
    dBooks.setPages(2000);

//    Integer insert = dBooksService.addBook(dBooks);
//    System.out.println(insert);
  }

  @Test
  void DBooksInfo() {
    Q q = dBooksService.clientGetAllBooks(1, 1, "");
    System.out.println(q);
  }

  @Test
  void dateCompare() {
    String encode = bCryptPasswordEncoder.encode("123456");
    System.out.println(encode);
  }
}
