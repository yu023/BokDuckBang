package bokduckbang.room;

import java.util.List;

public class RoomFilterVo {
	private String centerLat;
	private String centerLng;
	private String distance;
	private List<String> keyword;
	private String maxdeposit;
	private String maxlease;
	private String maxrent;
	private String mindeposit;
	private String minlease;
	private String minrent;
	private List<String> range;
	private String room_selling_type;
	private String type;
	private String key;
	
	public Double getCenterLat() {
		return Double.parseDouble(centerLat);
	}
	public void setCenterLat(String centerLat) {
		this.centerLat = centerLat;
	}
	public Double getCenterLng() {
		return  Double.parseDouble(centerLng);
	}
	public void setCenterLng(String centerLng) {
		this.centerLng = centerLng;
	}
	public Double getDistance() {
		return  Double.parseDouble(distance);
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public List<String> getKeyword() {
		return keyword;
	}
	public void setKeyword(List<String> keyword) {
		this.keyword = keyword;
	}
	public Double getMaxdeposit() {
		return  Double.parseDouble(maxdeposit);
	}
	public void setMaxdeposit(String maxdeposit) {
		this.maxdeposit = maxdeposit;
	}
	public Double getMaxlease() {
		return  Double.parseDouble(maxlease);
	}
	public void setMaxlease(String maxlease) {
		this.maxlease = maxlease;
	}
	public Double getMaxrent() {
		return  Double.parseDouble(maxrent);
	}
	public void setMaxrent(String maxrent) {
		this.maxrent = maxrent;
	}
	public Double getMindeposit() {
		return  Double.parseDouble(mindeposit);
	}
	public void setMindeposit(String mindeposit) {
		this.mindeposit = mindeposit;
	}
	public Double getMinlease() {
		return  Double.parseDouble(minlease);
	}
	public void setMinlease(String minlease) {
		this.minlease = minlease;
	}
	public Double getMinrent() {
		return  Double.parseDouble(minrent);
	}
	public void setMinrent(String minrent) {
		this.minrent = minrent;
	}
	public List<String> getRange() {
		return range;
	}
	public void setRange(List<String> range) {
		this.range = range;
	}
	public String getRoom_selling_type() {
		return room_selling_type;
	}
	public void setRoom_selling_type(String room_selling_type) {
		this.room_selling_type = room_selling_type;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
}
