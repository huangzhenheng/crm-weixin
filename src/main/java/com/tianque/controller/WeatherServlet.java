package com.tianque.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tianque.util.HttpUtil;


@WebServlet("/weather.xml")
public class WeatherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = "http://flash.weather.com.cn/wmaps/xml/hangzhou.xml";
		String xml = HttpUtil.getRequestText(url);

		resp.setContentType("text/xml;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		out.print(xml);
		out.flush();
		out.close();

    }
	
	
	public static void main(String[] args) {
		String url = "http://tianque.natapp1.cc/mpac/superBrain/getFMSGInfoQueue.do";
		String xml = HttpUtil.getRequestText(url);
		
		
		System.err.println(xml);
	}
}

