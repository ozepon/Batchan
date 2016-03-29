package batch;

/**
 * バッチ実装クラスの基底インターフェイス
 * @author Mac
 *
 */
public interface IBatchChan {
	
	/**
	 * バッチで実行する処理を実装するメソッド
	 */
	public abstract void execute();
}
