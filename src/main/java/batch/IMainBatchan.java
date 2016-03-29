package batch;

/**
 * バッチ起動クラスが実装するインターフェース
 * @author Mac
 *
 */
public interface IMainBatchan {
	/**
	 * 起動引数を取得してバッチを実行する
	 * @param args
	 */
	void execute(String[] args);
}
