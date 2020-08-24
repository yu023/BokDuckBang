package bokduckbang.dao;

import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bokduckbang.member.CheckMember;
import bokduckbang.member.Member;
import bokduckbang.member.MemberLessee;
import bokduckbang.member.MemberLessor;

@Repository
public class MemberDao {

	@Autowired
	SqlSession sqlSession;
	
	public void insertMember(MemberLessor lessor) {
		sqlSession.insert("MemberMapper.insertMember" , lessor);
	}
	
	public void insertMember(MemberLessee lessee) {
		sqlSession.insert("MemberMapper.insertMember" , lessee);
	}
	
	public void insertLessor(MemberLessor lessor) {
		sqlSession.insert("MemberMapper.insertLessor" , lessor);
	}
	
	public void insertLessee(MemberLessee lessee) {
		sqlSession.insert("MemberMapper.insertLessee" , lessee);
	}
	
	public Object checkId(HashMap<String, String> map) {
		return sqlSession.selectOne("MemberMapper.checkId" , map);
	}
	
	public Member checkMember(CheckMember checkMember) {
		 return (Member)sqlSession.selectOne("MemberMapper.checkMember" , checkMember);
	}
}
