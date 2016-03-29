package batch;

import java.util.ArrayList;
import java.util.List;

import db.OzeDAO;
import log.OzeLog;
import util.ResourceReader;

/**
 * menberテーブルを作成し,
 * しゃちほこメンバーの情報を作成
 * 
 * @author Mac
 */
public class BATCH001 extends AbstractBatchAction implements IBatchChan {
	
	public void execute() {
		OzeLog.INFO("しゃちほこメンバーDML発行");
		OzeDAO d = new OzeDAO();
		
		String create_sql = "create table if not exists member(name varchar(50));";
		d.createSql(create_sql);
		
		// seedファイル読み込み
		List<String> seed = ResourceReader.getShachSeedResource();
		String seed_sql = "insert into member(name) values(?)";
		for (String s : seed) {
			ArrayList<? super Object> params = new ArrayList<>();
			params.add(s);
			d.setInsertSql(seed_sql, params);;
		}
		// SQL発行
		d.execute();
	}
}
