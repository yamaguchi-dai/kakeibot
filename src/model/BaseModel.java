package model;

import java.sql.Timestamp;

public class BaseModel {

	/**
	 * 現在時刻を取得
	 * @return
	 */
	public Timestamp getTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

}
