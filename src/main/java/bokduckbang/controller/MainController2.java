//package bokduckbang.controller;
//
//import java.io.StringReader;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import java.util.HashMap;
//import java.util.List;
//
//import javax.servlet.http.HttpSession;
//
//import org.json.simple.parser.ParseException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.google.gson.Gson;
//import com.google.gson.JsonParser;
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.stream.JsonReader;
//
//import bokduckbang.member.CheckMember;
//import bokduckbang.member.MemberLessee;
//import bokduckbang.member.MemberLessor;
//import bokduckbang.room.Room;
//import bokduckbang.room.RoomFilterVo;
//import bokduckbang.service.CommonService;
//import bokduckbang.service.MemberService;
//import bokduckbang.service.RoomService;
//import bokduckbang.service.SetMetaData;
//
//@Controller
//public class MainController2 {
//	
//	@Autowired
//	private CommonService commonService;
//	
//	@Autowired
//	private MemberService memberService;
//	
//	@Autowired
//	private SetMetaData setMetadata;
//	
//	@Autowired
//	private RoomService roomService;
//	
//	Gson gson = new Gson();
//	
//	@RequestMapping("/input")
//	public String input() {
//		return "input";
//	}
//	
//	@RequestMapping("/insert")
//	@ResponseBody
//	public Object insert(Model model, @RequestBody HashMap<String, Object> map) {
//		setMetadata.insertRoom(setMetadata.getRoomId(map));
//		map.put("resultMsg", "test");
//		return map;
//	}
//	
//	
//	@RequestMapping("/")
//	public String index() {
//		return "index";
//	}
//
//	@RequestMapping("/select-join-type")
//	public String joinType() {
//		return "select-join-type";
//	}
//	
//	@RequestMapping("/join-lessee")
//	public String joinProcessLessee() {
//		return "join-lessee";
//	}
//	
//	@RequestMapping("/join-lessor")
//	public String joinProcessLessor() {
//		return "join-lessor";
//	}
//	
//	@RequestMapping("/joinLessee")
//	public String joinProcessLessor(MemberLessee lessee) {
//		memberService.lesseeInsert(lessee);
//		return "join-lessor";
//	}
//	
//	@RequestMapping("/joinLessor")
//	public String joinProcessLessor(MemberLessor lessor) {
//		memberService.lessorInsert(lessor);
//		return "join-lessor";
//	}
//	
//	
//	@RequestMapping("/room-recommend")
//	public String roomRecommend() {
//		return "room-recommend";
//	}
//	
//	@RequestMapping("/idChecker")
//	@ResponseBody
//	public HashMap<String, String> idCheker(@RequestBody HashMap<String, String> map) {
//		Boolean chk = memberService.idChecker(map);
//		System.out.println(chk);
//		if(chk) {
//			map.put("chkResult", "사용 가능한 아이디입니다");
//		}else {
//			map.put("chkResult", "이미 존재하는 아이디입니다");
//		}
//		System.out.println(map);
//		return map;
//	}
//	
//	@RequestMapping("/login")
//	public String login() {
//		return "login";
//	}
//	
//	@RequestMapping("/loginProcess")
//	public String loginProcess(CheckMember checkMember, HttpSession session) {
//		HashMap<String, Object> checker = memberService.loginProcess(checkMember);
//		if((Boolean)checker.get("result")) {
//			session.setAttribute("loginUrl", "logout");
//			session.setAttribute("loginMsg", "로그아웃");
//			session.setAttribute("member", checker.get("member"));
//			return "/";
//		}else {
//			session.setAttribute("loginUrl", "login");
//			session.setAttribute("loginMsg", "로그인");
//			return "/login";
//		}
//	}
//	
//	@RequestMapping("/getCenterLatLng")
//	@ResponseBody
//	public List<Room> getCenterLatLng(Model model, @RequestParam HashMap<String, String> map) {
//		System.out.println(map);
//		RoomFilterVo rfv = stringToGson(map);
//		System.out.println(rfv.getRoom_selling_type());
//		List<Room> resultList = roomService.getDistance(rfv);
//		model.addAttribute("rooms", resultList);
//		return resultList;
//	}
//	
//	@RequestMapping("/getFastRoot")
//	@ResponseBody
//	public HashMap<String, Object> getFastRoot(@RequestBody HashMap<String, Object> map) throws ParseException {
//		map.put("key", commonService.getMyKey());
//		return roomService.getFastRoot(map);
//	}
//	
//	
//	@RequestMapping("/search-room")
//	public String searchRoom(HashMap<Object, Object> map) throws ParseException {
//		map.put("key", commonService.getMyKey());
//		return "search-room";
//	}
//	
//	@RequestMapping("/room-detail")
//	public String roomDetail(Model model, @RequestParam int num) throws ParseException {
//		Room room = roomService.roomDetail(num);
//		model.addAttribute("key", commonService.getMyKey());
//		model.addAttribute("room", room);
//		model.addAttribute("roomUrl", roomService.roomImgUrl(room));
//		model.addAttribute("roomKeyword", roomService.roomKeyword(room));
//		model.addAttribute("roomOption", roomService.roomOption(room));
//		return "room-detail";
//	}
//	
//	@RequestMapping("/room-position")
//	@ResponseBody
//	public HashMap<String, Double> roomPosition(@RequestParam int num) throws ParseException {
//		HashMap<String, Double> map = new HashMap<String, Double>();
//		Room room = roomService.roomDetail(num);
//		map.put("myLat", room.getRoom_lat());
//		map.put("myLng", room.getRoom_lng());
//		return map;
//	}
//	
//	@RequestMapping("/get-room-number")
//	@ResponseBody
//	public HashMap<String, List<Room>> getRoomNumber(@RequestBody HashMap<String, Object> points) throws ParseException {
//		HashMap<String, List<Room>> room = new HashMap<String, List<Room>>();
//		room.put("room", roomService.getRoomNumber(points));
//		return room;
//	}
//	
//	@RequestMapping("/filter")
//	@ResponseBody
//	public HashMap<String, Object> filter(@RequestParam HashMap<String, String> filter) throws ParseException {
//		List<Room> rooms = roomService.filter(stringToGson(filter));
//		HashMap<String, Object> newhs = new HashMap<String, Object>();
//		if(rooms != null) {
//			newhs.put("result", rooms);
//			return newhs;
//		}else {
//			newhs.put("result", null);
//			return newhs;
//		}
//	}
//	
//	@RequestMapping("/search-room-get")
//	@ResponseBody
//	public HashMap<String, Object> srg(@RequestParam HashMap<String, String> filter) throws ParseException {
//		HashMap<String, Object> newhs = new HashMap<String, Object>();
//		List<Room> rooms = roomService.filter(stringToGson(filter));
//		if(rooms != null) {
//			newhs.put("result", rooms);
//			return newhs;
//		}else {
//			newhs.put("result", null);
//			return newhs;
//		}
//	}
//	
//	@RequestMapping("/minmax")
//	@ResponseBody
//	public HashMap<String, Integer> minmax(@RequestParam HashMap<String, String> filter) throws ParseException {
//		return roomService.getMoneyRange(stringToGson(filter));
//	}
//	
//	
//	@RequestMapping("/search")
//	@ResponseBody
//	public HashMap<String, Object> search(@RequestParam HashMap<String, String> keyword) throws ParseException {
//		HashMap<String, Object> newhs = new HashMap<String, Object>();
//		RoomFilterVo rfv = stringToGson(keyword);
//		rfv.setKey(commonService.getMyKey());
//		HashMap<String, Object> points = roomService.schRoomList(rfv);
//		List<Room> room = roomService.getDistance(rfv);
//		newhs.put("points", points);
//		newhs.put("room", room);
//		return newhs;
//	}
//	
//	@RequestMapping("/keyword")
//	@ResponseBody
//	public List<String> keyword(@RequestParam HashMap<String, String> keyword) throws ParseException {
//		return roomService.keywordRoomList(stringToGson(keyword));
//	}
//	
//	public RoomFilterVo stringToGson(HashMap<String, String> filterHs) {
//		JsonReader reader = null;
//		String str= "";
//		if(filterHs.containsKey("filter")) {
//			str = gson.toJson(filterHs.get("filter"));
//			reader = new JsonReader(new StringReader(str));
//		}else {
//			str = gson.toJson(filterHs);
//			reader = new JsonReader(new StringReader(str));
//		}
//		reader.setLenient(true);
//		return gson.fromJson(reader, RoomFilterVo.class);
//	}
//	
//}
