package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import base.DBManager;

public class UserDAO {
	public static void inserttUser(String lineId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("INSERT INTO t_user(line_id,created_date,last_active_date) VALUES(?,?,?)");
			st.setString(1,lineId);
			st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			st.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
			st.executeUpdate();
			System.out.println("新規利用者登録");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public static boolean isAlredyRegist(String lineId) throws SQLException {
		// 重複しているかどうか表す変数
		boolean isOverlap = false;
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DBManager.getConnection();
			// 入力されたlogin_idが存在するか調べる
			st = con.prepareStatement("SELECT line_id FROM t_user WHERE line_id = ?");
			st.setString(1, lineId);
			ResultSet rs = st.executeQuery();

			System.out.println("登録済みか検証");

			if (rs.next()) {
				//登録済み
				isOverlap = true;
			}

			System.out.println(isOverlap?"登録済み":"未登録");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

		return isOverlap;
	}

	/**
	 * ユーザーの最終アクティブ時刻を更新
	 * @param lineId 対象のユーザーのID
	 * @throws SQLException
	 */
	public static void userUpdate(String lineId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("UPDATE t_user SET last_active_date = cast( now() as datetime)  WHERE line_id=?;");
			st.setString(1, lineId);

			st.executeUpdate();
			System.out.println("update user ActiveDateTime completed!");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

}
