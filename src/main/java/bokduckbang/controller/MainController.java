package bokduckbang.controller;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import bokduckbang.member.CheckMember;
import bokduckbang.member.MemberLessee;
import bokduckbang.member.MemberLessor;
import bokduckbang.room.Room;
import bokduckbang.service.CommonService;
import bokduckbang.service.MemberService;
import bokduckbang.service.RoomService;
import bokduckbang.service.SetMetaData;

@Controller
public class MainController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SetMetaData setMetadata;
	
	@Autowired
	private RoomService roomService;
	
	@RequestMapping("/input")
	public String input() {
		return "input";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public Object insert(Model model, @RequestBody HashMap<String, Object> map) {
		setMetadata.insertRoom(setMetadata.getRoomId(map));
		map.put("resultMsg", "test");
		return map;
	}
	
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/select-join-type")
	public String joinType() {
		return "member/select-join-type";
	}
	
	@RequestMapping("/join-lessee")
	public String joinProcessLessee() {
		return "member/join-lessee";
	}
	
	@RequestMapping("/join-lessor")
	public String joinProcessLessor() {
		return "member/join-lessor";
	}
	
	@RequestMapping("/joinLessee")
	public String joinProcessLessor(MemberLessee lessee, RoomService roomService) throws ParseException {
		memberService.lesseeInsert(lessee, roomService, commonService.getMyKey());
		return "member/join-finish";
	}
	
	@RequestMapping("/joinLessor")
	public String joinProcessLessor(MemberLessor lessor) {
		memberService.lessorInsert(lessor);
		return "member/join-finish";
	}
	
	
	@RequestMapping("/room-recommend")
	public String roomRecommend() {
		return "room/room-recommend";
	}
	
	@RequestMapping("/idChecker")
	@ResponseBody
	public HashMap<String, String> idCheker(@RequestBody HashMap<String, String> map) {
		Boolean chk = memberService.idChecker(map);
		if(chk) {
			map.put("chkResult", "사용 가능한 아이디입니다");
		}else {
			map.put("chkResult", "이미 존재하는 아이디입니다");
		}
		return map;
	}
	
	@RequestMapping("/login")
	public String login() {
		return "member/login";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@RequestMapping("/loginProcess")
	public String loginProcess(CheckMember checkMember, HttpSession session) {
		HashMap<String, Object> checker = memberService.loginProcess(checkMember);
		if((Boolean)checker.get("result")) {
			session.setAttribute("member", checker.get("member"));
			return "/index";
		}else {
			session.setAttribute("email", checkMember.getMember_email());
			return "member/login";
		}
	}
	
	@RequestMapping("/businessLicenseChecker")
	@ResponseBody
	public HashMap<String, String> businessLicenseChecker(@RequestBody HashMap<String, String> licenseMap) {
		Boolean chk = memberService.businessLicenseChecker(licenseMap);
		if(chk) {
			licenseMap.put("chkResult", "사용 가능한 사업자 번호입니다");
		}else {
			licenseMap.put("chkResult", "이미 존재하는 사업자 번호입니다");
		}
		return licenseMap;
	}
	
	@RequestMapping("/getCenterLatLng")
	@ResponseBody
	public List<Room> getCenterLatLng(Model model, @RequestBody HashMap<String,Object> map) {
		List<Room> resultList = roomService.getDistance(map);
		model.addAttribute("rooms", resultList);
		return resultList;
	}
	
	@RequestMapping("/getFastRoot")
	@ResponseBody
	public HashMap<String, Object> getFastRoot(@RequestBody HashMap<String, Object> map) throws ParseException {
		map.put("key", commonService.getMyKey());
		return roomService.getFastRoot(map);
	}
	
	
	@RequestMapping("/search-room")
	public String searchRoom(HashMap<Object, Object> map) throws ParseException {
		map.put("key", commonService.getMyKey());
		return "room/search-room";
	}
	
	@RequestMapping("/room-detail")
	public String roomDetail(Model model, @RequestParam int num) throws ParseException {
		Room room = roomService.roomDetail(num);
		model.addAttribute("key", commonService.getMyKey());
		model.addAttribute("room", room);
		model.addAttribute("roomUrl", roomService.roomImgUrl(room));
		model.addAttribute("roomKeyword", roomService.roomKeyword(room));
		model.addAttribute("roomOption", roomService.roomOption(room));
		model.addAttribute("like", 10);
		return "room/room-detail";
	}
	
	
	@RequestMapping("/room-position")
	@ResponseBody
	public HashMap<String, Double> roomPosition(@RequestParam int num) throws ParseException {
		HashMap<String, Double> map = new HashMap<String, Double>();
		Room room = roomService.roomDetail(num);
		map.put("myLat", room.getRoom_lat());
		map.put("myLng", room.getRoom_lng());
		return map;
	}
	
	@RequestMapping("/get-room-number")
	@ResponseBody
	public HashMap<String, List<Room>> getRoomNumber(@RequestBody HashMap<String, Object> points) throws ParseException {
		HashMap<String, List<Room>> room = new HashMap<String, List<Room>>();
		room.put("room", roomService.getRoomNumber(points));
		return room;
	}
	
	@RequestMapping("/filter")
	@ResponseBody
	public HashMap<String, Object> filter(@RequestBody HashMap<String, Object> filter) throws ParseException {
		List<Room> rooms = roomService.filter(filter);
		HashMap<String, Object> newhs = new HashMap<String, Object>();
		if(rooms != null) {
			newhs.put("result", rooms);
			return newhs;
		}else {
			newhs.put("result", null);
			return newhs;
		}
	}
	
	
	@RequestMapping("/minmax")
	@ResponseBody
	public HashMap<String, Integer> minmax(@RequestBody HashMap<String, Object> filter) throws ParseException {
		return roomService.getMoneyRange(filter);
	}
	
	
	@RequestMapping("/search")
	@ResponseBody
	public HashMap<String, Object> search(@RequestBody HashMap<String, Object> keyword) throws ParseException {
		keyword.put("key", commonService.getMyKey());
		HashMap<String, Object> points = roomService.schRoomList(keyword);
		List<Room> room = roomService.getDistance(points);
		keyword.remove("key");
		keyword.put("points", points);
		keyword.put("room", room);
		return keyword; 
	}
	
	@RequestMapping("/keyword")
	@ResponseBody
	public List<String> keyword(@RequestBody HashMap<String, Object> keyword) throws ParseException {
		return roomService.keywordRoomList(keyword);
	}
	
	
}
