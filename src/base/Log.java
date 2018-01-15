package base;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.sun.javafx.binding.Logging;

public class Log {
	static final String filePath =
			"WebContent\\WEB-INF\\Log\\"+new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())+".log";

	public static void main(String[] args) {
		try {

			Logger logger = getLogWriter();

			logger.finest("FNST");
			logger.finer("FNR");
			logger.fine("FN");
			logger.config("CFG");
			logger.info("INF");
			logger.warning("WNG");
			logger.severe("SVR");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("エラー");

		}
	}


	/**
	 * 情報ログ書き込み用
	 * @param str
	 */
	public static void Info(String str)  {
		try {
			Logger logger = getLogWriter();
			logger.info(str);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	/**
	 * 重大エラー書き込み用
	 * @param str
	 */
	public static void severe(String str) {
		try {
			Logger logger = getLogWriter();
			logger.severe(str);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}



	/**
	 * ログクラス取得
	 * @return
	 * @throws Exception
	 * @throws Exception
	 */
	private static Logger getLogWriter() throws Exception, Exception {

		Logger logger = Logger.getLogger(Logging.class.getName());
		FileHandler fileHandler = new FileHandler(filePath, true);
		fileHandler.setFormatter(new SimpleFormatter());
		logger.addHandler(fileHandler);
		logger.setLevel(Level.ALL);

		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setLevel(Level.CONFIG);
		logger.addHandler(consoleHandler);
		logger.setUseParentHandlers(false);
		return logger;

	}

}
