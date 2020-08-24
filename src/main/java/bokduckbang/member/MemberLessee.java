package bokduckbang.member;

import org.springframework.stereotype.Component;

@Component
public class MemberLessee {
	
	String member_email;
	String member_password;
	String member_repassword;
	String member_phone;
	String member_name;
	String member_like_room_type;
	String member_dest_loc;
	String member_like_area;
	String member_like_room_num;
	
	public String getMember_dest_loc() {
		return member_dest_loc;
	}
	public void setMember_dest_loc(String member_dest_loc) {
		this.member_dest_loc = member_dest_loc;
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
	public String getMember_like_area() {
		return member_like_area;
	}
	public void setMember_like_area(String member_like_area) {
		this.member_like_area = member_like_area;
	}
	public String getMember_like_room_num() {
		return member_like_room_num;
	}
	public void setMember_like_room_num(String member_like_room_num) {
		this.member_like_room_num = member_like_room_num;
	}
	
}
