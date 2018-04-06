package Server;

import java.util.Map;

import org.json.simple.JSONObject;
@SuppressWarnings("unchecked")
public class JSONBuilder {
	private JSONBuilder () {};
	private static final JSONBuilder JB = new JSONBuilder();
	
	public static JSONBuilder getJSONBuilder () {
		return JB;
	}
	
	public static JSONObject build (Map<Object, Object> m) {
		JSONObject obj = new JSONObject();
		obj.putAll(m);
		return obj;
	}
	
	public static JSONObject build (Object[] keys, Object[] values) {
		JSONObject obj = new JSONObject();
		if(keys.length != values.length) return null; 
		for (int i = 0; i < keys.length; i++) {
			obj.put(keys[i], values[i]);
		}
		return obj;
	}
	public static JSONObject build (Object key, Object value) {
		JSONObject obj = new JSONObject();
		obj.put(key, value);
		return obj;
	}
}
