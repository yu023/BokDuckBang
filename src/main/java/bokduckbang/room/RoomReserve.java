package bokduckbang.room;

import org.springframework.stereotype.Component;

@Component
public class RoomReserve {
	private Integer reserve_num;
	private String reserve_member_email;
	private String reserve_member_name;
	private String reserve_member_phone;
	private String reserve_room_author_email;
	private Integer reserve_room_number;
	private String reserve_room_title;
	private String reserve_status;
	
	public Integer getReserve_num() {
		return reserve_num;
	}
	public void setReserve_num(Integer reserve_num) {
		this.reserve_num = reserve_num;
	}
	public String getReserve_member_email() {
		return reserve_member_email;
	}
	public void setReserve_member_email(String reserve_member_email) {
		this.reserve_member_email = reserve_member_email;
	}
	public String getReserve_member_name() {
		return reserve_member_name;
	}
	public void setReserve_member_name(String reserve_member_name) {
		this.reserve_member_name = reserve_member_name;
	}
	public String getReserve_member_phone() {
		return reserve_member_phone;
	}
	public void setReserve_member_phone(String reserve_member_phone) {
		this.reserve_member_phone = reserve_member_phone;
	}
	public String getReserve_room_author_email() {
		return reserve_room_author_email;
	}
	public void setReserve_room_author_email(String reserve_room_author_email) {
		this.reserve_room_author_email = reserve_room_author_email;
	}
	public Integer getReserve_room_number() {
		return reserve_room_number;
	}
	public void setReserve_room_number(Integer reserve_room_number) {
		this.reserve_room_number = reserve_room_number;
	}
	public String getReserve_room_title() {
		return reserve_room_title;
	}
	public void setReserve_room_title(String reserve_room_title) {
		this.reserve_room_title = reserve_room_title;
	}
	public String getReserve_status() {
		return reserve_status;
	}
	public void setReserve_status(String reserve_status) {
		this.reserve_status = reserve_status;
	}
}
