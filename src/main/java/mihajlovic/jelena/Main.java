package mihajlovic.jelena;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

public class Main {

	public static void main(String[] args) throws JSONException, IOException {
		JSONObject berry = PokeAPI.findTheLargestBerryYouCanGrowInTheShortestTime
				(PokeAPI.getBerries(), "growth_time", "size");
		System.out.println(berry.getString("name") + " is the largest berry you can grow in the shortest time. URL: "
				+ PokeAPI.findBerries().get(berry.getInt("id") - 1));
	}
}