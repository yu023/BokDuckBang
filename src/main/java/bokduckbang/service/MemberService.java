package bokduckbang.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import bokduckbang.dao.MemberDao;
import bokduckbang.member.CheckMember;
import bokduckbang.member.Member;
import bokduckbang.member.MemberLessee;
import bokduckbang.member.MemberLessor;
import bokduckbang.member.mail.ResetPw;
import bokduckbang.member.mail.SendMail;

@Service
public class MemberService {
	
	@Autowired
	MemberDao memberDao;
	
	@Autowired
	SendMail sendMail;
	
	@Transactional
	public void lessorInsert(MemberLessor lessor) {
		HashMap<String, Object> member = new HashMap<String, Object>();
		member.put("member_email", lessor.getMember_email());
		member.put("member_type", lessor.getMember_type()); 
		member.put("member_password", lessor.getMember_password()); 
		memberDao.insertLessor(lessor,member);
	}
	
	@Transactional
	public void lesseeInsert(MemberLessee lessee, RoomService roomService, String key) {
		
		HashMap<String, Object> getPoints = new HashMap<String,Object>();
		getPoints.put("key", key);
		getPoints.put("keyword", lessee.getMember_dest_loc());
		
		getPoints = roomService.schRoomList(getPoints);
		
		lessee.setMember_dest_lat(Double.valueOf(getPoints.get("centerLat").toString()));
		lessee.setMember_dest_lng(Double.valueOf(getPoints.get("centerLng").toString()));
		
		getPoints.put("member_email", lessee.getMember_email());
		getPoints.put("member_type", lessee.getMember_type());
		getPoints.put("member_password", lessee.getMember_password()); 
		
		memberDao.insertLessee(lessee, getPoints);
	}
	
	public String editLesseeInfo(MemberLessee lessee, RoomService roomService, String key, HttpSession session) {
		String uri = "member/join-finish";
		
		if(null != session && null != session.getAttribute("member")) {
			
			HashMap<String, Object> searchLoc = new HashMap<String, Object>();
			searchLoc.put("keyword", lessee.getMember_dest_loc());
			searchLoc.put("key", key);
			searchLoc = roomService.schRoomList(searchLoc);
			lessee.setMember_dest_lat((Double)searchLoc.get("centerLat"));
			lessee.setMember_dest_lng((Double)searchLoc.get("centerLng"));

			HashMap<String, String> memberMap = new HashMap<String, String>();
			memberMap.put("member_email",lessee.getMember_email());
			memberMap.put("member_password",lessee.getMember_password());
			
			memberDao.updateMemberLessee(lessee, memberMap);
			session.setAttribute("memberInfo", getMyInfo(session));

			uri = "member/edit-finish";
		}else {
			lesseeInsert(lessee, roomService, key);
		}
		return uri;
	}
	
	public String editLessorInfo(MemberLessor lessor, RoomService roomService, String key, HttpSession session) {
		String uri = "member/join-finish";
		if(null != session && null != session.getAttribute("member")) {
			HashMap<String, String> memberMap = new HashMap<String, String>();
			memberMap.put("member_email",lessor.getMember_email());
			memberMap.put("member_password",lessor.getMember_password());
			memberDao.updateMemberLessor(lessor, memberMap);
			session.setAttribute("memberInfo", getMyInfo(session));
			uri = "member/edit-finish";
		}else {
			lessorInsert(lessor);
		}
		return uri;
	}
	
	public Object getMyInfo(HttpSession session) {
		Object member = null;
		if(session != null) {
			Member sessionMember = (Member) session.getAttribute("member");
			String type = sessionMember.getMember_type();
			String email = sessionMember.getMember_email();
			if(type.equals("0")) {
				member = memberDao.getMemberLessor(email);
			}else if(type.equals("1")) {
				member = memberDao.getMemberLessee(email);
			}
		}
		return member;
	}
	
	public Boolean checkLikes(HashMap<String, Object> likesMap) {
		if(null == memberDao.checkLikes(likesMap)) {
			return false;
		}else {
			return true;
		}
	}

	public Boolean makeLikes(HttpSession session, HashMap<String, Object> likesMap, RoomService roomService) {
		Boolean result = null;
		if(null != session && null != session.getAttribute("member")) {
			Member member = (Member) session.getAttribute("member");
			likesMap.put("likes_member_id", member.getMember_email());
			if(!checkLikes(likesMap)) {
				memberDao.insertLikes(likesMap);
				likesMap.put("count", true);
				roomService.countRoomLikes(likesMap);
				result = true;
			}else {
				memberDao.deleteLikes(likesMap);
				likesMap.put("count", false);
				roomService.countRoomLikes(likesMap);
				result = false;
			}
		}
		return result;
	}
	
