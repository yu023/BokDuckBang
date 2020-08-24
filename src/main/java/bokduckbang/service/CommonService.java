package bokduckbang.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

@Service
public class CommonService {
	public String getMyKey() throws ParseException {
		
	 	URL url = null;
	    HttpURLConnection conn = null;
	    String jsonData = "";
	    BufferedReader br = null;
	    StringBuffer sb = null;
	    JSONParser jsonParser = new JSONParser();
	    String key = null;
	 
	    try {
	        url = new URL("http://localhost:8080/BokDuckBang/assets/json/google-key.json");
	 
	        conn = (HttpURLConnection) url.openConnection();
	        conn.setRequestProperty("Accept", "application/json");
	        conn.setRequestMethod("GET");
	        conn.connect();
	 
	        br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
	 
	        sb = new StringBuffer();
	 
	        while ((jsonData = br.readLine()) != null) {
	            sb.append(jsonData);
	        }
	        
	        @SuppressWarnings("unchecked")
			HashMap<String, Object> map = (HashMap<String, Object>) jsonParser.parse(sb.toString());
	        
	        key = String.valueOf(map.get("key"));
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (br != null) br.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	 return key;
}
}
