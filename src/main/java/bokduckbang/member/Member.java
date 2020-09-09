package bokduckbang.member;

public class Member {
	private Integer member_number;
	private String member_email;
	private String member_type;
	private String member_create_date;
	public Integer getMember_number() {
		return member_number;
	}
	public void setMember_number(Integer member_number) {
		this.member_number = member_number;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getMember_type() {
		return member_type;
	}
	public void setMember_type(String member_type) {
		this.member_type = member_type;
	}
	public String getMember_create_date() {
		return member_create_date;
	}
	public void setMember_create_date(String member_create_date) {
		this.member_create_date = member_create_date;
	}
}