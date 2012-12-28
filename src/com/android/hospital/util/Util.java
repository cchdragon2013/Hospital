package com.android.hospital.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
* @ClassName: Util 
* @Description: TODO(工具类) 
* @author wanghailong 81813780@qq.com 
* @date 2012-12-22 上午12:54:09 
*
 */
public class Util {

	/**
	 * 
	* @Title: toSimpleDate 
	* @Description: TODO(格式化时间) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String toSimpleDate(){
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date=new Date(System.currentTimeMillis());//获取当前时间
		String str=format.format(date);
		return str;
	}
	
	/**
	 * 
	* @Title: toOtherDate 
	* @Description: TODO(格式化检验号) 
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String toTest_No(String dateStr){
		switch (dateStr.length()) {
		case 1:
			dateStr="000"+dateStr;
			break;
		case 2:
			dateStr="00"+dateStr;
			break;
		case 3:
			dateStr="0"+dateStr;
			break;
		}
		SimpleDateFormat format=new SimpleDateFormat("yyMMdd");
		Date date=new Date(System.currentTimeMillis());//获取当前时间
		String str=format.format(date);
		return str+dateStr;
	}
	
	
	/**
	 * 
	* @Title: toDateOfBirth 
	* @Description: TODO(格式化病人出生日期) 
	* @param @param dateStr
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String toDateOfBirth(String dateStr){
		String[] date_of_birthArr=dateStr.split("/");
		String[] date_of_birthArr2=date_of_birthArr[2].split(" ");
		StringBuffer sBuffer=new StringBuffer();
		sBuffer.append(date_of_birthArr2[0]).append("-").append(date_of_birthArr[1]).append("-").append(date_of_birthArr[0]);
		return sBuffer.toString();
	}
	
}
