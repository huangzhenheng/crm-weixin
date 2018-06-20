package com.tianque.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpUtil {

	public static String getRequestTextByPost(String url) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {
			HttpResponse httpResponse = httpClient.execute(new HttpPost(url));

			// 获取HTTP请求的相应码
			int httpCode = httpResponse.getStatusLine().getStatusCode();
			if (httpCode == HttpStatus.SC_OK) {
				// 获取网页的字符流
				InputStream inputStream = httpResponse.getEntity().getContent();
				String html = IOUtils.toString(inputStream); // 将inputstream转换为字符串

				return html;

			} else {
				throw new RuntimeException("请求服务器异常:" + httpCode);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

    public static String getRequestText(String url) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
			HttpResponse httpResponse = httpClient.execute(new HttpGet(url));
            //获取HTTP请求的相应码
            int httpCode = httpResponse.getStatusLine().getStatusCode();
			if (httpCode == HttpStatus.SC_OK) {
                //获取网页的字符流
                InputStream inputStream = httpResponse.getEntity().getContent();
                String html = IOUtils.toString(inputStream); //将inputstream转换为字符串

                return html;

            } else {
                throw new RuntimeException("请求服务器异常:" + httpCode);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void getRequestStream(String url,String savePath) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            //获取HTTP请求的相应码
            int httpCode = httpResponse.getStatusLine().getStatusCode();
            if(httpCode == 200) {
                //获取图片的字节流
                InputStream inputStream = httpResponse.getEntity().getContent();
                FileOutputStream outputStream = new FileOutputStream(savePath);

                IOUtils.copy(inputStream,outputStream);

                outputStream.flush();
                outputStream.close();
                inputStream.close();

            } else {
                throw new RuntimeException("请求服务器异常:" + httpCode);
            }

            httpClient.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

	public static String postStringWithRaw(String url, String raw) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost post = new HttpPost(url);
		try {
			post.setEntity(new StringEntity(raw, "UTF-8"));
			HttpResponse response = httpClient.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return IOUtils.toString(response.getEntity().getContent());
			}
			return null;
		} catch (IOException e) {
			throw new RuntimeException("执行" + url + "异常:" + e.getMessage());
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				throw new RuntimeException("关闭HTTP请求异常");
			}
		}

	}



}