package controller;

import dao.UserDAO;
import logic.HelpLogic;
import logic.RegistLogic;
import model.InputModel;

public class MainController {

	/**
	 * テストコード
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		InputModel inputModel = new InputModel();
		inputModel.setLineId("test02");
		inputModel.setInputMessage("北浦和");
		inputModel.setType("text");

		String returnMessage = mainLogic(inputModel);
		System.out.println(returnMessage);

	}

	/**
	 * メインロジック 他ロジックなどはすべてここから呼び出し 詳細項目に限り\
	 *
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String mainLogic(InputModel input) throws Exception {

		String returnMessage = "";
		// 登録済みでないなら新規登録
		if (!UserDAO.isAlredyRegist(input.getLineId())) {
			UserDAO.inserttUser(input.getLineId());
		}

		// 入力文字に応じてメイン処理を分岐
		if (input.getInputMessage().indexOf("合計") != -1) {
			returnMessage = RegistLogic.getTotalPriceMessage(input);
		} else if (input.getInputMessage().indexOf("ヘルプ") != -1) {
			returnMessage = HelpLogic.helpMessage();
		} else if (input.getInputMessage().indexOf("北浦和") != -1) {
			returnMessage = "北浦和ってワードはNGだって言ってんだろカス\n\rお前の個人情報ぶちまくぞ";
		} else {
			returnMessage = RegistController.main(input);
		}

		UserDAO.userUpdate(input.getLineId());
		return returnMessage;

	}

}