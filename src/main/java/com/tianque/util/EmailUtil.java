package com.tianque.util;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.tianque.exception.DataAccessException;

public class EmailUtil {

    public static void sendEmail(String toAddress, String subject, String htmlMsg) {

        HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setHostName(ConfigUtil.get("mail.hostname"));
		htmlEmail.setSmtpPort(Integer.parseInt(ConfigUtil.get("mail.port", "25")));
		htmlEmail.setAuthentication(ConfigUtil.get("mail.username"), ConfigUtil.get("mail.password"));
		htmlEmail.setCharset(ConfigUtil.get("mail.charset"));

        try {

			htmlEmail.setFrom(ConfigUtil.get("mail.fromEmail"));
            htmlEmail.setHtmlMsg(htmlMsg);
            htmlEmail.setSubject(subject);
            htmlEmail.addTo(toAddress);
            htmlEmail.send();
        } catch (EmailException ee) {
            throw new DataAccessException(ee, "邮件发送失败");
        }

    }

}
