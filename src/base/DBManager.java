package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 *
 * @author d-yamaguchi
 *
 */
public class DBManager {
	public static Connection getConnection() {
		Connection con = null;
		try {

			//DBアクセス情報はプロパティーファイルから読み取り
			ResourceBundle rb = ResourceBundle.getBundle("db_access");
			final String IP = rb.getString("ip");
			final String PORT = rb.getString("port");
			final String USER = rb.getString("user");
			final String PASS = rb.getString("pass");

			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(
					"jdbc:mysql://"+IP+":"+PORT+"/KakeiBOT_DB?useUnicode=true&characterEncoding=utf8", USER, PASS);
			System.out.println("DBConnected!!");
			return con;
		} catch (ClassNotFoundException e) {

			System.out.println(e.toString());
			throw new IllegalMonitorStateException();
		} catch (SQLException e) {
			System.out.println(e.toString());
			throw new IllegalMonitorStateException();
		}
	}
}
