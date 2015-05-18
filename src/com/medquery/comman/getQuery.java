package com.medquery.comman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.mortbay.log.Log;

public class getQuery {
	final Logger log = Logger.getLogger(getQuery.class.getName());
	final String USER_AGENT = "Mozilla/5.0";
	static final Pattern reUnicode = Pattern.compile("\\\\u([0-9a-zA-Z]{4})");
	LoginFormData form = new LoginFormData();

	public List<MedEntity> getMedicine(String name) {
		String cookie = getCookies();
		log.info("----------------------getQuery----------------------");
		List<MedEntity> meds = new ArrayList<MedEntity>();

		// Google App Engine 有限制回傳字數改方法
		// String doc = Jsoup
		// .connect(
		// "http://www.chahwa.com.tw/order.php?act=query&&drug="
		// + name).timeout(10000).cookies(Connect())
		// .get().select("script").toString();
		// log.info("size:" + doc.toString().length());

		String parseString = decode1(
				Getcontext(name, cookie).replace("\\//", "")).replace("\\", "")
				.replace("}", "").replace("{", "").replace("rn", "");

		Document resault = Jsoup.parse(parseString);

		for (Element n : resault.getElementsByClass("item_text")) {
			MedEntity m = new MedEntity();
			m.setOid(n.getElementsByClass("code").text()); // 健保碼
			m.setOidprice(n.getElementsByClass("price").text());// 健保價
			m.setIsenough(n.getElementsByClass("sell_price").text());// 庫存價格
			m.setName(n.getElementsByClass("name").text());// 藥名
			meds.add(m);
		}

		return meds;
	}

	public String Getcontext(String name, String cookiePara) {
		String context = "";

		String getURL;
		try {
			getURL = "http://www.chahwa.com.tw/order.php?act=query&&drug="
					+ URLEncoder.encode(name, "utf-8");

			URL getUrl = new URL(getURL);
			HttpURLConnection connection = (HttpURLConnection) getUrl
					.openConnection();

			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(3000);
			connection.addRequestProperty("Cookie", cookiePara);
			connection.connect();
			log.info("Response code:" + connection.getResponseCode());
			// 取得輸入流，並使用Reader讀取
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			String lines;
			while ((lines = reader.readLine()) != null) {
				context = context + decode1(lines);
			}
			log.info("html size:" + context.length());
			log.info("html     :" + context);
			reader.close();
			// 斷開連接
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return context;
	}

	public String decode1(String s) {
		Matcher m = reUnicode.matcher(s);
		StringBuffer sb = new StringBuffer(s.length());
		while (m.find()) {
			m.appendReplacement(sb,
					Character.toString((char) Integer.parseInt(m.group(1), 16)));
		}
		m.appendTail(sb);
		return sb.toString();
	}

	public String getCookies() {
		Map<String, String> cookies = null;
		String cookiePara = "";
		try {
			Connection.Response res = Jsoup
					.connect("https://www.chahwa.com.tw/user.php")
					.data("username", form.getUsername(), "password",
							form.getPassword(), "wsrc", form.getWsrc(), "act",
							form.getAct(), "back_act", form.getBack_act())
					.method(Method.POST).execute();
			cookies = res.cookies();

		} catch (IOException e) {
			e.printStackTrace();
		}

		for (String cookie : cookies.keySet()) {
			cookiePara = cookiePara + cookie.toString() + "="
					+ cookies.get(cookie) + ";";
		}
		return cookiePara;
	}
}
