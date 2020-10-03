package bokduckbang.mySocket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import bokduckbang.member.Member;
import bokduckbang.room.RoomReserve;
import bokduckbang.service.MemberService;
import bokduckbang.service.RoomService;

public class Handler extends BinaryWebSocketHandler {
	
	@Autowired
	MemberService memberService;
	
	@Autowired
	RoomService roomService;
	
	private static List<HashMap<String, WebSocketSession>> sessionList = new ArrayList<HashMap<String, WebSocketSession>>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Member member = memberService.returnMember(session);
		HashMap<String, WebSocketSession> sessionMap = new HashMap<String, WebSocketSession>();
		sessionMap.put(member.getMember_email(), session);
		sessionList.add(sessionMap);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) {
		Member member = memberService.returnMember(session);
		String msgStr = message.getPayload();
		
		try {
			
			if(msgStr.contains(",")) {
				HashMap<String, Object> myJsonMap = roomService.strJsonParser(msgStr);
				if(myJsonMap.containsKey("val") && !myJsonMap.get("val").equals("null") ){
					RoomReserve roomReserve = roomService.updateRoomReserve(myJsonMap);
					String roomReserveStr = roomService.ObjJsonParser(roomReserve);
					if(null != roomReserve) {
						String member_email = roomReserve.getReserve_member_email();
						for(HashMap<String, WebSocketSession> myMap : sessionList) {
							if(myMap.containsKey(member_email)) {
								WebSocketSession lesseeSession = myMap.get(member_email);
								lesseeSession.sendMessage(new TextMessage(roomReserveStr));
							}
						}
						session.sendMessage(new TextMessage(roomReserveStr));
					}
				}else if(myJsonMap.containsKey("val") && myJsonMap.get("val").equals("null") ){
					RoomReserve resultRoomReserve = roomService.reserveRoom(myJsonMap);
					String roomReserveStr = roomService.ObjJsonParser(resultRoomReserve);
					session.sendMessage(new TextMessage(roomReserveStr));
					if(null != resultRoomReserve) {
						String author_member_email = resultRoomReserve.getReserve_room_author_email();
						for(HashMap<String, WebSocketSession> myMap : sessionList) {
							if(myMap.containsKey(author_member_email)) {
								WebSocketSession lesseeSession = myMap.get(author_member_email);
								lesseeSession.sendMessage(new TextMessage(roomReserveStr));
							}
						}
						session.sendMessage(new TextMessage(roomReserveStr));
					}
				}else {
				}
			}
			
			if(msgStr.equals("lessor")) {
				List<RoomReserve> list = roomService.returnReserveList(member, null);
				String myRoomReserveList = roomService.roomReserveParser(list);
				session.sendMessage(new TextMessage(myRoomReserveList));
			}
			
			if(msgStr.contains("lessee:")) {
				String[] roomNum = msgStr.split(":");
				List<RoomReserve> list = roomService.returnReserveList(member, Integer.valueOf(roomNum[1]));
				String myRoomReserveList = roomService.roomReserveParser(list);
				session.sendMessage(new TextMessage(myRoomReserveList));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Member member = memberService.returnMember(session);
		for(int i = 0 ; i < sessionList.size(); i++) {
			if(null != sessionList.get(i).get(member.getMember_email())) {
				sessionList.remove(i);
			}
		}
	}
	
}