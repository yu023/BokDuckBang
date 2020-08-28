package bokduckbang.member;

import org.springframework.stereotype.Component;

@Component
public class MemberLessee {
	
	private Boolean member_type = true;
	private String member_email;
	private String member_password;
	private String member_repassword;
	private String member_phone;
	private String member_name;
	private String member_like_room_type;
	private String member_dest_loc;
	private Double member_dest_lat;
	private Double member_dest_lng;
	private String member_like_room_num;
	
	public Boolean getMember_type() {
		return member_type;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getMember_password() {
		return member_password;
	}
	public void setMember_password(String member_password) {
		this.member_password = member_password;
	}
	public String getMember_repassword() {
		return member_repassword;
	}
	public void setMember_repassword(String member_repassword) {
		this.member_repassword = member_repassword;
	}
	public String getMember_phone() {
		return member_phone;
	}
	public void setMember_phone(String member_phone) {
		this.member_phone = member_phone;
	}
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	public String getMember_like_room_type() {
		return member_like_room_type;
	}
	public void setMember_like_room_type(String member_like_room_type) {
		this.member_like_room_type = member_like_room_type;
	}
	public String getMember_dest_loc() {
		return member_dest_loc;
	}
	public void setMember_dest_loc(String member_dest_loc) {
		this.member_dest_loc = member_dest_loc;
	}
	public Double getMember_dest_lat() {
		return member_dest_lat;
	}
	public void setMember_dest_lat(Double member_dest_lat) {
		this.member_dest_lat = member_dest_lat;
	}
	public Double getMember_dest_lng() {
		return member_dest_lng;
	}
	public void setMember_dest_lng(Double member_dest_lng) {
		this.member_dest_lng = member_dest_lng;
	}
	public String getMember_like_room_num() {
		return member_like_room_num;
	}
	public void setMember_like_room_num(String member_like_room_num) {
		this.member_like_room_num = member_like_room_num;
	}
	
	
}
