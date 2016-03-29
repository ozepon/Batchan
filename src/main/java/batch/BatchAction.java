package batch;

import Interceptor.AppInterceptor;
import log.OzeLog;
import validate.Validate_001;
import validate.Validate_002;

/**
 * バッチ実行クラス
 * @author Mac
 *
 */
public class BatchAction extends AbstractBatchAction implements IMainBatchan, IBatchChan {
//public class BatchAction implements IMainBatchan, IBatchChan{
	// バッチのパッケージ名
	private static final String batchPackageName = "batch.";
	
	/**
	 * 起動引数
	 */
	private Object args;

	public BatchAction(Object args) {
		super();
		this.args = args;
	}
	
	public void setValidate() {
		// 入力チェック追加
		addValidate(new Validate_001());
		addValidate(new Validate_002());
	}

	@Override
	public void execute(String[] args) {
		String[] _args = (String[]) args;
		//　バッチ名
		String batchName = new StringBuffer(batchPackageName).append(_args[0]).toString();
		
		// バッチ呼び出し
		try {
			Object clazz = Class.forName(batchName).newInstance();
			OzeLog.DEBUG(clazz.toString());
			IBatchChan batch = (IBatchChan) AppInterceptor.getProxyInstance(clazz);
			OzeLog.DEBUG(batch.toString());
			batch.execute();
		} catch (ClassNotFoundException e) {
			System.out.println("バッチクラスがみつかりません");
			e.printStackTrace();
		} catch (SecurityException e) {
			System.out.println("executeメソッドが見つかりません");
			e.printStackTrace();
		} catch (IllegalAccessException | IllegalArgumentException e) {
			System.out.println("executメソッドの呼び出しに失敗しました");
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void execute() {
		execute((String[])this.args);
	}
}
