package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import base.DBManager;
import model.CategoryModel;

public class CategoryDAO {


	/**
	 * すでにっカテゴリに登録されているか
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public static int getCategoryId(String name) throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DBManager.getConnection();
			// 入力されたlogin_idが存在するか調べる
			st = con.prepareStatement("SELECT id FROM m_category WHERE name = ?");
			st.setString(1, name);
			ResultSet rs = st.executeQuery();

			System.out.println("登録済みか検証");

			if (rs.next()) {
				return rs.getInt("id");
			}else {
				return 0;
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			throw new SQLException(e);
		} finally {
			if (con != null) {
				con.close();
			}
		}

	}

	public static ArrayList<CategoryModel> getCategoryList() throws SQLException {
		Connection con = null;
		PreparedStatement st = null;

		try {
			con = DBManager.getConnection();
			// 入力されたlogin_idが存在するか調べる
			st = con.prepareStatement("SELECT * FROM m_category");
			ResultSet rs = st.executeQuery();

			ArrayList<CategoryModel>categoryList = new ArrayList<CategoryModel>();


			while(rs.next()) {
				CategoryModel category = new CategoryModel();
				category.setId(rs.getInt("id"));
				category.setName(rs.getString("name"));

				categoryList.add(category);
				}

			return categoryList;

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
