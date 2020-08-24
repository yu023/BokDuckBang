package bokduckbang.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Tester {
//	public static void main(String[] args) {
//		// Jsoup를 이용해서 http://www.cgv.co.kr/movies/ 크롤링
//		String url = "https://www.dabangapp.com/api/3/room/list/multi-room/bbox?api_version=3.0.1&call_type=web&filters=%7B%22multi_room_type%22%3A%5B0%5D%2C%22selling_type%22%3A%5B0%5D%2C%22deposit_range%22%3A%5B0%2C999999%5D%2C%22price_range%22%3A%5B0%2C999999%5D%2C%22trade_range%22%3A%5B0%2C999999%5D%2C%22maintenance_cost_range%22%3A%5B0%2C999999%5D%2C%22include_maintenance_option1%22%3Atrue%2C%22room_size%22%3A%5B0%2C999999%5D%2C%22supply_space_range%22%3A%5B0%2C999999%5D%2C%22room_floor_multi%22%3A%5B1%2C2%2C3%2C4%2C5%2C6%2C7%2C-1%2C0%5D%2C%22division%22%3Afalse%2C%22duplex%22%3Afalse%2C%22room_type%22%3A%5B%5D%2C%22enter_date_range%22%3A%5B0%2C999999%5D%2C%22parking_average_range%22%3A%5B0%2C999999%5D%2C%22household_num_range%22%3A%5B0%2C999999%5D%2C%22parking%22%3Afalse%2C%22animal%22%3Afalse%2C%22short_lease%22%3Afalse%2C%22full_option%22%3Afalse%2C%22built_in%22%3Afalse%2C%22elevator%22%3Afalse%2C%22balcony%22%3Afalse%2C%22loan%22%3Afalse%2C%22safety%22%3Afalse%2C%22pano%22%3Afalse%2C%22deal_type%22%3A%5B0%2C1%5D%7D&location=%5B%5B127.00976649721088%2C37.46585025269164%5D%2C%5B127.08706128698218%2C37.523699252796504%5D%5D&page=1&use_map=kakao&version=1&zoom=6";
//		Document doc = null;        //Document에는 페이지의 전체 소스가 저장된다
//
//		try {
//			doc = Jsoup.connect(url).get();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		//select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
//		String str = doc.select("body").toString();
//		str = str.substring(8, str.length()-7);
//
//		JSONParser p = new JSONParser();
//		JSONObject obj = null;
//		try {
//			obj = (JSONObject)p.parse(str);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		System.out.println(obj.getClass());
//		System.out.println(obj.get("rooms"));
//		System.out.println(obj);
//'https://maps.googleapis.com/maps/api/directions/json';\//
	//?origin=37.4946857499,127.0322689672&destination=37.5156334703411,127.04026377666612&mode=transit&departure_time=now&key=AIzaSyAwqAemUA5ddBC5tC2w-fnVsz_zaWBA0CQ

//	}
	public static void main(String[] args) {
		String myUrl = "https://maps.googleapis.com/maps/api/directions/json?origin=37.4946857499,127.0322689672&destination=37.5156334703411,127.04026377666612&mode=transit&departure_time=now&key=AIzaSyAwqAemUA5ddBC5tC2w-fnVsz_zaWBA0CQ";
		String jsonData = "";
	    BufferedReader br = null;
	    StringBuffer sb = null;
	    String returnText = "";

try {
	
	URL u = new URL(myUrl);
	
	HttpURLConnection conn = (HttpURLConnection) u.openConnection();
	conn.setRequestProperty("Accept", "application/json");
	conn.setRequestMethod("GET");
	conn.connect();
	
	System.out.println("응답코드 : " + conn.getResponseCode());
	
	System.out.println("응답메세지 : " + conn.getResponseMessage());
	
	
	br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	
	sb = new StringBuffer();
	
	while ((jsonData = br.readLine()) != null) {
		sb.append(jsonData);
	}
	
	System.out.println("sb : " + sb);
	
	returnText = sb.toString();
}catch (Exception e) {
	e.printStackTrace();
}






	}
}
