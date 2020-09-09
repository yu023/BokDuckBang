package bokduckbang.room;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class RoomImg {
	private int room_img_number;
	private byte[] room_img;

	public int getRoom_img_number() {
		return room_img_number;
	}
	public void setRoom_img_number(int room_number) {
		this.room_img_number = room_number;
	}
	public String getRoom_img() throws UnsupportedEncodingException {
		byte[] base64Encode = Base64.getEncoder().encode(room_img);
		return new String(Base64.getDecoder().decode(new String(base64Encode).getBytes("UTF-8")));
	}
	public void setRoom_img(byte[] room_img){
		this.room_img = room_img;
	}
}
