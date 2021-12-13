package com.hey.request.system.utils.mail;


import com.hey.request.system.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MailUtil {

  private final JavaMailSenderImpl javaMailSenderImpl;

  @Value("${spring.mail.username}")
  private String mailPost;

  public String mailSend(String post, String info) throws MessagingException {
    try {
      String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
      Pattern regex = Pattern.compile(check);
      Matcher matcher = regex.matcher(post);
      if (matcher.matches()) {
        MimeMessage mimeMessage = javaMailSenderImpl.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String verifyNumber = Utils.generatorNumber(6);

        helper.setSubject("[request404] 账号验证");
        helper.setText("<b>这是一封来自 Request404 的验证信息</b>" +
            "</br>" +
            "<p>你该邮箱账号正在对[request404.com]上的账号进行绑定或修改操作，该操作需要以下验证码方可进行该操作。</p>" +
            "<p></p>" +
            "<p>当前正在进行账号的邮箱认证。</p>" +
            "<p>验证码：<font style='color: #1c84dd; text-decoration:underline'>" + verifyNumber + "</font></p>" +
            "<p>验证设备标识：<font style='color: #1c84dd'>" + info + "</font></p>" +
            "</br>" +
            "<p>请保护好该验证码，避免账号信息泄露 。</p>" +
            "<p>该验证码有效期为<font style='color: #1c84dd'>5</font>分钟，如非本人操作请忽略该邮件信息。</p>" +
            "<p>详情请访问：www.request404.com 。</p>", true);

        helper.setFrom(mailPost);
        helper.setTo(post);

//    helper.addAttachment("1.jog", new File(""));
        javaMailSenderImpl.send(mimeMessage);
        return verifyNumber;
      } else {
        return null;
      }
    } catch (Exception e) {
      return null;
    }
  }
}
