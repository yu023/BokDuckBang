package bokduckbang.member.mail;

import java.util.ArrayList;

public class ResetPw {
	public static ArrayList<String> randomNum() {
		ArrayList<String> list = new ArrayList<String>();
		for(int i = 0; i < 10; i++) {
			String j = String.valueOf(i);
			list.add(j);
		}
		return list;
	}
	
	public static ArrayList<Character> randomalp() {
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i = 65; i < 91; i++) {
			char j = (char)i;
			list.add(j);
		}
		return list;
	}
	
	public static ArrayList<Character> randomalp2() {
		ArrayList<Character> list = new ArrayList<Character>();
		for(int i = 97; i < 123; i++) {
			char j = (char)i;
			list.add(j);
		}
		return list;
	}

	public static ArrayList<String> pwSet = new ArrayList<String>();
	
	public static String randomPw() {
		
		while(pwSet.size() < 3) {
			int ran = (int)(Math.random()*10);
			pwSet.add(randomNum().get(ran));
		}
		
		while(pwSet.size() < 7) {
			int ran = (int)(Math.random()*10);
			pwSet.add(String.valueOf(randomalp().get(ran)));
		}
		
		while(pwSet.size() < 9) {
			int ran = (int)(Math.random()*10);
			pwSet.add(String.valueOf(randomalp2().get(ran)));
		}

		String realPw = "";
		
		for(int i = 0; i < pwSet.size(); i++) {
			int ran = (int)(Math.random()*pwSet.size());
			realPw += pwSet.get(ran);
		}
		
		return realPw;
		
	}
}
