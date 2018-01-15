package logic;

import java.util.ArrayList;

import dao.CategoryDAO;
import dao.RegistDAO;
import model.CategoryModel;
import model.InputModel;
import model.RegistModel;

public class RegistLogic {
	/**
	 * 合計金額を取得
	 *
	 * @param lineId
	 * @return
	 * @throws Exception
	 */
	public static String getTotalPriceMessage(InputModel input) throws Exception {
		int agoTargetMonth = 0;// 今月

		if (input.getInputMessage().indexOf("先月") != -1) {
			agoTargetMonth = 1;
		} else if (input.getInputMessage().indexOf("先々月") != -1) {
			agoTargetMonth = 2;

		}

		/* リプライ用 */
		String targetMonth = agoTargetMonth == 2 ? "先々月" : agoTargetMonth == 1 ? "先月" : "今月";

		ArrayList<RegistModel> registList = RegistDAO.getRegistModelList(input.getLineId(), agoTargetMonth);
		int totalPrice = 0;
		for (RegistModel regist : registList) {
			totalPrice += regist.getPrice();
		}

		String returnMessage = targetMonth + "の合計" + totalPrice + "円";

		// 各部門の合計値
		ArrayList<CategoryModel> categoryList = CategoryDAO.getCategoryList();
		for (CategoryModel category : categoryList) {
			for (RegistModel regist : registList) {
				if (category.getId() == regist.getCategoryId()) {
					category.setPrice(category.getPrice() + regist.getPrice());
				}
			}

			if (category.getPrice() != 0) {
				returnMessage += "\n\r" + category.getName() + ":" + category.getPrice() + "円";
			}

		}

		return returnMessage;
	}

	/**
	 * 数字のみか
	 *
	 * @param str
	 * @return
	 */
	public static Boolean isNum(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}
}
