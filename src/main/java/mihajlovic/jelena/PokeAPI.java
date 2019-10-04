package mihajlovic.jelena;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PokeAPI {

	private static final String ROOT = "https://pokeapi.co/api/v2/";

	static String connectToAPI(String root) throws IOException {
		URL request = new URL(root);
		InputStream openStream = request.openStream();
		return IOUtils.toString(openStream, "UTF-8");
	}

	static int countBerries() throws IOException {
		JSONObject body = new JSONObject(connectToAPI(ROOT + "berry/"));
		return body.getInt("count");
	}

	static List<String> findBerries() throws IOException {
		List<String> berries = new ArrayList<String>();
		JSONObject body = new JSONObject(connectToAPI(ROOT + "berry/?offset=0&limit=" + countBerries()));
		JSONArray json = body.getJSONArray("results");
		json.forEach(item -> berries.add(((JSONObject) item).getString("url")));
		return berries;
	}

	static List<JSONObject> getBerries() throws IOException {
		List<JSONObject> berries = new ArrayList<JSONObject>();
		findBerries().forEach(url -> {
			try {
				berries.add(new JSONObject(connectToAPI(url)));
			} catch (JSONException | IOException e) {
				e.printStackTrace();
			}
		});
		return berries;
	}

	static int findTheShortestTime(List<JSONObject> berries, String filter) throws JSONException, IOException {
		return berries
				.stream()
				.min(Comparator.comparingInt(berry -> berry.getInt(filter)))
				.get()
				.getInt(filter);
	}

	static JSONObject findTheLargestBerry(List<JSONObject> berries, String filter, String toCompare) throws IOException {
		int minTime = findTheShortestTime(berries, filter);
		return berries
				.stream()
				.filter(berry -> berry.getInt(filter) == minTime)
				.max(Comparator.comparingInt(berry -> berry.getInt(toCompare)))
				.get();
	}
}
