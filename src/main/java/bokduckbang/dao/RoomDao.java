package bokduckbang.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bokduckbang.room.Room;
import bokduckbang.room.MinMax;

@Repository
public class RoomDao {
	
	@Autowired
	SqlSession sqlSession;
	
	static List<Object> roomList = null;
	
	public void insertRoom(Room room) {
		sqlSession.insert("RoomMapper.roomInsert", room);
	}
	
	public int dupChkRoom(Room room) {
		List<Room> obj = sqlSession.selectList("RoomMapper.dupChkRoom", room);
		if(obj.size() == 0) {
			return 0;
		}else {
			return 1;
		}
	}
	
	public List<Room> selectRoom(HashMap<String, Object> map){
		return sqlSession.selectList("RoomMapper.SelectRoom", map);
	}
	
	public List<MinMax> selectMoney(HashMap<String, Object> map){
		return sqlSession.selectList("RoomMapper.SelectMoney", map);
	}
	

}
