package Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import log.OzeLog;

public class AppInterceptor implements InvocationHandler {
	
	// インターセプターされるインスタンス
	Object target;
	
	private AppInterceptor(Object target) {
		this.target = target;
	}
	
	/**
	 * インターセプターする為のProxyインスタンスを返却する
	 * @param instance インターセプターされるクラス
	 * @return
	 */
	public static <T> T getProxyInstance(T instance) {
		Class<? extends Object> clazz = instance.getClass();
		// 対象クラスが実装するインターフェースのリスト
		Class<?>[] interfaces = clazz.getInterfaces();
		AppInterceptor interceptor = new AppInterceptor(instance);
		@SuppressWarnings("unchecked")
		T proxyinstance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces, interceptor);
		return proxyinstance;
	}

	/**
	 * インターセプター実行 
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// メソッド実行時にログを出力
		String className = this.target.getClass().getName();
		OzeLog.INFO_TRACE(className, method.getName(), "START");
		// 実際の呼び出されたメソッドを実行
		Object ret = method.invoke(target, args);
		OzeLog.INFO_TRACE(className, method.getName(), "END");
		return ret;
	}
}
