package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	// format
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	//　現在時刻を返却する
	public static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date date = new Date();
		return sdf.format(date);
	}
}
