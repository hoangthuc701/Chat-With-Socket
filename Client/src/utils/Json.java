package utils;

import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class Json {
	public static JSONObject JsonDecode(String s) {
		Object obj = JSONValue.parse(s);
		JSONObject jsonObject = (JSONObject) obj;
		return jsonObject;
	}

	public static String JsonEncode(HashMap hashmap) {
		String jsonText = JSONValue.toJSONString(hashmap);
		return jsonText;
	}
}
