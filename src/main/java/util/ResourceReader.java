package util;

import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class ResourceReader {
	
	/**
	 * エラーメッセージ一覧を取得
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getErrorMessageResource(){
		return (Map<String, String>)new Yaml().load(ClassLoader.getSystemResourceAsStream("error_message.yaml"));
	}
	
	/**
	 * Databaseの設定を取得
	 * @param configFileName　DBの設定ファイル名(拡張子はyaml)
	 * @return DB設定情報をもつMap
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getDBConfig(String configFileName){
		return (Map<String, String>)new Yaml().load(ClassLoader.getSystemResourceAsStream(configFileName));
	}
	
	/**
	 * しゃちほこ用のseedデータを取得
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getShachSeedResource() {
		return (List<String>) new Yaml().load(ClassLoader.getSystemResourceAsStream("syachi_seed.yaml")); 
	}
	
}
