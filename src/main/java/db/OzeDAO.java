package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.PreparedStatementObserver.QueryType;
import log.OzeLog;
import util.ResourceReader;

public class OzeDAO {
	// DB接続オブジェクト
	private  Connection con = null;
	private  List<PreparedStatementObserver> prepares = new ArrayList<>();
	private  List<ResultSet> resultSets = new ArrayList<>();
	// DB設定ファイル名
	private String cofingFileName = "h2.yaml";
	// 設定情報を保持したオブジェクト
	private  Map<String, String> config;
	
	/**
	 * DAOを初期化する
	 * @param con
	 */
	public OzeDAO() {
		setConnection();
	}
	
	/**
	 * DBとコネクションをはる
	 * @throws ClassNotFoundException 
	 */
	private void setConnection() {
		setDBConfig();
		try {
			Class.forName(config.get("driver"));
		} catch (ClassNotFoundException e) {
			String msg = new StringBuilder("DBDriverのロードに失敗しました: ").append(config.get("driver")).toString();
			OzeLog.ERROR(msg);
			e.printStackTrace();
			return;
		}
		String path = config.get("path");
		String user = config.get("user");
		String pass = config.get("pass"); 
		OzeLog.DEBUG("path:" + path + " user:" + user + " pass: " + pass);
		try {
			con = DriverManager.getConnection(path, user, pass);
			
			// 自動コミット解除
			con.setAutoCommit(false);
		} catch (SQLException e) {
			String msg = "コネクション接続に失敗しました";
			OzeLog.ERROR(msg);
			e.printStackTrace();
			return;
		}
	}
	
	/**
	 * セットされたSQLを発行する
	 */
	public void execute() {
		OzeLog.INFO("SQL発行");
		try {
			for (PreparedStatementObserver pre: this.prepares) {
				OzeLog.INFO(new StringBuffer("発行するSQL: ").append(pre.getSql()).toString());
				pre.execute();
			}
			commit();
		} catch (SQLException e) {
			e.printStackTrace();
			rollBack();
		} finally {
			close();
		}
	}
	
	/**
	 * ロールバック処理
	 */
	private void rollBack() {
		try {
			con.rollback();
		} catch (SQLException e) {
			OzeLog.WARN("ロールバックに失敗しました");
			e.printStackTrace();
		}
	}
	