	public String findMemberId(HashMap<String, Object> map) {
		String phone = map.get("member_phone").toString();
		if(phone.contains("-")) {
			String[] phnArr = phone.split("-");
			phone = phnArr[0] + phnArr[1] + phnArr[2];
		}
		map.put("member_phone", phone);
		return memberDao.selectMember(map);
	}
	
	public HashMap<String, Object> sendMailPw(HashMap<String, Object> map) {
		
		map.put("resultMsg", "비밀번호 발송에 실패하였습니다.");
		if(map.containsKey("member_name") && map.containsKey("member_email")) {
			
			String name = map.get("member_name").toString();
			String email = map.get("member_email").toString();
			
			HashMap<String, String> chkIdMap = new HashMap<String, String>();
			chkIdMap.put("id", email);
			
			if(memberDao.checkId(chkIdMap) != null) {
				Session session = sendMail.sendMail();
				String user = sendMail.user;
				try {
					
					MimeMessage message = new MimeMessage(session);
					String resetPw = ResetPw.randomPw();
					
					message.setFrom(new InternetAddress(user));
					message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
					message.setHeader("Content-Type", "text/html; charset=UTF-8");
					message.setSubject(MimeUtility.encodeText(name + "님 임시 비밀번호 발송드립니다.", "UTF-8", "B"));
					message.setContent(name + " 님의 임시 비밀번호는 " + resetPw + " 입니다. (대소문자구분)",
							"text/html; charset=UTF-8");
					
					map.put("member_password", resetPw);
					Integer updateResult = memberDao.updateMemberInfo(map);
					if(updateResult > 0) {
						Transport.send(message);
						map.put("resultMsg", "임시비밀번호를 가입하신 메일로 발급하였습니다. \n 메일함에 없을 경우 스팸함을 확인해주세요.");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else {
				map.put("resultMsg", "가입된 이메일이 아닙니다.");
			}
		}
		return map;
	}
	
	public String returnLoginUri(HttpSession session, CheckMember checkMember) {
		String uri = "";
		HashMap<String, Object> checker = loginProcess(checkMember);
		if((Boolean)checker.get("result")) {
			if(null != session  && null != session.getAttribute("member")) {
				Member m = (Member) session.getAttribute("member");
				session.setAttribute("memberInfo", getMyInfo(session));
				if(m.getMember_type().equals("0")) {
					uri = "member/join-lessor";
				}else if(m.getMember_type().equals("1")) {
					uri = "member/join-lessee";
				}
			}else {
				session.setAttribute("member", checker.get("member"));
				session.setAttribute("memberInfo", getMyInfo(session));
				uri = "/index";
			}
		}else {
			session.setAttribute("email", checkMember.getMember_email());
			uri = "member/login";
		}
		return uri;
	}
	
	public Member returnMember(WebSocketSession session) {
		Map<String, Object> map = session.getAttributes();
		return (Member) map.get("member");
	}
	
	public Boolean businessLicenseChecker(HashMap<String, String> map) {
		Object obj = memberDao.businessLicenseChecker(map);
		if(obj != null) {
			return false;
		}else {
			return true;
		}
	}
	
	public Boolean idChecker(HashMap<String, String> map) {
		Object obj = memberDao.checkId(map);
		if(obj != null) {
			return false;
		}else {
			return true;
		}
	}
	
	
	public HashMap<String, Object> loginProcess(CheckMember checkMember) {
		HashMap<String, Object> checker = new HashMap<String,Object>();
		if(checkMember.getMember_email() != null && checkMember.getMember_password() != null) {
			Member member = memberDao.checkMember(checkMember);
			if(member == null) {
				checker.put("result", false);
				checker.put("msg", "아이디, 패스워드가 일치하지 않습니다.");
			}else {
				checker.put("result", true);
				checker.put("member", member);
			}
		}else {
			checker.put("result", false);
			checker.put("msg", "아이디, 패스워드를 모두 입력하여 주십시오.");
		}
		return checker;
	}
	
	public List<Integer> getLikeList(HttpSession session) {
		List<Integer> list = null;
		if(null != session && null != session.getAttribute("member")) {
			Member member = (Member)session.getAttribute("member");
			list = memberDao.getLikes(member.getMember_email());
		}
		return list;
	}
}
