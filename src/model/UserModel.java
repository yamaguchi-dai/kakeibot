package model;

import java.util.Date;

public class UserModel extends BaseModel{

	//固有ラインID
	private String lineId;
	//KAKEIBOTへの登録日時
	private Date createdDate;
	//最終活動日時
	private Date lastActiveDate;


	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getLastActiveDate() {
		return lastActiveDate;
	}
	public void setLastActiveDate(Date lastActiveDate) {
		this.lastActiveDate = lastActiveDate;
	}

	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

}
