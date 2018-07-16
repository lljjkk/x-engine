package start;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import octopus.utils.ManagedInstance;
import octopus.utils.SimpleHtmlTable;
import octopus.utils.StringList;

public class YTools extends ToolDAO {
	public void about() {
		System.out.println("(^@^): Dynamo Settlement Engine!");
	}

	String DB_USER = "root";

	// String DB_URL =
	// "jdbc:mysql://192.168.102.165:3306/bcpeople?autoReconnect=true&autoReconnectForPools=true&characterEncoding=utf-8";
	// String DB_PASSWORD = "mysql";

	String DB_URL = "jdbc:mysql://localhost:3306/bcfinancial?autoReconnect=true&autoReconnectForPools=true&characterEncoding=utf-8";
	String DB_PASSWORD = "mysql";

	public boolean sendReport(String mailSubject, String tempFile, String attachments, Map<String, String> valueMaps) {

		// return sendReport(mailSubject, tempFile, valueMaps,
		// "liujingkun@bcpeople.com=刘景坤",
		// "tianzhiyong@bcpeople.com=田智勇;yangdong@bcpeople.com=杨栋;wangyantao@bcpeople.com=王艳涛",
		// "marenchong@bcpeople.com=马仁崇;zengjian@bcpeople.com=曾健;tuya@bcpeople.com=涂亚",
		// attachments);

		return sendReport(mailSubject, tempFile, valueMaps, "liujingkun@bcpeople.com=刘景坤", "liujingkun@bcpeople.com=0",
				"1291189840@qq.com=1;18515652440@163.com=2;liujingkun@bcpeople.com=3", attachments);
	}

	public void dailyReport() {
		Map<Integer, String> coinMap = new HashMap<Integer, String>();
		coinMap.put(2, "ETH");
		coinMap.put(1, "BTC");

		SimpleHtmlTable reportTable = new SimpleHtmlTable();
		reportTable.init("id=\"wallet_by_type\"", "币种", "数量");
		queryWalletByType.open();
		while (queryWalletByType.read()) {
			if (queryWalletByType.f_Count > 0)
				reportTable.add(queryWalletByType.f_WalletCoinType, "" + queryWalletByType.f_Count);
		}
		queryWalletByType.closeData();
		tempVars.put("wallet_by_type", reportTable.getSrc());

		reportTable.init("id=\"coin_by_type\"", "币种", "数量");
		queryCoinByType.open();
		while (queryCoinByType.read()) {
			if (queryCoinByType.f_Allcoin > 0)
				reportTable.add(queryCoinByType.f_WalletCoinType, "" + queryCoinByType.f_Allcoin);
		}
		queryCoinByType.closeData();
		tempVars.put("coin_by_type", reportTable.getSrc());

		queryStatistics.open();
		while (queryStatistics.read()) {
			tempVars.put(queryStatistics.f_VarName, "" + queryStatistics.f_Quantity);
		}
		queryStatistics.closeData();

		reportTable.init("id=\"coin_by_type\"", "币种", "数量");
		querySum11.open();
		while (querySum11.read()) {
			if (querySum11.f_Allamount > 0)
				reportTable.add(coinMap.get(querySum11.f_CoinType), "" + querySum11.f_Allamount);
		}
		querySum11.closeData();
		tempVars.put("sum_11", reportTable.getSrc());

		reportTable.init("id=\"coin_by_type\"", "币种", "数量");
		querySum12.open();
		while (querySum12.read()) {
			if (querySum12.f_Allamount > 0)
				reportTable.add(coinMap.get(querySum12.f_CoinType), "" + querySum12.f_Allamount);
		}
		querySum12.closeData();
		tempVars.put("sum_12", reportTable.getSrc());

		String date = formatter.format(new Date());
		tempVars.put("report_date", date);

		sendReport("币乘运营数据统计日报(" + date + ")", "@Report.temp.html", "", tempVars);
	}

	protected synchronized void loadConfig() {
		if (StringList.fileExists("conf/settings.ini")) {
			StringList loader = new StringList();
			loader.loadFromFile("conf/settings.ini");
			PUBLIC_CONFIG = loader.splitAsMap();
			DB_URL = PUBLIC_CONFIG.get("DB_URL");
			DB_USER = PUBLIC_CONFIG.get("DB_USER");
			DB_PASSWORD = PUBLIC_CONFIG.get("DB_PASSWORD");
		}
	}

	public void init() {
		loadConfig();
		initDB(DB_URL, DB_USER, DB_PASSWORD);
		instanciateFieldsWithAnnotation(ManagedInstance.class);
		setupMailer("smtp.exmail.qq.com", "liujingkun@bcpeople.com", "Ljk@12345");
	}

	public void test() {
	}

	public void f1(String cond) {
		System.out.println("(^@^):" + cond);
	}

	int x = 123;
	String cond = "Hello";
	int y = 20;

	public void start() {
		// "C:/Work/eclipseWS/juno/x-engine/src/main/resources/Report.temp.html"
		// System.out.println(runSnippet("f1(cond);x*y*10"));
		// queryClSms.open();
		// while (queryClSms.read()) {
		// System.out.println(queryClSms.f_Phone);
		dailyReport();
		// queryClSms.f_Id
		// queryClSms.f_Phone
		// queryClSms.f_SendTime
		// queryClSms.f_Content
		// queryClSms.f_RespTime
		// queryClSms.f_Resp
		// queryClSms.f_SmsType
		// queryClSms.f_Code
		// queryClSms.f_OrderNo
		// queryClSms.f_State
		// queryClSms.f_VerifyTime
		// queryClSms.f_SmsChannel
		// queryClSms.f_SendStatus
		// }

	}

	public static void main(String[] args) {
		// _____p.startProfiling("main");
		singleton = new YTools();
		singleton.init();
		singleton.start();
		// _____p.logPoint("singleton = new YTools();");
		if (args.length > 0) {
			String cmd = args[0];
			System.out.println("(^@^)R: [" + cmd + "]");
			if (cmd.equals("-test"))
				singleton.test();
			else if (cmd.equals("-about"))
				singleton.about();
		} else {
			singleton.about();
		}
		// _____p.endProfiling();
	}

	public YTools() {
		super();
	}

	public static YTools singleton;
}
