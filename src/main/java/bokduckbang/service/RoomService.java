package bokduckbang.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import bokduckbang.dao.RoomDao;
import bokduckbang.member.Member;
import bokduckbang.room.MinMax;
import bokduckbang.room.Room;
import bokduckbang.room.RoomImg;
import bokduckbang.room.RoomReserve;

@Service
public class RoomService {
	
	@Autowired
	RoomReserve roomReserve;
	
	@Autowired
	RoomDao roomDao;
	
	@Autowired
	SetMetaData getRoomLocationService;
	
	static Gson gson = new Gson();
	
	static HashMap<String, Object> BestFastRoot = new HashMap<String, Object>();
	public static int maxM = 0;
	public static int minM = 0;
	
	public List<Room> getDistance(HashMap<String, Object> map) {
		 
		HashMap<String, Object> pointMap = makePoint(map);
		pointMap.put("schFilterRoom", true);
		pointMap.put("orderDistance", true);
		
		List<Room> list = roomDao.selectRoom(pointMap);
		
		if(list.size() != 0) {
			Room firstRoom = list.get(0);
			BestFastRoot.put("fastRoomLat", firstRoom.getRoom_lat());
			BestFastRoot.put("fastRoomLng", firstRoom.getRoom_lng());
		}

		return list;
	}
	
	public HashMap<String, Object> makePoint(HashMap<String, Object> map) {
		Double standard_lat = Double.parseDouble(map.get("centerLat").toString());
		Double standard_lng =  Double.parseDouble(map.get("centerLng").toString());
		Double distance =  Double.parseDouble(map.get("distance").toString());
		
		HashMap<String, Object> pointMap = new HashMap<String, Object>();
		
		pointMap.put("point", "point(" + standard_lat + " " + standard_lng + ")");
		pointMap.put("distance", setDist(distance));

		return pointMap;
	}
	
	public void addMyRoom(HttpSession session, Room room) {
		if(null != session && null != room) {
			Member member = (Member) session.getAttribute("member");
			room.setRoom_author_email(member.getMember_email());
			System.out.println(room);
		}
	}
	
	public Boolean BooleanMyRoomImg(HashMap<String, Object> map) throws IOException {
		Boolean result = null;
		if(map.containsKey("files")) {
			Integer i = Double.valueOf(map.get("roomNumber").toString()).intValue();
			System.out.println(i);
			roomDao.deleteMyRoomImg(i);
			result = insertMyRoomImg(map);
		}
		System.out.println(result);
		return result;
	}
	
	public Boolean insertMyRoomImg(HashMap<String, Object> map) throws IOException {
		HashMap<String, Object> imgMap = new HashMap<String, Object>();
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) map.get("files");
		List<byte[]> imgList = new ArrayList<byte[]>();
		
		for(String file : list) {
			byte[] name = file.getBytes();
			imgList.add(name);
		}
		imgMap.put("room_img_number", map.get("roomNumber"));
		imgMap.put("images", imgList);
		
