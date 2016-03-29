package batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.OzeDAO;
import log.OzeLog;

/**
 * menberテーブルの情報を標準出力する
 * @author Mac
 *
 */
public class BATCH002 extends AbstractBatchAction implements IBatchChan {

	@Override
	public void execute() {
		OzeDAO d = new OzeDAO();
		String select_sql = "select * from member";
		ResultSet rs = d.selectSql(select_sql);
		//　TODO　ResultSetは必ずcloseしてください
		try {
			while (rs.next()) {
				OzeLog.INFO(rs.getString("name"));
			}
		} catch (SQLException e) {
			OzeLog.WARN("ResultSetの処理に失敗しました");
			e.printStackTrace();
			return;
		} finally {
			if(null != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					OzeLog.WARN("ResultSetのcloseに失敗しました");
					e.printStackTrace();
					return;
				}
			}
		}
	}

}
