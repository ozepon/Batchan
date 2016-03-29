package main;

import batch.BatchAction;

/**
 * バッチを起動するmainクラス
 * 起動引数に渡されたバッチ名のexecuteメソッドを呼び出す
 * @author Mac
 */
public class Main {
	
	public static void main(String[] args) {
		try {
			// DML発行用
			String[] dml = {"BATCH001"};
			new BatchAction(dml).batchStart();
			
			// 起動バッチ
			new BatchAction(args).batchStart();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		
}
