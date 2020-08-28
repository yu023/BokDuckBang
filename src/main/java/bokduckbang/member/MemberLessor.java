package bokduckbang.member;

import org.springframework.stereotype.Component;

@Component
public class MemberLessor {
	
	private Boolean member_type = false;
	private String member_email;
	private String member_password;
	private String member_business_license;
	private String member_business_name;
	private String member_ceo_name;
	private String member_business_phn_num;
	private String member_purchase_goods;
	private String member_amount;
	
	public Boolean getMember_type() {
		return member_type;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email.trim();
	}
	public String getMember_password() {
		return member_password;
	}
	public void setMember_password(String member_password) {
		this.member_password = member_password.trim();
	}
	public String getMember_business_license() {
		return member_business_license;
	}
	public void setMember_business_license(String member_business_license) {
		this.member_business_license = member_business_license.trim();
	}
	public String getMember_business_name() {
		return member_business_name;
	}
	public void setMember_business_name(String member_business_name) {
		this.member_business_name = member_business_name.trim();
	}
	public String getMember_ceo_name() {
		return member_ceo_name;
	}
	public void setMember_ceo_name(String member_ceo_name) {
		this.member_ceo_name = member_ceo_name.trim();
	}
	public String getMember_business_phn_num() {
		return member_business_phn_num;
	}
	public void setMember_business_phn_num(String member_business_phn_num) {
		this.member_business_phn_num = member_business_phn_num.trim();
	}
	public String getMember_purchase_goods() {
		return member_purchase_goods;
	}
	public void setMember_purchase_goods(String member_purchase_goods) {
		this.member_purchase_goods = member_purchase_goods.trim();
	}
	public String getMember_amount() {
		return member_amount;
	}
	public void setMember_amount(String member_amount) {
		this.member_amount = member_amount.trim();
	}

}
