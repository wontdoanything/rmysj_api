package org.rmysj.api.commons.util;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

public class SpiTool {
	/**
	 * 验证 验证摘要
	 * 
	 * @return
	 */
	public static boolean checkSign(String sign, Map<String, Object> map) {
		StringBuffer sbr = new StringBuffer();
		try {
			for (Entry<String, Object> entry : map.entrySet()) {
				sbr = sbr.append(entry.getKey() + "=" + entry.getValue()).append("&");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String md5 = null;
		try {
			//md5 = CreateMd5.getMD5(URLEncoder.encode(sbr.substring(0,sbr.length()-1).toString().trim(),"utf-8"));
			md5 = MD5andKL.MD5(URLEncoder.encode(sbr.substring(0,sbr.length()-1).toString().trim(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5.equals(sign);
	}
	
	/**
	 * 构建sign
	 * 
	 * @return
	 */
	public static String structureSign(Map<String, Object> map) {
		StringBuffer sbr = new StringBuffer();
		try {
			for (Entry<String, Object> entry : map.entrySet()) {
				sbr = sbr.append(entry.getKey() + "=" + entry.getValue()).append("&");
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String md5 = null;
		try {
			//md5 = CreateMd5.getMD5(URLEncoder.encode(sbr.substring(0,sbr.length()-1).toString(),"utf-8"));
			md5 = MD5andKL.MD5(URLEncoder.encode(sbr.substring(0,sbr.length()-1).toString(),"utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return md5;
	}
	
	/**
	 * 对传过来的json进行分解排序构建，返回sign未加密的Map
	 * @param json
	 * @return
	 */
	public static Map<String,Object> formSign(JSONObject json){
		Map<String,Object> map = new LinkedHashMap<String, Object>();
		Set<String> set = json.keySet();
		Iterator<String> iterator = set.iterator();
		List<String> strList = new ArrayList<String>();
		while(iterator.hasNext()){
			String it = iterator.next();
			strList.add(it);
		}
		Collections.sort(strList);
		for(String str : strList){
			map.put(str, json.getString(str));
		}
		return map;
	}
	
	/**
	 * 首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String toUpperCaseFirstOne(String name) {
		char[] ch = name.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			if (i == 0) {
				ch[0] = Character.toUpperCase(ch[0]);
			}
		}
		StringBuffer a = new StringBuffer();
		a.append(ch);
		return a.toString();
	}
	
	public static List getNewList(List li){
		List list = new ArrayList();
        for(int i=0; i<li.size(); i++){
            Object str = li.get(i);  //获取传入集合对象的每一个元素
            if(!list.contains(str)){   //查看新集合中是否有指定的元素，如果没有则加入
                list.add(str);
            }
        }
        return list;  //返回集合
	}
	
	public static void main(String[] args) {
		byte[] mBuffer = new byte[3];
		mBuffer[0] = (byte) 0x02;
		mBuffer[1] = (byte) 0x54;
		mBuffer[2] = (byte) 0x0D;

		decode(mBuffer);
	}
	
	public static void decode(byte[] datas) {
		String str = "0123456789ABCDEF";
        int scale = 10; //转化目标进制
         
        String s = "";
        for (int i = 0; i < datas.length; i++) {
        	int data = datas[i];
        	while(data > 0){
                if(data < scale){
                    s = str.charAt(data) + s+",";
                    data = 0;
                }else{
                    int r = data%scale;
                    s = str.charAt(r) + s;
                    data  = (data-r)/scale;
                }
            }
		}
        System.out.println(s);
        
	}
}