		Integer i = roomDao.insertRoomImg(imgMap);
		if(i > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public RoomReserve reserveRoom(HashMap<String, Object> map) throws ParseException {
		
		Integer result = 0;
		
		if(map.containsKey("val")) {
			result = roomDao.insertRoomReserve(map);
		}
		
		if(result > 0 ) {
			return roomDao.selectMyReserveDetail(map);
		}else {
			return null;
		}
	}
	
	public List<RoomReserve> checkReserveDetailRoom(HttpSession session, HashMap<String, Object> myWebSocketList) {
		Member member = (Member) session.getAttribute("member");
		myWebSocketList.put("member_type", member.getMember_type());
		return roomDao.selectMyReserveRoom(myWebSocketList);
	}
	
	public List<RoomReserve> returnReserveList(Member member, Integer roomNumber) {
		HashMap<String, Object> reserveMap = new HashMap<String, Object>();
		reserveMap.put("member_email", member.getMember_email());
		reserveMap.put("member_type", member.getMember_type());

		if(null != roomNumber) {
			reserveMap.put("member_email", member.getMember_email());
			reserveMap.put("room_number", roomNumber);
		}
		
		return roomDao.selectMyReserveRoom(reserveMap);
	}
	
	public Model editMyRoom(Integer roomNumber, Model model) throws UnsupportedEncodingException {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("DetailRoomInfo", true);
		map.put("room_number", roomNumber);
		model.addAttribute("room", roomDao.selectRoom(map).get(0));
		List<RoomImg> roomImgList = roomDao.selectOneRoomImg(roomNumber);
		model.addAttribute("roomImgList", returnRoomImgStr(roomImgList));
		return model;
	}
	
	public List<String> returnRoomImgStr(List<RoomImg> roomImgList) throws UnsupportedEncodingException{
		List<String> roomImages = new ArrayList<String>();
		for(int i = 0; i < roomImgList.size(); i++) {
			roomImages.add(roomImgList.get(i).getRoom_img());
		}
		return roomImages;
	}
	
	public Integer updateRoom(Room room, HttpSession session, String key, Integer num) {
		if(null != room && null != session && null != session.getAttribute("member")) {
			room.setRoom_number(num);
			Member member = (Member) session.getAttribute("member");
			room.setRoom_author_email(member.getMember_email());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", room.getMember_dest_loc());
			map.put("key", key);
			map = schRoomList(map);
			room.setRoom_lat(Double.valueOf(map.get("centerLat").toString()));
			room.setRoom_lng(Double.valueOf(map.get("centerLng").toString()));
			roomDao.updateRoom(room);
			return num;
		}else {
			return null;
		}
	}
	
	public Integer setRoom(Room room, HttpSession session, String key) {
		if(null != room && null != session && null != session.getAttribute("member")) {
			Member member = (Member) session.getAttribute("member");
			room.setRoom_author_email(member.getMember_email());
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("keyword", room.getMember_dest_loc());
			map.put("key", key);
			map = schRoomList(map);
			room.setRoom_lat(Double.valueOf(map.get("centerLat").toString()));
			room.setRoom_lng(Double.valueOf(map.get("centerLng").toString()));
			roomDao.insertRoom(room);
			return roomDao.dupChkRoom(room).get(0);
		}else {
			return null;
		}
	}
	
	public HashMap<String, Object> setLikesRoom(List<Integer> list) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		if(list.size() != 0) {
			map.put("likesRoom", true);
			map.put("room_number", list);
			map.put("roomList", roomDao.selectRoom(map));
		}else {
			map.put("likesRoom", false);
		}
		return map;
	}
	
	public HashMap<String, Object> setMyRoom(HttpSession session) throws UnsupportedEncodingException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Member member = (Member) session.getAttribute("member");
		if(null != member) {
			map.put("member_email", member.getMember_email());
			map.put("setMyRoom", true);
			List<Room> room = roomDao.selectRoom(map);
			map.put("roomList", room);
			List<RoomImg> rl = roomDao.selectRoomImg(room);
			for(int i = 0; i < rl.size(); i++) {
				rl.get(i).setRoom_img_str(rl.get(i).getRoom_img());
			}
			map.put("roomImgList", rl);
		}else {
			map.put("roomList", null);
		}
		
