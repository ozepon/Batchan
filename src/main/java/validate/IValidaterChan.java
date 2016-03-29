package validate;

/**
 * バリデータインターフェース
 * @author Mac
 */
public interface IValidaterChan extends IValidaterResource {
	
	/**
	 * バリデータ処理を実装する
	 */
	public void validate() throws Exception;
}
