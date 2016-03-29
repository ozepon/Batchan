package validate;

/**
 * 引数のあるバリデータを実装
 * @author Mac
 *
 */
public interface IValidaterArgsChan extends IValidaterResource{
	
	/**
	 * バリデータ処理を実装する
	 * @param obj　バリデート対象オブジェクト
	 */
	public void validate(Object obj) throws Exception;
	

}
