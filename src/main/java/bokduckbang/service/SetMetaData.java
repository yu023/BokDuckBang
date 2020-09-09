package bokduckbang.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.internal.LinkedTreeMap;

import bokduckbang.dao.RoomDao;
import bokduckbang.room.Room;

@Component
public class SetMetaData {
	@Autowired
	RoomDao roomDao;
	
	@Autowired
	Room room;
	
public List<String> getRoomId(HashMap<String, Object> map) {
		
		@SuppressWarnings("unchecked")
		ArrayList<Object> roomsList = (ArrayList<Object>)map.get("rooms");
		List<String> list = new ArrayList<String>();
		
		for(int i = 0; i < roomsList.size(); i++) {
			@SuppressWarnings("unchecked")
			LinkedTreeMap<String, Object> t = (LinkedTreeMap<String, Object>) roomsList.get(i);
			list.add("https://www.dabangapp.com/api/3/room/detail?api_version=3.0.1&call_type=web&room_id=" + t.get("id").toString() + "&use_map=kakao&version=1");
		}
		
		return list;
		
	}
	
	@Transactional
	public void insertRoom(List<String> jsonList) {
		
		int count = 0;
		
		for(int listNum = 0; listNum < jsonList.size(); listNum++) {
			
			count++;
			
			String url = jsonList.get(listNum).toString();
			Document doc = null;        
			System.out.println(url);
			Connection con = Jsoup.connect(url).userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.21 (KHTML, like Gecko) Chrome/19.0.1042.0 Safari/535.21");
			con.timeout(180000).ignoreHttpErrors(true).followRedirects(true);
			try {
				con.execute();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			try {
				doc = con.get();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String str = doc.select("body").toString();
			str = str.substring(8, str.length()-7);

			JSONParser p = new JSONParser();
			JSONObject jsonObj = null;
			try {
				jsonObj = (JSONObject)p.parse(str);
				System.out.println("jsonObj  : " + jsonObj);
			} catch (ParseException e) {
				e.printStackTrace();
			}

			@SuppressWarnings("unchecked")
			HashMap<String, Object> t = (HashMap<String, Object>) jsonObj.get("room");
			
			JSONArray priceInfoArr = (JSONArray) t.get("price_info");
			JSONArray priceInfo = (JSONArray) priceInfoArr.get(0);
			System.out.println(priceInfo);
			Integer sellType = Integer.valueOf(priceInfo.get(2).toString());
			System.out.println(sellType);
			
			if(sellType == 0) {
				room.setRoom_deposit_num(Integer.valueOf(priceInfo.get(0).toString()));
				room.setRoom_monthly_rent_num(Integer.valueOf(priceInfo.get(1).toString()));
			}else if(sellType == 1) {
				room.setRoom_lease_num(Integer.valueOf(priceInfo.get(0).toString()));
			}
			
			String option = "";
			option += splitArr(t.get("full_options").toString());
			option += splitArr(t.get("etc_options").toString());
			
			String keywords = splitArr(t.get("hash_tags").toString());
			String photos = splitArr(t.get("photos").toString());
			String service = splitArr(t.get("maintenance_items_str").toString());
	
			room.setRoom_service(service);
			
			int selling_type = Integer.valueOf(t.get("selling_type").toString());
			String[] price_info = t.get("price_title").toString().split("/");
			
			if(selling_type == 1) {
				room.setRoom_selling_type("전세");
				room.setRoom_money_lease(price_info[0]);
			}else if(selling_type == 0){
				room.setRoom_selling_type("월세");
				room.setRoom_money_deposit(price_info[0]);
				room.setRoom_money_monthly_rent(price_info[1]);
			}
			
			room.setRoom_address(t.get("address").toString());
			room.setRoom_balcony(t.get("balcony_str").toString());
			room.setRoom_total_floor(t.get("building_floor_str").toString());
			room.setRoom_floor_str(t.get("room_floor_str").toString());
			room.setRoom_elevator(t.get("elevator_str").toString());
			
			String location = t.get("location").toString();
			location = location.substring(1, location.length()-1);
			room.setRoom_lat_lng(location);
			
			int room_type = Integer.valueOf(t.get("room_type").toString());
			
			room.setRoom_type(room_type);
			room.setRoom_option(option);
			room.setRoom_keywords(keywords);
			room.setRoom_img_url(photos);
	
			room.setRoom_title(t.get("title").toString());
			room.setRoom_width(Double.valueOf(t.get("room_size").toString()));
			room.setRoom_heating(t.get("heating").toString());
			room.setRoom_loan(t.get("loan_str").toString());
			room.setRoom_service_charge(t.get("maintenance_cost_str").toString());
			room.setRoom_content(t.get("memo").toString());
			room.setRoom_move_in_date(t.get("moving_date").toString());
			room.setRoom_park_charge(t.get("parking_str").toString());
			room.setRoom_author_email("lessor@gmail.com");
			
			if(t.get("complex_id") != null) {
				room.setRoom_complex_id(t.get("complex_id").toString());
			}else {

				room.setRoom_complex_id("");
			}
			
			if(t.get("dong") != null) {
				room.setRoom_dong(t.get("dong").toString());
			}else {
				room.setRoom_dong("");
			}
			
			if(t.get("ho") != null) {
				room.setRoom_ho(t.get("ho").toString());
			}else {
				room.setRoom_ho("");
			}
			
			if(0 == roomDao.dupChkRoom(room).size()) {
				roomDao.insertRoom(room);
			}else {
				System.out.println(count + "번째 실패");
			}
		}
	}
	
	public String splitArr(String str) {
		String[] str_arr = (str.substring(1, str.length()-1)).split(",");
		String arrStr = "";
		if(str_arr != null) {
			for(int i = 0; i < str_arr.length; i++) {
				str = str_arr[i];
				if(str.length() > 2) {
					str = str.substring(1,str.length()-1);
					if(i == str_arr.length-1) {
						arrStr += str;
					}else {
						arrStr += str + ",";
					}
				}
			}
		}
		return arrStr;
	}

}
