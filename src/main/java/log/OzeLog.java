package log;

import util.DateUtil;

/**
 * ログを書き出すクラス
 * @author Mac
 *
 */
public class OzeLog {
	

	/**
	 * formatを定義したenum
	 * @author Mac
	 */
	private enum format {
		/**
		 *  logテンプレート<br>
		 * 	出力サンプル<br>
		 * 	現在時刻 [ログレベル] 処理クラス名#メソッド名　メッセージ<br>
		 *	2016-03-28 14:16:07 [INFO] batch.BATCH001#execute　message<br> 
		 */
		log("%1$s [%2$s] %3$s　%4$s"),
		//　
		// 
		/**
		 * log出力class名テンプレート<br>
		 * 出力サンプル<br>
		 * クラス名#メソッド<br>
		 * SamplePackage.SampleClass#sampleMethod
		 */
		className("%1$s#%2$s");
		
		private String temple;
		private format(String temple) {
			this.temple = temple;
		}
		/**
		 * 定義されているformatを返却する
		 * @return
		 */
		public String getFormat(){
			return this.temple;
		}
	}

	public enum Level {
		ALERT()
		,ERROR()
		,WARN()
		,INFO()
		,DEBUG()
		;
		
		/**
		 * logに受け取ったメッセージを書き出す
		 * @param message ログに書き出すメッセージ
		 * @param className 呼び出し元クラス名
		 */
		public void write(String message, String className) {
			logWrite(this.name(), message, className);
		}

		/**
		 * フォーマットに沿ってログを書き出す
		 * @param level ログレベル
		 * @param msg   ログに書き出すメッセージ
		 */
		private static void logWrite(String level, String msg, String className)  {
			System.out.println(String.format(format.log.getFormat(), DateUtil.now(), level, className, msg));
		}
		
	}
	
	/**
	 * ERRORログを書き出す
	 */
	public static void ERROR() {
		String msg = "";
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.ERROR.write(msg, getCurrentClassName(ste));
	}
	
	/**
	 * ERRORログを書き出す
	 * @param message ログに書き出すメッセージ
	 */
	public static void ERROR(String message) {
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.ERROR.write(message, getCurrentClassName(ste));
	}

	/**
	 * ERRORログを書き出す<br>
	 * インターセプター用
	 * @param className　実行クラス
	 * @param methodName 実行メソッド
	 * @param message メッセージ
	 */
	public static void ERROR_TRACE(String className, String methodName, String message) {
		OzeLog.Level.ERROR.write(message, getCurrentClassName(className, methodName));
	}

	/**
	 * ALERTログを書き出す
	 */
	public static void ALERT() {
		String msg = "";
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.ALERT.write(msg, getCurrentClassName(ste));
	}

	/**
	 * ALERTログを書き出す
	 * @param message ログに書き出すメッセージ
	 */
	public static void ALERT(String message) {
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.ALERT.write(message, getCurrentClassName(ste));
	}

	/**
	 * ALERTログを書き出す<br>
	 * インターセプター用
	 * @param className　実行クラス
	 * @param methodName 実行メソッド
	 * @param message メッセージ
	 */
	public static void ALERT_TRACE(String className, String methodName, String message) {
		OzeLog.Level.ALERT.write(message, getCurrentClassName(className, methodName));
	}

	/**
	 * WRANログを書き出す
	 */
	public static void WARN() {
		String msg = "";
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.WARN.write(msg, getCurrentClassName(ste));
	}

	/**
	 * WRANログを書き出す
	 * @param message ログに書き出すメッセージ
	 */
	public static void WARN(String message) {
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.WARN.write(message, getCurrentClassName(ste));
	}

	/**
	 * WARNログを書き出す<br>
	 * インターセプター用
	 * @param className　実行クラス
	 * @param methodName 実行メソッド
	 * @param message メッセージ
	 */
	public static void WARN_TRACE(String className, String methodName, String message) {
		OzeLog.Level.WARN.write(message, getCurrentClassName(className, methodName));
	}


	/**
	 * DEBUGログを書き出す
	 */
	public static void DEBUG() {
		String msg = "";
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.DEBUG.write(msg, getCurrentClassName(ste));
	}

	/**
	 * DEBUGログを書き出す
	 * @param message ログに書き出すメッセージ
	 */
	public static void DEBUG(String message) {
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.DEBUG.write(message, getCurrentClassName(ste));
	}

	/**
	 * DEBUGログを書き出す<br>
	 * インターセプター用
	 * @param className　実行クラス
	 * @param methodName 実行メソッド
	 * @param message メッセージ
	 */
	public static void DEBUG_TRACE(String className, String methodName, String message) {
		OzeLog.Level.DEBUG.write(message, getCurrentClassName(className, methodName));
	}

	/**
	 * INFOログを書き出す
	 */
	public static void INFO() {
		String msg = "";
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.INFO.write(msg, getCurrentClassName(ste));
	}

	/**
	 * INFOログを書き出す
	 * @param message ログに書き出すメッセージ
	 */
	public static void INFO(String message) {
		// 呼び出し元classを取得
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		OzeLog.Level.INFO.write(message, getCurrentClassName(ste));
	}

	/**
	 * INFOログを書き出す<br>
	 * インターセプター用
	 * @param className　実行クラス
	 * @param methodName 実行メソッド
	 * @param message メッセージ
	 */
	public static void INFO_TRACE(String className, String methodName, String message) {
		OzeLog.Level.INFO.write(message, getCurrentClassName(className, methodName));
	}

	/**
	 * ログ書き出しを実施したクラス名とメソッド名を取得<br>
	 * @param ste StackTraceElement
	 * @return クラス名とメソッド名をフォーマットした文字列
	 */
	private static String getCurrentClassName(StackTraceElement ste) {
		return String.format(format.className.getFormat(), ste.getClassName(), ste.getMethodName());
	}

	/**
	 * ログ書き出しを実施したクラス名とメソッド名を取得<br>
	 * @param className クラス名
	 * @param methodName メソッド名
	 * @return クラス名とメソッド名をフォーマットした文字列
	 */
	private static String getCurrentClassName(String className, String methodName) {
		return String.format(format.className.getFormat(), className, methodName);
	}
}
