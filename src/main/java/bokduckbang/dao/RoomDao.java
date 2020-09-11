package bokduckbang.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import bokduckbang.room.Room;
import bokduckbang.room.RoomImg;
import bokduckbang.room.RoomReserve;
import bokduckbang.room.MinMax;

@Repository
public class RoomDao {
	
	@Autowired
	SqlSession sqlSession;
	
	static List<Object> roomList = null;
	
	public void insertRoom(Room room) {
		sqlSession.insert("RoomMapper.roomInsert", room);
	}
	
	public Integer insertRoomImg(HashMap<String, Object> map) {
		return sqlSession.insert("RoomMapper.insertRoomImg", map);
	}
	
	public Integer insertRoomReserve(HashMap<String,Object> roomReserveMap) {
		return sqlSession.insert("RoomMapper.insertRoomReserve", roomReserveMap);
	}
	
	public void updateRoom(Room room) {
		sqlSession.update("RoomMapper.roomUpdate", room);
	}

	public Integer updateMyReserveRoom(HashMap<String, Object> map) {
		return sqlSession.update("RoomMapper.updateMyReserveRoom", map);
	}
	
	public RoomReserve selectMyReserveDetail(HashMap<String, Object> map) {
		return sqlSession.selectOne("RoomMapper.selectMyReserveDetail", map);
	}
	
	public RoomReserve selectMyReserveStatus(HashMap<String, Object> map) {
		return sqlSession.selectOne("RoomMapper.selectMyReserveStatus", map);
	}
	
	public List<RoomReserve> selectMyReserveRoom(HashMap<String, Object> map) {
		return sqlSession.selectList("RoomMapper.selectMyReserveRoom", map);
	}
	
	public List<Integer> dupChkRoom(Room room) {
		List<Integer> obj = sqlSession.selectList("RoomMapper.dupChkRoom", room);
		if(obj.size() == 0) {
			return null;
		}else {
			return obj;
		}
	}
	
	public List<RoomImg> selectOneRoomImg(Integer roomNum) {
		List<RoomImg> obj = sqlSession.selectList("RoomMapper.selectOneRoomImg", roomNum);
		if(obj.size() == 0) {
			return null;
		}else {
			return obj;
		}
	}
	
	public List<RoomImg> selectRoomImg(List<Room> room) {
		List<RoomImg> obj = sqlSession.selectList("RoomMapper.selectRoomImg", room);
		if(obj.size() == 0) {
			return null;
		}else {
			return obj;
		}
	}
	
	public Integer deleteMyRoom(Integer num) {
		Integer deleteMyRoom =  sqlSession.delete("RoomMapper.deleteMyRoom", num);
		Integer deleteMyRoomImg =  sqlSession.delete("RoomMapper.deleteMyRoomImg", num);
		return deleteMyRoom + deleteMyRoomImg;
	}
	
	public Integer deleteMyRoomImg(Integer num) {
		return sqlSession.delete("RoomMapper.deleteMyRoomImg", num);
	}
	
	public Integer changeSellingType(Integer i) {
		return sqlSession.update("RoomMapper.changeSellingType", i);
	}
	
	public Integer addRoomHits(Integer i) {
		return sqlSession.update("RoomMapper.addRoomHits", i);
	}
	
	public Integer countRoomLikes(HashMap<String, Object> map) {
		return sqlSession.update("RoomMapper.countRoomLikes", map);
	}
	
	public List<Room> selectRoom(HashMap<String, Object> map){
		return sqlSession.selectList("RoomMapper.SelectRoom", map);
	}
	
	public List<MinMax> selectMoney(HashMap<String, Object> map){
		return sqlSession.selectList("RoomMapper.SelectMoney", map);
	}
	

}
