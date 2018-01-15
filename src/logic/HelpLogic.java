package logic;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.CategoryDAO;
import model.CategoryModel;

public class HelpLogic {


	/**
	 * ヘルプメッセージの作成
	 * @return
	 * @throws SQLException
	 */
	public static String helpMessage() throws SQLException {
		ArrayList<CategoryModel>categoryList = CategoryDAO.getCategoryList();
		String msg="現在登録できるカテゴリ";
		for(CategoryModel category:categoryList) {
			msg+="\n\r"+category.getName();
		}

		msg+="\n\r";
		msg+="追加してほしいカテゴリがある場合はDMください";
		msg+="\n\r";
		msg+="合計というと今月の合計金額を表示してくれます";
		return msg;

	}

}
