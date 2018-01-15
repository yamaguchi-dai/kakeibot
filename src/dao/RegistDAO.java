package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.DBManager;
import model.RegistModel;

public class RegistDAO {

	/**
	 * 項目登録
	 * @param regist
	 * @throws SQLException
	 */
	public static void insertRegist(RegistModel regist) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();
			st = con.prepareStatement("INSERT INTO t_regist(user_id,category_id,created_date) VALUES(?,?,?)");
			st.setString(1, regist.getUserId());
			st.setInt(2, regist.getCategoryId());
			st.setTimestamp(3, regist.getTimeStamp());
			st.executeUpdate();
			System.out.println("新規記帳（値段入力まち");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	/**
	 * 金額が入力されていないレコードを取得 リミットも受けていない理由 1ユーザNullが許されるのは1件まで
	 *
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public static RegistModel getPriceNullRegistModel(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement("SELECT * FROM t_regist WHERE user_id = ? AND price IS NULL  ");
			st.setString(1, userId);

			ResultSet rs = st.executeQuery();
			RegistModel regist = new RegistModel();

			while (rs.next()) {
				regist.setId(rs.getInt("id"));
				regist.setUserId(rs.getString("user_id"));
				regist.setCategoryId(rs.getInt("category_id"));
			}
			return regist;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * 金額入力
	 *
	 * @param regist
	 * @throws SQLException
	 */
	public static void updateRegist(RegistModel regist) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		try {

			con = DBManager.getConnection();
			st = con.prepareStatement("UPDATE t_regist SET price=?  WHERE id=?;");
			st.setInt(1, regist.getPrice());
			st.setInt(2, regist.getId());

			st.executeUpdate();
			System.out.println("update has been completed");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	/**
	 * 金額が入力されていないレコードを取得 リミットも受けていない理由 1ユーザNullが許されるのは1件まで
	 *
	 * @param userId
	 * @return
	 * @throws SQLException
	 */
	public static Boolean isWaitPrice(String userId) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;
		try {
			con = DBManager.getConnection();

			st = con.prepareStatement("SELECT id FROM t_regist WHERE user_id = ? AND price IS NULL  ");
			st.setString(1, userId);

			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				// 入力待ちがあるよ
				return true;
			}
			// 入力待ちはない
			return false;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}


	/**
	 * 対象月のIDに紐づくLINEMODELを取得
	 * @param lineId 対象USERID
	 * @param agoTargetMonth 何か月前か。0今月
	 * @return
	 * @throws SQLException
	 */
	public static ArrayList<RegistModel> getRegistModelList(String lineId,int agoTargetMonth) throws SQLException {
			Connection con = null;
			PreparedStatement st = null;
			try {
				con = DBManager.getConnection();

				st = con.prepareStatement(
						" SELECT * FROM t_regist"+
						" WHERE user_id = ? "+
						" AND price IS NOT NULL "+
						" AND DATE_FORMAT(created_date,\"%Y%m\")=DATE_FORMAT(adddate(now(), interval -? month),\"%Y%m\")"
						);
				st.setString(1, lineId);
				//0 今月 1 先月
				st.setInt(2, agoTargetMonth);
				ResultSet rs = st.executeQuery();

				ArrayList<RegistModel>registList = new ArrayList<RegistModel>();
				while (rs.next()) {
					RegistModel regist = new RegistModel();
					regist.setId(rs.getInt("id"));
					regist.setCategoryId(rs.getInt("category_id"));
					regist.setPrice(rs.getInt("price"));
					regist.setCreatedDate(rs.getDate("created_date"));
					registList.add(regist);

				}
				return registList;
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
