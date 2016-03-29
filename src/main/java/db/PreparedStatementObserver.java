package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import log.OzeLog;

public class PreparedStatementObserver implements IPreparedStatementObserver{
	
	private PreparedStatement preparedStatement = null;
	private Enum<PreparedStatementObserver.QueryType> queyType = null;
	private String sql = null;
	private ArrayList<? super Object> params = null;
	/**
	 * SQLクエリのタイプを定義
	 * @author Mac
	 */
	public enum QueryType {
		INSERT
		,SELECT
		,UPDATE
		,DELETE
		,CREATE
		;
	}
	
	/** 
	 * @param con
	 * @param sql
	 * @throws SQLException
	 */
	public PreparedStatementObserver(Connection con, String sql, Enum<PreparedStatementObserver.QueryType> queyType) {
		try {
			this.preparedStatement = con.prepareStatement(sql);
			this.queyType = queyType;
			this.sql = sql;
		} catch (SQLException e) {
			OzeLog.WARN("PreparedStatement生成に失敗しました");
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @param con
	 * @param sql
	 * @param params
	 * @param queyType
	 */
	public PreparedStatementObserver(Connection con, String sql, ArrayList<? super Object> params, Enum<PreparedStatementObserver.QueryType> queyType) {
		try {
			this.preparedStatement = con.prepareStatement(sql);
			this.queyType = queyType;
			this.sql = sql;
			this.params = params;
		} catch (SQLException e) {
			OzeLog.WARN("PreparedStatement生成に失敗しました");
			e.printStackTrace();
			return;
		}
	}
	
	
	/**
	 * preparedStatement.execute();を実行する
	 * @return select文の時だけResultSetに値が入っている<br>
	 * 		   select文以外はnull
	 * @throws SQLException
	 */
	public ResultSet execute() throws SQLException {
		ResultSet rs = null;
		try {
				if(this.queyType == null) {
					return rs;
				}
				
				// SELCETは別
				if(this.queyType == QueryType.SELECT) {
					sqlAddParams(); 
					rs = this.preparedStatement.executeQuery();				
				} else if (this.queyType == QueryType.CREATE){
					sqlAddParams(); 
					this.preparedStatement.execute();
				} else {
					sqlAddParams(); 
					this.preparedStatement.executeUpdate();
				}
		} catch (SQLException e) {
			OzeLog.WARN("SQL実行に失敗しました");
			e.printStackTrace();
			throw e;
		}
		return rs;
	}

	/**
	 * パラーメーター付きのSQLにパラメーターをセット
	 * @throws SQLException
	 */
	private void sqlAddParams() throws SQLException {
		if(null != params) {
			for(int i = 0; i < params.size(); i++) {
				if( params.get(i) instanceof String) {
					this.preparedStatement.setString(i + 1, (String) params.get(i));
				}
				// TODO その他にも型ごとに定義
			}
		}
	}
	
	/**
	 * コミット、ロールバックじに呼ばれる関数
	 */
	public void update() {
		try {
			this.preparedStatement.close();
		} catch (SQLException e) {
			OzeLog.WARN("PreparedStatementのcloseに失敗しました");
			e.printStackTrace();
		}
	}

	/**
	 * QueyTypeを取得
	 * @return
	 */
	public Enum<PreparedStatementObserver.QueryType> getQueyType() {
		return this.queyType;
	}

	/**
	 * 設定されているsql文を返却
	 * @return the sql
	 */
	public String getSql() {
		return sql;
	}
}
