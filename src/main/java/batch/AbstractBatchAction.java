package batch;

import java.util.ArrayList;
import java.util.List;

import validate.IValidaterAncestor;
import validate.IValidaterArgsChan;
import validate.IValidaterChan;

/**
 * バッチ実行抽象クラス
 * @author Mac
 *
 */
public abstract class AbstractBatchAction implements IBatchChan{
	
	/**
	 * 入力チェックリスト
	 */
	List<IValidaterAncestor> validaters = new ArrayList<IValidaterAncestor>();
	
	/**
	 * 入力チェックを追加
	 * @param validate
	 */
	public void addValidate(IValidaterAncestor validate) {
		validaters.add(validate);
	}
	
	/**
	 * 入力チェックを削除
	 * @param validate
	 */
	public void removeValidate(IValidaterChan validate) {
		this.validaters.remove(validate);
	}
	
	/**
	 * バリデートを実行後、バッチ処理を実行する
	 * 
	 * @param args　バリデート対象オブジェクト
	 * @throws Exception
	 */
	public void batchStart(Object args) throws Exception {
		//　入力チェックセット
		setValidate();
		
		//　入力チェック実行
		for(IValidaterAncestor v : validaters) {
			if( v instanceof IValidaterChan) {
				((IValidaterChan) v).validate();
			}
			if( v instanceof IValidaterArgsChan){				
				((IValidaterArgsChan) v).validate(args);				
			}
		}
		//　バッチ実行
		execute();
	}
	
	/**
	 * バリデートを実行後、バッチ処理を実行する
	 * @throws Exception
	 */
	public void batchStart() throws Exception {
		//　入力チェックセット
		setValidate();
		
		//　入力チェック実行
		for(IValidaterAncestor v : validaters) {
			if( v instanceof IValidaterChan) {
				((IValidaterChan) v).validate();
			}
		}
		// TODO コンテキストにOzeDAOにインスタンスをもつ
		
		// 実行をtry cacheで囲む
		//　バッチ実行
		execute();
	}

	/**
	 * valiadteをセットする<br>
	 * <br>
	 * 入力チェックが必要な場合は<br>
	 * addValidate(ValidaterCHAN)で追加して下さい。
	 */
	 public void setValidate() {
		//e.g addValidate(new Validate_001());
	}

	/**
	 * バッチで実行する処理を実装するメソッド
	 */
	public abstract void execute();}
