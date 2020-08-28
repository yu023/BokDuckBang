package bokduckbang.room;

import org.springframework.stereotype.Component;

@Component
public class Room {
	
	private String room_selling_type;
	private Integer room_type;
	private String room_type_str;
	private String room_address;
	private String room_complex_id;
	private String room_dong;
	private String room_ho;
	private String room_lat_lng;
	private Double room_lat;
	private Double room_lng;
	private String room_title;
	private String room_content;
	private String room_money_lease;
	private String room_money_deposit;
	private String room_money_monthly_rent;
	private String room_service;
	private String room_service_charge;
	private String room_park_charge;
	private Double room_width;
	private String room_keywords;
	private String room_floor_str;
	private String room_total_floor;
	private String room_elevator;
	private String room_heating;
	private String room_balcony;
	private String room_loan;
	private String room_move_in_date;
	private String room_option;
	private String room_author_email;
	private String room_create_date;
	private String room_status;
	private String room_likes;
	private String room_hits;
	
	private int room_deposit_num;
	private int room_lease_num;
	private int room_monthly_rent_num;
	
	private String room_number;
	private String room_img_url;
	
	public String getRoom_selling_type() {
		return room_selling_type;
	}
	public void setRoom_selling_type(String room_selling_type) {
		this.room_selling_type = room_selling_type;
	}
	public String getRoom_address() {
		return room_address;
	}
	public void setRoom_address(String room_address) {
		this.room_address = room_address;
	}
	public String getRoom_complex_id() {
		return room_complex_id;
	}
	public void setRoom_complex_id(String room_complex_id) {
		this.room_complex_id = room_complex_id;
	}
	public String getRoom_dong() {
		return room_dong;
	}
	public void setRoom_dong(String room_dong) {
		this.room_dong = room_dong;
	}
	public String getRoom_ho() {
		return room_ho;
	}
	public void setRoom_ho(String room_ho) {
		this.room_ho = room_ho;
	}
	public String getRoom_lat_lng() {
		return room_lat_lng;
	}
	public void setRoom_lat_lng(String room_lat_lng) {
		String[] arr = room_lat_lng.split(",");
		setRoom_lng(Double.valueOf(arr[0]));
		setRoom_lat(Double.valueOf(arr[1]));
		this.room_lat_lng = arr[1] + " " + arr[0];
	}
	public Double getRoom_lat() {
		return room_lat;
	}
	public void setRoom_lat(Double room_lat) {
		this.room_lat = room_lat;
	}
	public Double getRoom_lng() {
		return room_lng;
	}
	public void setRoom_lng(Double room_lng) {
		this.room_lng = room_lng;
	}
	public String getRoom_title() {
		return room_title;
	}
	public void setRoom_title(String room_title) {
		this.room_title = room_title;
	}
	public String getRoom_content() {
		return room_content;
	}
	public void setRoom_content(String room_content) {
		this.room_content = room_content;
	}
	public Integer getRoom_type() {
		return room_type;
	}
	public void setRoom_type(Integer room_type) {
		if(room_type == 0) {
			setRoom_type_str("원룸");
		}else if(room_type == 1) {
			setRoom_type_str("투룸");
		}else if(room_type == 2) {
			setRoom_type_str("쓰리룸");
		}else {
			setRoom_type_str("포룸 이상");
		}
		this.room_type = room_type;
	}
	public String getRoom_type_str() {
		return room_type_str;
	}
	public void setRoom_type_str(String room_type_str) {
		this.room_type_str = room_type_str;
	}
	public String getRoom_money_lease() {
		return room_money_lease;
	}
	public void setRoom_money_lease(String room_money_lease) {
		this.room_money_lease = room_money_lease;
	}
	public String getRoom_money_deposit() {
		return room_money_deposit;
	}
	public void setRoom_money_deposit(String room_money_deposit) {
		this.room_money_deposit = room_money_deposit;
	}
	public String getRoom_money_monthly_rent() {
		return room_money_monthly_rent;
	}
	public void setRoom_money_monthly_rent(String room_money_monthly_rent) {
		this.room_money_monthly_rent = room_money_monthly_rent;
	}
	public String getRoom_service() {
		return room_service;
	}
	public void setRoom_service(String room_service) {
		this.room_service = room_service;
	}
	public String getRoom_service_charge() {
		return room_service_charge;
	}
	public void setRoom_service_charge(String room_service_charge) {
		this.room_service_charge = room_service_charge;
	}
	public String getRoom_park_charge() {
		return room_park_charge;
	}
	public void setRoom_park_charge(String room_park_charge) {
		this.room_park_charge = room_park_charge;
	}
	public Double getRoom_width() {
		return room_width;
	}
	public void setRoom_width(Double room_width) {
		this.room_width = room_width;
	}
	public String getRoom_keywords() {
		return room_keywords;
	}
	public void setRoom_keywords(String room_keywords) {
		this.room_keywords = room_keywords;
	}
	public String getRoom_floor_str() {
		return room_floor_str;
	}
	public void setRoom_floor_str(String room_floor_str) {
		this.room_floor_str = room_floor_str;
	}
	public String getRoom_total_floor() {
		return room_total_floor;
	}
	public void setRoom_total_floor(String room_total_floor) {
		this.room_total_floor = room_total_floor;
	}
	public String getRoom_elevator() {
		return room_elevator;
	}
	public void setRoom_elevator(String room_elevator) {
		this.room_elevator = room_elevator;
	}
	public String getRoom_heating() {
		return room_heating;
	}
	public void setRoom_heating(String room_heating) {
		this.room_heating = room_heating;
	}
	public String getRoom_balcony() {
		return room_balcony;
	}
	public void setRoom_balcony(String room_balcony) {
		this.room_balcony = room_balcony;
	}
	public String getRoom_loan() {
		return room_loan;
	}
	public void setRoom_loan(String room_loan) {
		this.room_loan = room_loan;
	}
	public String getRoom_move_in_date() {
		return room_move_in_date;
	}
	public void setRoom_move_in_date(String room_move_in_date) {
		this.room_move_in_date = room_move_in_date;
	}
	public String getRoom_option() {
		return room_option;
	}
	public void setRoom_option(String room_option) {
		this.room_option = room_option;
	}
	public String getRoom_author_email() {
		return room_author_email;
	}
	public void setRoom_author_email(String room_author_email) {
		this.room_author_email = room_author_email;
	}
	public String getRoom_create_date() {
		return room_create_date;
	}
	public void setRoom_create_date(String room_create_date) {
		this.room_create_date = room_create_date;
	}
	public String getRoom_status() {
		return room_status;
	}
	public void setRoom_status(String room_status) {
		this.room_status = room_status;
	}
	public String getRoom_likes() {
		return room_likes;
	}
	public void setRoom_likes(String room_likes) {
		this.room_likes = room_likes;
	}
	public String getRoom_hits() {
		return room_hits;
	}
	public void setRoom_hits(String room_hits) {
		this.room_hits = room_hits;
	}
	
	public int getRoom_deposit_num() {
		return room_deposit_num;
	}
	public void setRoom_deposit_num(int room_deposit_num) {
		this.room_deposit_num = room_deposit_num;
	}
	public int getRoom_lease_num() {
		return room_lease_num;
	}
	public void setRoom_lease_num(int room_lease_num) {
		this.room_lease_num = room_lease_num;
	}
	public int getRoom_monthly_rent_num() {
		return room_monthly_rent_num;
	}
	public void setRoom_monthly_rent_num(int room_monthly_rent_num) {
		this.room_monthly_rent_num = room_monthly_rent_num;
	}
	
	public String getRoom_number() {
		return room_number;
	}
	public void setRoom_number(String room_number) {
		this.room_number = room_number;
	}
	public String getRoom_img_url() {
		return room_img_url;
	}
	public void setRoom_img_url(String room_img_url) {
		this.room_img_url = room_img_url;
	}

}
