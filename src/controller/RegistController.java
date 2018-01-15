package controller;

import dao.CategoryDAO;
import dao.RegistDAO;
import logic.RegistLogic;
import model.InputModel;
import model.RegistModel;

public class RegistController {

	/**
	 * 家計簿ロジックのメイン
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public static String main(InputModel input) throws Exception {
			// 金額入力まちがないか確認
			Boolean isWaitRegist = RegistDAO.isWaitPrice(input.getLineId());

			//入力待ちがないなら
			if (!isWaitRegist) {

				// カテゴリIDを取得
				int categoryId = CategoryDAO.getCategoryId(input.getInputMessage());
				// 未登録なら新規登録
				if (categoryId == 0) {
					return "対応したカテゴリを品目を入力してね。ほしい品目がない場合はリクエストしてください！\n\r項目がわからない場合ヘルプと送ってね";
				}

				//品名を入力
				RegistModel regist = new RegistModel();
				regist.setCategoryId(categoryId);
				regist.setUserId(input.getLineId());
				RegistDAO.insertRegist(regist);

				return "項目を登録しました！金額を入力してね";


			} else {//金額入力待ちがあるなら
				//円を潰す
				input.setInputMessage(input.getInputMessage().replaceAll("円", ""));

				if(!RegistLogic.isNum(input.getInputMessage())) {
					return "数字を入力してね";
				}


				/*inputMessageはすうじ確定後の処理*/
				RegistModel regist = RegistDAO.getPriceNullRegistModel(input.getLineId());
				regist.setPrice(Integer.parseInt(input.getInputMessage()));

				//ここでアップデート
				RegistDAO.updateRegist(regist);

				return "登録完了！";

			}

	}





}
