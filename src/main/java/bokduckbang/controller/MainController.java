package bokduckbang.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import bokduckbang.room.RoomReserve;
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
	
	@Autowired
	HttpSession session;
	
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
		return memberService.editLesseeInfo(lessee, roomService, commonService.getMyKey(), session);
	}
	
	@RequestMapping("/joinLessor")
	public String joinProcessLessor(MemberLessor lessor, RoomService roomService) throws ParseException {
		return memberService.editLessorInfo(lessor, roomService, commonService.getMyKey(), session);
	}
	
	
	@RequestMapping("/room-likes-list")
	public String roomRecommend() {
		if(session != null && session.getAttribute("member") != null) {
			return "room/room-likes";
		}else {
			return "member/login";
		}
	}
	
	@RequestMapping("/set-room-likes")
	@ResponseBody
	public HashMap<String, Object> setRoomLikes() {
		return roomService.setLikesRoom(memberService.getLikeList(session));
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
	public String logout() {
		session.invalidate();
		return "index";
	}
	
	@RequestMapping("/loginProcess")
	public String loginProcess(CheckMember checkMember) {
		return memberService.returnLoginUri(session, checkMember);
	}
	
	@RequestMapping("/findIdPw")
	public String findIdPw() {
		return "member/findIdPw";
	}
	
	@RequestMapping("/findId")
	@ResponseBody
	public String findId(@RequestBody HashMap<String, Object> map) {
		return memberService.findMemberId(map);
	}
	
	@RequestMapping("/findPw")
	@ResponseBody
	public HashMap<String, Object> findPw(@RequestBody HashMap<String, Object> map) {
		return memberService.sendMailPw(map);
	}
	
	@RequestMapping("/write-my-room")
	public String writeMyRoom() {
		if(null != session && null != session.getAttribute("member")) {
			return "room/write-my-room";
		}else {
			return "member/login";
		}
	}
	
	@RequestMapping("/my-webSocket-list")
	@ResponseBody
	public String myWebSocketList(HttpSession session, @RequestBody HashMap<String,Object> map) throws ParseException {
//		return roomService.checkReserveDetailRoom(session, map);
		return "메롱";
	}
	
	@RequestMapping("/update-my-room")
	@ResponseBody
	public Integer updateMyRoom(Room room, @RequestParam Integer num) throws ParseException {
		return roomService.updateRoom(room, session, commonService.getMyKey(), num);
	}
	
	@RequestMapping("/add-my-room")
	@ResponseBody
	public Integer addMyRoom(Room room) throws ParseException {
		return roomService.setRoom(room, session, commonService.getMyKey());
	}
	
	@RequestMapping("/add-my-room-img")
	@ResponseBody
	public Boolean addMyRoomImg(@RequestBody HashMap<String, Object> map) throws IOException {
		return roomService.BooleanMyRoomImg(map);
	}
	
	@RequestMapping("/my-room-list")
	public String myRoomList() {
		return "room/my-room-list";
	}
	
	@RequestMapping("/stop-selling-my-room")
	@ResponseBody
	public Room stopSellingMyRoom(@RequestParam Integer num) {
		return roomService.changeSellingType(num);
	}
	
	@RequestMapping("/set-my-room")
	@ResponseBody
	public HashMap<String, Object> setMyRoom() throws UnsupportedEncodingException {
		HashMap<String, Object> map = roomService.setMyRoom(session);
		return map;
	}
	
	@RequestMapping("/businessLicenseChecker")
	@ResponseBody
	public HashMap<String, String> businessLicenseChecker(@RequestBody HashMap<String, String> licenseMap) {
		Boolean chk = memberService.businessLicenseChecker(licenseMap);
		if(chk) {
			licenseMap.put("rst", "true");
			licenseMap.put("chkResult", "사용 가능한 사업자 번호입니다");
		}else {
			licenseMap.put("rst", "false");
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
	public String roomDetail(Model model, @RequestParam int num) throws ParseException, UnsupportedEncodingException {
		model.addAttribute("key", commonService.getMyKey());
		roomService.returnRoomDetail(num, model, session, memberService);
		return "room/room-detail";
	}

	@RequestMapping("/edit-my-room")
	public String editMyRoom(@RequestParam Integer roomNumber, Model model) throws IOException {
		roomService.editMyRoom(roomNumber, model);
		return "room/write-my-room";
	}
	
	@RequestMapping("/my-room-reservation")
	public String myRoomReservationList(){
		return "room/my-room-reservation";
	}
	
	@RequestMapping("/room-likes")
	@ResponseBody
	public Boolean roomLikes(Model model, @RequestBody HashMap<String, Object> map) throws ParseException {
		return memberService.makeLikes(session, map, roomService);
	}
	
	@RequestMapping("/room-delete")
	@ResponseBody
	public Boolean roomDelete(Integer num) throws ParseException {
		return roomService.deleteMyRoom(num);
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
		return roomService.filter(memberService, session, filter);
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
