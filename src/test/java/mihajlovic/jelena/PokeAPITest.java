package mihajlovic.jelena;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class PokeAPITest {

	private static final int NUMBER_OF_BERRIES = 64;
	private static final String POKE_API_ROOT = "https://pokeapi.co/api/v2/";

	@Test
	void testCountBerries() throws IOException {
		assertEquals(NUMBER_OF_BERRIES, PokeAPI.countBerries());
	}

	@Test
	void testFindBerries() throws IOException {
		assertEquals(NUMBER_OF_BERRIES, PokeAPI.findBerries().size());
		for (int i = 1; i <= NUMBER_OF_BERRIES; i++)
			assertEquals(POKE_API_ROOT + "berry/" + i + "/", PokeAPI.findBerries().get(i - 1));
	}

	@Test
	void testGetBerries() throws IOException {
		assertEquals(NUMBER_OF_BERRIES, PokeAPI.getBerries().size());
		String[] keysOfJson = { "firmness", "flavors", "growth_time", "id", "item", "max_harvest", "name",
				"natural_gift_power", "natural_gift_type", "size", "smoothness", "soil_dryness" };
		for (JSONObject jsonObject : PokeAPI.getBerries()) {
			Iterator<String> keys = jsonObject.keys();
			while (keys.hasNext()) {
				String key = keys.next();
				assertTrue(Arrays.stream(keysOfJson).anyMatch(key::equals));
			}
		}
	}

	@Test
	void testFindTheShortestTime() throws JSONException, IOException {
		List<JSONObject> objects = createTestList();
		assertEquals(1, PokeAPI.findTheShortestTime(objects, "filter"));
		assertThrows(JSONException.class, () -> PokeAPI.findTheShortestTime(objects, "hello"));
	}

	@Test
	void testFindTheLargestBerryYouCanGrowInTheShortestTime() throws IOException {
		List<JSONObject> objects = createTestList();
		assertEquals(objects.get(4), PokeAPI.findTheLargestBerryYouCanGrowInTheShortestTime(objects, "filter", "size"));
	}

	@Test
	void testConnectToAPI() throws IOException {
		URL url = new URL(POKE_API_ROOT);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		assertEquals(200, conn.getResponseCode());

		assertNotNull(PokeAPI.connectToAPI(POKE_API_ROOT));
		assertNotNull(PokeAPI.connectToAPI(POKE_API_ROOT + "berry/"));
		assertNotNull(PokeAPI.connectToAPI(POKE_API_ROOT + "berry/?offset=0&limit=" + NUMBER_OF_BERRIES));
		for (int i = 1; i <= NUMBER_OF_BERRIES; i++)
			assertNotNull(PokeAPI.connectToAPI(POKE_API_ROOT + "berry/" + i + "/"));
		assertThrows(IOException.class, () -> PokeAPI.connectToAPI(POKE_API_ROOT + 123));

		JSONObject obj = new JSONObject(PokeAPI.connectToAPI(POKE_API_ROOT));
		String berryURL = obj.getString("berry");
		assertEquals(POKE_API_ROOT + "berry/", berryURL);

		String response = IOUtils.toString((new File("doc/jsonResponse.txt")).toURI(), "UTF-8");
		assertEquals(response.replaceAll("\n", ""), PokeAPI.connectToAPI(POKE_API_ROOT));
	}

	private static List<JSONObject> createTestList() {
		List<JSONObject> objects = new ArrayList<JSONObject>();
		JSONObject one = new JSONObject();
		one.put("filter", 1);
		one.put("key", "one");
		one.put("size", 10);
		objects.add(one);
		JSONObject two = new JSONObject();
		two.put("key", "two");
		two.put("filter", 2);
		two.put("size", 20);
		objects.add(two);
		JSONObject three = new JSONObject();
		three.put("filter", 3);
		three.put("size", 30);
		three.put("key", "three");
		objects.add(three);
		JSONObject four = new JSONObject();
		four.put("key", "four");
		four.put("size", 40);
		four.put("filter", 4);
		objects.add(four);
		JSONObject five = new JSONObject();
		five.put("key", "five");
		five.put("size", 40);
		five.put("filter", 1);
		objects.add(five);
		JSONObject six = new JSONObject();
		six.put("key", "six");
		six.put("size", 20);
		six.put("filter", 1);
		objects.add(six);
		return objects;
	}
}