	/**
	 * コミット処理
	 * @throws SQLException
	 */
	private void commit() throws SQLException {
		try {
			con.commit();
		} catch (SQLException e) {
			OzeLog.WARN("コミットに失敗しました");
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * クローズ処理
	 */
	private void close() {
		// ResultSet処理
		resultSetsClose();
		
		// PreparedStatementObserver処理
		for(PreparedStatementObserver pre: prepares) {
			pre.update();
		}
		// Connection処理
		connectionClose();
	}
	
	/**
	 * 発行されたResultSetオブジェクトをcloseする
	 */
	private void resultSetsClose() {
		// ResultSet処理
		for(ResultSet re: resultSets) {
			if(null != re) {
				try {
					re.close();
				} catch (SQLException e) {
					OzeLog.WARN("resultSetのcloseに失敗しました");
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * コネクションをクローズする
	 */
	private void connectionClose() {
		if (null != con) {
			try {
				con.close();
			} catch (SQLException e) {
				OzeLog.WARN("コネクションcloseに失敗しました");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 発行するCREATET文のSQLをセット
	 * @param sql CREATE文のSQL
	 * @return 
	 */
	public void createSql(String sql) {
		setSql(sql, QueryType.CREATE);
	}

	/**
	 * 発行するSELECT文のSQLをセット
	 * @param sql SELECT文のSQL
	 * @return 
	 */
	public ResultSet selectSql(String sql) {
		return selectQuery(sql);
	}

	/**
	 * 発行するSELECT文のSQLをセット
	 * @param sql SELECT文のSQL
	 * @param params SQLに埋め込むパラメータ
	 * @return 
	 */
	public ResultSet selectSql(String sql, ArrayList<? super Object> params) {
		return selectQuery(sql, params);
	}
	
	/**
	 * 発行するINSERT文のSQLをセット
	 * @param sql INSERT文のSQL
	 * @param params SQL文にセットするパラメータ
	 */
	public void setInsertSql(String sql, ArrayList<? super Object> params) {
		setSql(sql, params, QueryType.INSERT);
	}

	/**
	 * 発行するINSERT文のSQLをセット
	 * @param sql INSERT文のSQL
	 */
	public void setInsertSql(String sql) {
		setSql(sql, QueryType.INSERT);
	}

	/**
	 * 発行するUPDATE文のSQLをセット
	 * @param sql UPDATE文のSQL
	 * @param params SQL文にセットするパラメータ 
	 */
	public void setUpdateSql(String sql, ArrayList< ? super Object> params) {
		setSql(sql, params, QueryType.UPDATE);
	}

	/**
	 * 発行するUPDATE文のSQLをセット
	 * @param sql UPDATE文のSQL
	 */
	public void setUpdateSql(String sql) {
		setSql(sql, QueryType.UPDATE);
	}

	/**
	 * 発行するDELETE文のSQLをセット
	 * @param sql DELETE文のSQL
	 * @param params SQL文にセットするパラメータ
	 */
	public void setDeleteSql(String sql, ArrayList< ? super Object> params) {
		setSql(sql, params, QueryType.DELETE);
	}
	
	/**
	 * 発行するDELETE文のSQLをセット
	 * @param sql DELETE文のSQL
	 */
	public void setDeleteSql(String sql) {
		setSql(sql, QueryType.DELETE);
	}

	/**
	 * PreparedStatementObserverを作成
	 * @param sql
	 */
	private void setSql(String sql, PreparedStatementObserver.QueryType queryType) {
		PreparedStatementObserver pre = new PreparedStatementObserver(this.con, sql, queryType);
		this.prepares.add(pre);
	}
	/**
	 * PreparedStatementObserverを作成
	 * SELET文発行用
	 * @param sql
	 */
	private ResultSet selectQuery(String sql) {
		PreparedStatementObserver pre = new PreparedStatementObserver(this.con, sql, QueryType.SELECT);
		this.prepares.add(pre);
		ResultSet re = null;
		try {
			String message = new StringBuilder("SELECT文を発行: ").append(pre.getSql()).toString();
			OzeLog.INFO(message);
			re = pre.execute();
		} catch (SQLException e) {
			OzeLog.WARN("SELECT文の発行に失敗しました");
			e.printStackTrace();
		}
		resultSets.add(re);
		return re;
	}

	/**
	 * PreparedStatementObserverを作成
	 * @param sql
	 * @param params
	 * @param queryType
	 */
	private void setSql(String sql, ArrayList<? super Object> params, PreparedStatementObserver.QueryType queryType) {
		PreparedStatementObserver pre = new PreparedStatementObserver(this.con, sql, params, queryType);
		this.prepares.add(pre);
	}

	/**
	 * PreparedStatementObserverを作成
	 * SELET文発行用
	 * @param sql
	 * @param params
	 * @return 
	 */
	private ResultSet selectQuery(String sql, ArrayList<? super Object> params) {
		PreparedStatementObserver pre = new PreparedStatementObserver(this.con, sql, params, QueryType.SELECT);
		this.prepares.add(pre);
		ResultSet re = null;
		try {
			String message = new StringBuilder("SELECT文を発行: ").append(pre.getSql()).toString();
			OzeLog.INFO(message);
			re = pre.execute();
		} catch (SQLException e) {
			OzeLog.WARN("SELECT文の発行に失敗しました");
			e.printStackTrace();
		}
		resultSets.add(re);
		return re;
	}

	/**
	 * DB設定情報を取得
	 */
	private void setDBConfig() {
		config = ResourceReader.getDBConfig(getCofingFileName());
	}
	
	/**
	 * configFileNameを取得
	 * @return
	 */
	public String getCofingFileName() {
		return cofingFileName;
	}
}
