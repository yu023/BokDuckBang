package bokduckbang.service;

import java.util.HashMap;

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
		memberDao.insertMember(lessor);
		memberDao.insertLessor(lessor);
	}
	
	@Transactional
	public void lesseeInsert(MemberLessee lessee) {
		memberDao.insertMember(lessee);
		memberDao.insertLessee(lessee);
	}
	
	public Boolean idChecker(HashMap<String, String> map) {
		Object obj = memberDao.checkId(map);
		System.out.println(obj);
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
}
