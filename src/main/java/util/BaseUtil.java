package util;

import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.*;

public class BaseUtil {

	
	// 아이피 확인
	public static String getClientIP(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");

	    if (ip == null) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null) {
	        ip = request.getRemoteAddr();
	    }

	    return ip;
	}
	
	// 로컬여부 확인
	public static boolean isLocal() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		HttpServletRequest request = servletRequestAttributes.getRequest();
		String ip = getClientIP(request);
		
		return (ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1"))? true : false;
	}
	
	// 브라우저 확인
	public String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}
	
	// 모바일 확인
	public static boolean isMobile(HttpServletRequest request) {
    	String userAgent = request.getHeader("user-agent");
    	boolean mobile1 = userAgent.matches( ".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
    	boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*"); 

    	if (mobile1 || mobile2) return true;
    	else return false;
    }
	
	// ajax 여부 확인
	public static boolean isAjax(HttpServletRequest request){
		String requestedWithHeader = request.getHeader("X-Requested-With");
	    return "XMLHttpRequest".equals(requestedWithHeader);
	}
	
	//===============================================================================================================================
	// 날짜관련
	//===============================================================================================================================
	
	// 현재 년도
	public static final int currentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}
	
	// 현재 월
	public static final int currentMonth() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.MONTH) + 1;
	}

	// 현재 날짜
	public static final String currentDate(String format) {
		return new SimpleDateFormat(format).format(new Date());
	}

	// string -> date 타입
	public static Date stringTodate(String str, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(str);
		return date;
	}

	// date -> String
	public static String dateTostring(Date date, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	// 날짜 차이 
	public static int dateDiff(Date d1, Date d2) throws Exception {
		long sec = (d1.getTime() - d2.getTime()) / 1000; // 초
		//long min = (d1.getTime() - d2.getTime()) / 60000; // 분
		//long hour = (d1.getTime() - d2.getTime()) / 3600000; // 시
		long days = sec / (24*60*60); // 일자수
		return (int) days;
	}
	
	// 분 차이
	public static int minutesDiff(Date d1, Date d2) throws Exception {
		long sec = (d1.getTime() - d2.getTime()) / 1000; // 초
		long min = (d1.getTime() - d2.getTime()) / 60000; // 분
		//long hour = (d1.getTime() - d2.getTime()) / 3600000; // 시
		//long days = sec / (24*60*60); // 일자수
		return (int) min;
	}

	// 날짜 비교
	public static boolean isBigger(String d1, String d2, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date1 = sdf.parse(d1);
		Date date2 = sdf.parse(d2);
		return date1.compareTo(date2) >= 0;
	}

	// 날짜 기간 포함 여부
	public static boolean isDateBetween(Date d, Date min, Date max) throws Exception {
		return d.compareTo(min) >= 0 && d.compareTo(max) <= 0;
	}
	
	// 날짜 형식 여부
	public static boolean isDate(String str, String format) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		boolean result = true;
		try {
			sdf.parse(str);
		}catch(Exception e) {
			result = false;
		}
		return result;
	}
	
	
	public static Boolean isEmpty(Object obj) {
		if (obj instanceof Integer)
			return obj == null || (int) obj == 0;
		else if (obj instanceof String)
			return obj == null || "".equals(obj.toString().trim());
		else if (obj instanceof List)
			return obj == null || ((List<?>) obj).isEmpty();
		else if (obj instanceof Map)
			return obj == null || ((Map<?, ?>) obj).isEmpty();
		else if (obj instanceof Object[])
			return obj == null || Array.getLength(obj) == 0;
		else
			return obj == null;
	}
	
	
	//===============================================================================================================================
	// 숫자관련
	//===============================================================================================================================
	
	// 2자리 포멧
	public static final String addZeroString(int value) {
		String result = String.format("%02d", value);
		return result;
	}
	
	// 앞에 0 제거
	public static final String removeLeadingZero(String value) {
		return value.replaceFirst("^0+(?!$)", "");
	}
	
	public static int getInt(Object obj) {
    	return (int) getDouble(obj);
	}
    
    public static long getLong(Object obj) {
    	return (long) getDouble(obj);
	}
    
    public static double getDouble(Object obj) {
    	double result = 0;
    	String str = getString(obj).replaceAll(",", "");
    	if(isNum(str)) result = Double.parseDouble(str);
    	return result;
	}
    
    public static boolean isNum(String str){
    	try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    

	//===============================================================================================================================
	// 문자관련
	//===============================================================================================================================
	
    public static boolean hasText(Object obj) {
		return StringUtils.hasText(getString(obj));
	}
    
    public static boolean equals(Object obj1, Object obj2) {
		return getString(obj1).equals(getString(obj2));
	}
    
    public static String getString(Object obj) {
		String result = "";
		if(obj != null) result = obj.toString();
		return result;
	}
    
    public static String getString(Object obj, String d) {
		String result = getString(obj);
		if(!StringUtils.hasText(result)) result = d;
		return result;
	}
    
    public static String getSHA512(String input) {

		String toReturn = null;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-512");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%0128x", new BigInteger(1, digest.digest()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}
    
    public static String urlEncode(String input) {
		return Base64.getUrlEncoder().encodeToString(input.getBytes());
	}
    
    public static String urlDecode(String input) {
    	byte[] decodedBytes = Base64.getUrlDecoder().decode(input);
		return new String(decodedBytes);
	}
    
    public static Map<String, Object> queryStringToMap(String query){
	    Map<String, Object> result = new HashMap<String, Object>();
	    for (String param : query.split("&")) {
	        String pair[] = param.split("=");
	        if (pair.length>1) {
	            result.put(pair[0], pair[1]);
	        }else{
	            result.put(pair[0], "");
	        }
	    }
	    return result;
	}
	
	
	//===============================================================================================================================
	// random
	//===============================================================================================================================

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static String getRandomPassword(int size) { 
		
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        uuid = uuid.substring(0, size); //uuid를 앞에서부터 10자리 잘라줌.
        
        return uuid;
	}

	public static String getRandomNumString(int size) { 
		char[] charSet = new char[] { 
				'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
		}; 
		
		StringBuffer sb = new StringBuffer(); 
		SecureRandom sr = new SecureRandom(); 
		sr.setSeed(new Date().getTime()); 
		int idx = 0; 
		int len = charSet.length; 
		for (int i=0; i<size; i++) { 
			idx = sr.nextInt(len); 
			sb.append(charSet[idx]); 
		} 
		return sb.toString();
	}
	

	//===============================================================================================================================
	// 
	//===============================================================================================================================

	public static boolean isImageExtension(String ext){
		String[] imageExtensions = {"jpg","bmp","gif","png","jpeg"}; //  etc
		return Arrays.asList(imageExtensions).contains(ext.toLowerCase());
	}
	
	public static boolean isVideoExtension(String ext){
		String[] imageExtensions = {"mp4","avi","mkv"}; //  etc
		return Arrays.asList(imageExtensions).contains(ext.toLowerCase());
	}
	
    
    
    
    
   
    
    
    
    
    
    

}