		return map;
	}
	
	public HashMap<String, Object> schRoomList(HashMap<String, Object> keyword) {
		String ky = keyword.get("keyword").toString();
		String myUrl = "";
		try {
			myUrl = "https://maps.googleapis.com/maps/api/place/textsearch/json?"
					+ "query=" + URLEncoder.encode(ky, "UTF-8") 
					+ "&key=" + keyword.get("key");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		JsonObject je = openJsonObject(myUrl, false);
		JsonArray resultArr = parsingHsArr(je, "results");

		JsonObject result = (JsonObject) resultArr.get(0);
		JsonObject geometry = (JsonObject) result.get("geometry");
		
		JsonObject location = (JsonObject) geometry.get("location");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("centerLat", location.get("lat").getAsDouble());
		map.put("centerLng", location.get("lng").getAsDouble());
		map.put("distance", 5.0);

		return map;
	}
	
	public Double setDist(Double distance) {
		if(distance == 3) {
			distance = (0.0269932247006001 / 2);
		}else if(distance == 5) {
			distance = (0.0449887078343335 / 2);
		}else if(distance == 10) {
			distance = (0.089977415668667 / 2);
		}
		
		return distance;
	}
	
	static HashMap<String, Object> keywordMap;
	
	public List<String> keywordRoomList(HashMap<String, Object> keyword) {
		String str = keyword.get("keyword").toString();
		keywordMap = makePoint(keyword);
		
		str = str.trim();
		if(str.contains("[")) {
			str = str.substring(1,str.length()-1);
		}
		String[] strArr = null;
		List<String> list = new ArrayList<String>();
		List<String> returnList = new ArrayList<String>();

		if(str.contains(",")) {
			strArr = str.split(",");
			for(String arrStr : strArr) {
				arrStr = arrStr.trim();
				returnList.add(arrStr.trim());
				if(arrStr.contains(" ")) {
					arrStr = arrStr.replace(" ", "%");
				}
				list.add("%"+arrStr+"%");
			}
		}
		
		if(strArr == null && !str.equals("")) {
			returnList.add(str.trim());
			if(str.contains(" ")) {
				str = str.replace(" ", "%");
			}
			list.add("%"+str+"%");
		}

		keywordMap.put("list", list);
		keywordMap.put("keywordSch", true);

		if(returnList.size() == 0) {
			returnList.clear();
		}
		
		return returnList;
	}
	
	public List<Room> returnRoom(HashMap<String, Object> map) {
		map.put("schRoomNum", true);
		return roomDao.selectRoom(map);
	} 
	
	public HashMap<String, Object> filter(MemberService memberService, HttpSession session, HashMap<String, Object> map) {
		
		List<Room> originList = roomDao.selectRoom(returnRangeHs(map));
		List<Room> filterList = new ArrayList<Room>();
		
		if(null != originList) {
			for(Room room : originList) {
				if(room.getRoom_status().equals("1")) {
					filterList.add(room);
				}
			}
		}
		
		HashMap<String, Object> newhs = new HashMap<String, Object>();
		
		if(0 < filterList.size()) {
			List<Integer> list = memberService.getLikeList(session);
			newhs.put("likeList", list);
			newhs.put("result", filterList);
		}else {
			newhs.put("result", null);
		}
		
		return newhs;
	}
	
	public HashMap<String, Integer> getMoneyRange(HashMap<String, Object> map) {
		
		HashMap<String,Object> rangeHs = returnRangeHs(map);
		HashMap<String, Integer> money = new HashMap<String, Integer>();
		
		if(rangeHs.containsKey("select1") || rangeHs.containsKey("select2")) {
			rangeHs.put("max", true);
			rangeHs.put("min", true);
			MinMax minMax = roomDao.selectMoney(rangeHs).get(0);
			if(minMax != null) {
				if(rangeHs.containsKey("select1") && (Boolean) rangeHs.get("select1")) {
					money.put("max",minMax.getMaxLease());
					money.put("min",minMax.getMinLease());
					minMax.setMulti(money.get("max"), money.get("min"));
					money.put("multi",minMax.getMulti());
				}else if(rangeHs.containsKey("select2") && (Boolean) rangeHs.get("select2")) {
					money.put("max",minMax.getMaxDeposit());
					money.put("min",minMax.getMinDeposit());
					minMax.setMulti(money.get("max"), money.get("min"));
					money.put("multi",minMax.getMulti());
					money.put("maxRent",minMax.getMaxRent());
					money.put("minRent",minMax.getMinRent());
					minMax.setRentMulti(money.get("maxRent"), money.get("minRent"));
					money.put("rentMulti",minMax.getRentMulti());
				}
			}
		}
		
		
		return money;
	}
	
	public HashMap<String, Object> returnRangeHs(HashMap<String, Object> map) {
		String sellStr = map.get("room_selling_type").toString();
		
		HashMap<String, Object> rangeHs = makePoint(map);
		
		if(map.containsKey("range")) {
			String rangeString = map.get("range").toString();
			
			if(rangeString.contains("[")){
				rangeString = rangeString.substring(1,rangeString.length()-1);
			}
			
			if(!rangeString.equals("")) {
				String[] rangeArr = rangeString.split(",");
				
				if(rangeArr.length > 0) {
					rangeHs.put("rangeFilterRoom", true);
					for(String ran : rangeArr) {
						ran = ran.trim();
						if(ran.equals("range1")) {
							rangeHs.put("range1", true);
						}
						if(ran.equals("range2")) {
							rangeHs.put("range2", true);
						}
						if(ran.equals("range3")) {
							rangeHs.put("range3", true);
						}
						if(ran.equals("range4")) {
							rangeHs.put("range4", true);
						}
					}
				}
			}else {
				rangeHs.put("orderDistance", true);
			}
			
			if(sellStr.equals("select1")) {
				rangeHs.put("yearRange", true);
			}else if(sellStr.equals("select2")) {
				rangeHs.put("monthRange", true);
			}else if(sellStr.equals("all")) {
				rangeHs.put("allRange", true);
			}
			
			HashMap<String, Object> makePoint = makePoint(map);
			
			rangeHs.put("schFilterRoom", true);
			rangeHs.put("distance", makePoint.get("distance"));
			rangeHs.put("point", makePoint.get("point"));
		}
		
		if(map.containsKey("type") && !map.get("type").equals("none") && map.get("type").equals("lease")) {
			rangeHs.put("minmaxLease", true);
			rangeHs.put("minlease", map.get("minlease"));
			rangeHs.put("maxlease", map.get("maxlease"));
		}
		if(map.containsKey("type") && !map.get("type").equals("none") && (map.get("type").equals("deposit") || map.get("type").equals("rent"))) {
			rangeHs.put("minmaxRent", true);
			rangeHs.put("mindeposit", map.get("mindeposit"));
			rangeHs.put("maxdeposit", map.get("maxdeposit"));
			rangeHs.put("minrent", map.get("minrent"));
			rangeHs.put("maxrent", map.get("maxrent"));
		}
		
		if(null != keywordMap && keywordMap.containsKey("keywordSch")) {
			@SuppressWarnings("unchecked")
			List<String> keyList = (List<String>) keywordMap.get("list");
			if(keyList.size() > 0) {
				rangeHs.put("keywordSch", true);
				rangeHs.put("list", keywordMap.get("list"));
			}
		}
		
		if(sellStr.equals("select1")) {
			rangeHs.put("select1", true);
		}else if(sellStr.equals("select2")) {
			rangeHs.put("select2", true);
		}
		
		return rangeHs;
	}

	public HashMap<String, Object> getFastRoot(HashMap<String, Object> map) {
		
		String myUrl = "https://maps.googleapis.com/maps/api/directions/json?"
				+ "origin=" + map.get("lat") + "," + map.get("lng")
				+ "&destination=" + BestFastRoot.get("fastRoomLat") + "," + BestFastRoot.get("fastRoomLng")
				+ "&mode=transit"
				+ "&departure_time=now"
				+ "&key=" + map.get("key");
		
		
		HashMap<String, Object> fastRoot = new HashMap<String, Object>();
		System.out.println(myUrl);
		JsonObject je = openJsonObject(myUrl, true);
		JsonObject routesHs = parsingArrHs(parsingHsArr(je, "routes"), 0); //3번 legs. polyline 있는 hashmap
		
		JsonObject legs = parsingArrHs(parsingHsArr(routesHs, "legs"), 0); //legs 내의 애들 고르기 위해 배열 0번째 불러옴.hs로 불러야 해
		JsonArray legsStepsArr = parsingHsArr(legs, "steps"); // legs 의 steps 배열로 받아와서 for문 돌릴거야 steps 마다
		
		JsonObject legsSteps = null;
		JsonObject stopPoint = null;
		
		ArrayList<String> stepArr = new ArrayList<String>();
		
		String route = "총 " 
						+ strSubString(parsingHsStr(parsingHsHs(legs, "duration"), "text"))
						+ " / "
						+ strSubString(parsingHsStr(parsingHsHs(legs, "distance"), "text"));
		stepArr.add(route);
		
		for(int i = 0; i <legsStepsArr.size(); i++) {
			
			route="";
			
			legsSteps = parsingArrHs(legsStepsArr, i); // i번째 불러와서 hs로 받음

			String mode = strSubString(parsingHsStr(legsSteps, "travel_mode"));
			
			if(mode.equals("WALKING")) {
				route += strSubString(parsingHsStr(legsSteps, "html_instructions"))
						+ " (" 
						+ strSubString(parsingHsStr(parsingHsHs(legsSteps, "distance"), "text")) 
						+ ", "
						+ strSubString(parsingHsStr(parsingHsHs(legsSteps, "duration"), "text")) 
						+ ")";
			}else if(mode.equals("TRANSIT")) {
				
				JsonObject transit =  (JsonObject) legsSteps.get("transit_details");
				stopPoint =  (JsonObject) transit.get("departure_stop");
				
				JsonObject line =  parsingHsHs(parsingHsHs(legsSteps, "transit_details"), "line");
				JsonObject vehicle =  (JsonObject) line.get("vehicle");
				
				route += strSubString(parsingHsStr(stopPoint, "name")) + "에서 ";
				
				route += strSubString(parsingHsStr(transit, "headsign"))
						+ "행 " 
						+ strSubString(parsingHsStr(vehicle, "name"))
						+ " " 
						+ strSubString(parsingHsStr(line, "short_name"))
						+ " 탑승 후 ";
				
				route += strSubString(parsingHsStr(parsingHsHs(transit, "arrival_stop"), "name")) + "에서 하차";

			}
			
			stepArr.add(route);
		}
		
		JsonObject overview_polyline = (JsonObject) routesHs.get("overview_polyline"); //폴리라인 map
		
		fastRoot.put("distance", legs.get("distance").toString());
		fastRoot.put("duration", legs.get("duration").toString());
		fastRoot.put("points", overview_polyline.get("points").toString());
		fastRoot.put("route", stepArr);
		
		return fastRoot;
	}
	
	
	public List<Room> getRoomNumber(HashMap<String, Object> points) {
		return returnRoom(points);
	}
	
	public Room changeSellingType(int num) {
		Integer i = roomDao.changeSellingType(num);
		System.out.println(i);
		return roomDetail(num);
	}
	
	public Room roomDetail(int num) {
		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("DetailRoomInfo", true);
		map.put("room_number", num);
		return roomDao.selectRoom(map).get(0);
	}
	
	public Model returnRoomDetail(int num, Model model, HttpSession session, MemberService memberService) throws UnsupportedEncodingException {
		roomDao.addRoomHits(num);
		Room room = roomDetail(num);
		
		if(session != null && session.getAttribute("member") != null) {
			Member member = (Member) session.getAttribute("member");
			HashMap<String, Object> likesMap = new HashMap<String,Object>();
			likesMap.put("likes_member_id", member.getMember_email());
			likesMap.put("likes_room", room.getRoom_number());
			model.addAttribute("roomLikes", memberService.checkLikes(likesMap));
		}
		
		model.addAttribute("room", room);
		
		if(null != room.getRoom_img_url()) {
			model.addAttribute("roomUrl", roomImgUrl(room));
		}else {
			List<RoomImg> roomImgList = roomDao.selectOneRoomImg(room.getRoom_number());
			model.addAttribute("roomImgList", returnRoomImgStr(roomImgList));
		}
		model.addAttribute("roomKeyword", roomKeyword(room));
		model.addAttribute("roomOption", roomOption(room));
		
		return model;
	}
	
	public Boolean deleteMyRoom(Integer num) {
		Integer result = roomDao.deleteMyRoom(num);
		if(result > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	public void countRoomLikes(HashMap<String, Object> likesMap) {
		roomDao.countRoomLikes(likesMap);
	}
	
	public String getRoomImg(List<RoomImg> roomImg) throws UnsupportedEncodingException {
		return roomImg.get(0).getRoom_img();
	}
	
	public String[] roomImgUrl(Room room) {
		String urlStr = room.getRoom_img_url();
		return urlStr.split(",");
	}
	
	public String[] roomKeyword(Room room) {
		String urlStr = room.getRoom_keywords();
		return urlStr.split(",");
	}
	
	public String[] roomOption(Room room) {
		String urlStr = room.getRoom_option();
		return urlStr.split(",");
	}
	
	public JsonObject openJsonObject(String myUrl, Boolean lang) {
		
		String jsonData = "";
		BufferedReader br = null;
		StringBuffer sb = null;
		String returnText = "";
		JsonObject jsonObject = null;
		
		try {
			
			URL u = new URL(myUrl);
			
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept-Language", "ko-KR,ko;q=0.8,en-US;q=0.6,en;q=0.4");
			conn.connect();
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			
			sb = new StringBuffer();
			
			while ((jsonData = br.readLine()) != null) {
				sb.append(jsonData);
			}
			
			returnText = sb.toString();
			
			jsonObject = (JsonObject) JsonParser.parseString(returnText) ;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonObject);
		return jsonObject;
		
	}
	
	public RoomReserve updateRoomReserve(HashMap<String, Object> map) throws ParseException {
		RoomReserve roomReserve = null;
		if(map.containsKey("val") && !map.get("val").equals("null")) {
			Integer result = roomDao.updateMyReserveRoom(map);
			if(result > 0) {
				roomReserve = roomDao.selectMyReserveStatus(map);
			}
		}
		return roomReserve;
	}
	
	public String roomReserveParser(List<RoomReserve> list) {
		String myRoomJson = "";
		if(null != list) {
			for(RoomReserve myRoom : list) {
				String str = gson.toJson(myRoom);
				str = str.substring(1, str.length()-1);
				myRoomJson += "/" + str;
			}
		}
		return myRoomJson;
	}
	
	public String ObjJsonParser(Object obj) {
		return gson.toJson(obj);
	}
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Object> strJsonParser(String str) throws ParseException{
		JSONParser parser = new JSONParser();
		return (HashMap<String, Object>) parser.parse(str);
	} 
	
	public JsonArray parsingHsArr(JsonObject beforeObj, String key) {
		return beforeObj.getAsJsonArray(key); //hs에서 key의 value값 꺼내기 - arr버전
	}
	
	public JsonObject parsingHsHs(JsonObject beforeObj, String key) {
		return beforeObj.getAsJsonObject(key); //hs에서 key의 value값 꺼내기 - hs버전
	}
	
	public JsonObject parsingArrHs(JsonArray beforeObj, int key) {
		return (JsonObject) beforeObj.get(key); //배열에서 key 번째 hs 꺼내기
	}
	
	public JsonArray parsingArrArr(JsonArray beforeObj, int key) {
		return (JsonArray) beforeObj.get(key); // 배열에서 key 번째 arr 꺼내기
	}
	
	public JsonArray parsingArrStr(JsonArray beforeObj, int key) {
		return (JsonArray) beforeObj.get(key); // 배열에서 key 번째 arr 번째 value값 string으로 꺼내기
	}
	
	public String parsingHsStr(JsonObject beforeObj, String key) {
		return beforeObj.get(key).toString(); // hs에서 key의 value값 string으로 꺼내기
	}
	
	public String strSubString(Object obj) {
		String str = obj.toString();
		str = str.substring(1, str.length()-1);
		return str;
	}
	
}
