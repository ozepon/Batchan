package db;

import java.sql.SQLException;

/**
 * PreparedStatementObserverの基本機能を持つインターフェース
 * @author Mac
 *
 */
public interface IPreparedStatementObserver {
	
	/**
	 * コミット・ロールバックが通知されたときに実行したい処理を実装
	 * @throws SQLException
	 */
	public void update() throws SQLException;
}
