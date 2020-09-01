package bokduckbang.dao;

import java.util.HashMap;
import java.util.List;

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

	public void insertLessor(MemberLessor lessor, HashMap<String, Object> member) {
		sqlSession.insert("MemberMapper.insertMember" , member);
		sqlSession.insert("MemberMapper.insertLessor" , lessor);
	}
	
	public void insertLessee(MemberLessee lessee, HashMap<String, Object> member) {
		sqlSession.insert("MemberMapper.insertMember" , member);
		sqlSession.insert("MemberMapper.insertLessee" , lessee);
	}
	
	public Integer updateMemberInfo(HashMap<String, Object> memberMap) {
		sqlSession.update("MemberMapper.updateMember", memberMap);
		return sqlSession.update("MemberMapper.updateMemberInfo", memberMap);
	}
	
	public Integer insertLikes(HashMap<String, Object> likesMap) {
		return sqlSession.insert("MemberMapper.insertLikes", likesMap);
	}
	
	public MemberLessee getMemberLessee(String email) {
		return sqlSession.selectOne("MemberMapper.getMemberLessee", email);
	}
	
	public MemberLessor getMemberLessor(String email) {
		return sqlSession.selectOne("MemberMapper.getMemberLessor", email);
	}
	
	public void updateMemberLessee(MemberLessee lessee,  HashMap<String, String> member) {
		sqlSession.update("MemberMapper.updateMember" , member);
		sqlSession.update("MemberMapper.updateMemberLessee", lessee);
	}
	
	public void updateMemberLessor(MemberLessor lessor,  HashMap<String, String> member) {
		sqlSession.update("MemberMapper.updateMember" , member);
		sqlSession.update("MemberMapper.updateMemberLessor", lessor);
	}
	
	public Object checkLikes(HashMap<String, Object> map) {
		return sqlSession.selectOne("MemberMapper.checkLikes" , map);
	}
	
	public Object deleteLikes(HashMap<String, Object> map) {
		return sqlSession.update("MemberMapper.deleteLikes" , map);
	}
	
	public String selectMember(HashMap<String, Object> map){
		return sqlSession.selectOne("MemberMapper.selectMember" , map);
	}
	
	public Object checkId(HashMap<String, String> map) {
		return sqlSession.selectOne("MemberMapper.checkId" , map);
	}
	
	public Object businessLicenseChecker(HashMap<String, String> map) {
		return sqlSession.selectOne("MemberMapper.businessLicenseChecker" , map);
	}
	
	public Member checkMember(CheckMember checkMember) {
		 return (Member)sqlSession.selectOne("MemberMapper.checkMember" , checkMember);
	}
	
	public List<Integer> getLikes(String memberEmail) {
		return sqlSession.selectList("MemberMapper.getLikes" , memberEmail);
	}
}
