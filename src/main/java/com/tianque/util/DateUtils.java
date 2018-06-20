package com.tianque.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtils {

	private static DateFormat birthdayDateFormat = new SimpleDateFormat("yyyyMMdd");
	
	public static Date parseFromIdcardNo(String idcardno){
		String seqBirthday = null;
		if(idcardno != null && idcardno.length()==18){
			    seqBirthday = idcardno.substring(6, 14) ;
			    if(seqBirthday.matches("^\\d{8}$") ){
				    int year = Integer.parseInt(seqBirthday.substring(0,4));
				    int month =  Integer.parseInt(seqBirthday.substring(4,6));
				    int day = Integer.parseInt(seqBirthday.substring(6,8));
				    if(year > 1900 && year < 2100 && month < 13 && day < 32 ){
				    	try {
							return birthdayDateFormat.parse(idcardno.substring(6, 14));
						} catch (ParseException e) {
//							e.printStackTrace();
							throw new RuntimeException("出生日期格式转换错误转换");
						}
				    };
			    }
		}
		return null;
	}
	
}
