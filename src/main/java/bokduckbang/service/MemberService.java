package bokduckbang.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bokduckbang.dao.MemberDao;
import bokduckbang.member.CheckMember;
import bokduckbang.member.Member;
import bokduckbang.member.MemberLessee;
import bokduckbang.member.MemberLessor;

@Service
public class MemberService {
	
	@Autowired
	MemberDao memberDao;
	
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
		if(session != null && null != session.getAttribute("member")) {
			Member member = (Member)session.getAttribute("member");
			list = memberDao.getLikes(member.getMember_email());
		}
		return list;
	}
}
