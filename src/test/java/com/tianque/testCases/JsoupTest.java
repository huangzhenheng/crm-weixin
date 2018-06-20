package com.tianque.testCases;

import java.io.IOException;

import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tianque.util.EmailUtil;
import com.tianque.util.HttpUtil;


public class JsoupTest {

	public void sendEmail() {
		// ok
		EmailUtil.sendEmail("346802600@qq.com", "帐号登录",
				"你得账号于时间" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + "登录了商品管理");

	}


    public void testGetImage() throws IOException {

        for(int i = 1;i < 10;i++) {

			Document document = Jsoup.connect("http://www.topit.me/tag/%E9%A3%8E%E6%99%AF?p=" + i)
					.cookie("is_click", "3").get();
            Elements elements = document.select("#content .catalog .e>a");

            for (Element element : elements) {
                String href = element.attr("href");
                System.out.println("href:" + href);

                Document bigImageDoc = Jsoup.connect(href).cookie("is_click", "3").get();
                Element imgElement = bigImageDoc.select("#content>a").first();
                String imgSrc = imgElement.attr("href");

                System.out.println(imgSrc);
                String fileName = imgSrc.substring(imgSrc.lastIndexOf("/") + 1);
				HttpUtil.getRequestStream(imgSrc, "F:/upload/" + fileName);
            }
        }

    }
}
