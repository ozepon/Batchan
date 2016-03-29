package validate;

import java.util.Map;

import util.ResourceReader;

/**
 * バリデータで使用するリソース管理
 * @author Mac
 *
 */
public interface IValidaterResource {
	/**
	 * errorMessage
	 */
	Map<String, String> errorMessage = ResourceReader.getErrorMessageResource();
}
