package mihajlovic.jelena;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

class PokeAPITest {

	private static final int NUMBER_OF_BERRIES = 64;

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

	@Test
	void testConnectToAPI() throws IOException {
		String response = "{\"ability\":\"https://pokeapi.co/api/v2/ability/\","
				+ "\"berry\":\"https://pokeapi.co/api/v2/berry/\","
				+ "\"berry-firmness\":\"https://pokeapi.co/api/v2/berry-firmness/\","
				+ "\"berry-flavor\":\"https://pokeapi.co/api/v2/berry-flavor/\","
				+ "\"characteristic\":\"https://pokeapi.co/api/v2/characteristic/\","
				+ "\"contest-effect\":\"https://pokeapi.co/api/v2/contest-effect/\","
				+ "\"contest-type\":\"https://pokeapi.co/api/v2/contest-type/\","
				+ "\"egg-group\":\"https://pokeapi.co/api/v2/egg-group/\","
				+ "\"encounter-condition\":\"https://pokeapi.co/api/v2/encounter-condition/\","
				+ "\"encounter-condition-value\":\"https://pokeapi.co/api/v2/encounter-condition-value/\","
				+ "\"encounter-method\":\"https://pokeapi.co/api/v2/encounter-method/\","
				+ "\"evolution-chain\":\"https://pokeapi.co/api/v2/evolution-chain/\","
				+ "\"evolution-trigger\":\"https://pokeapi.co/api/v2/evolution-trigger/\","
				+ "\"gender\":\"https://pokeapi.co/api/v2/gender/\","
				+ "\"generation\":\"https://pokeapi.co/api/v2/generation/\","
				+ "\"growth-rate\":\"https://pokeapi.co/api/v2/growth-rate/\","
				+ "\"item\":\"https://pokeapi.co/api/v2/item/\","
				+ "\"item-attribute\":\"https://pokeapi.co/api/v2/item-attribute/\","
				+ "\"item-category\":\"https://pokeapi.co/api/v2/item-category/\","
				+ "\"item-fling-effect\":\"https://pokeapi.co/api/v2/item-fling-effect/\","
				+ "\"item-pocket\":\"https://pokeapi.co/api/v2/item-pocket/\","
				+ "\"language\":\"https://pokeapi.co/api/v2/language/\","
				+ "\"location\":\"https://pokeapi.co/api/v2/location/\","
				+ "\"location-area\":\"https://pokeapi.co/api/v2/location-area/\","
				+ "\"machine\":\"https://pokeapi.co/api/v2/machine/\","
				+ "\"move\":\"https://pokeapi.co/api/v2/move/\","
				+ "\"move-ailment\":\"https://pokeapi.co/api/v2/move-ailment/\","
				+ "\"move-battle-style\":\"https://pokeapi.co/api/v2/move-battle-style/\","
				+ "\"move-category\":\"https://pokeapi.co/api/v2/move-category/\","
				+ "\"move-damage-class\":\"https://pokeapi.co/api/v2/move-damage-class/\","
				+ "\"move-learn-method\":\"https://pokeapi.co/api/v2/move-learn-method/\","
				+ "\"move-target\":\"https://pokeapi.co/api/v2/move-target/\","
				+ "\"nature\":\"https://pokeapi.co/api/v2/nature/\","
				+ "\"pal-park-area\":\"https://pokeapi.co/api/v2/pal-park-area/\","
				+ "\"pokeathlon-stat\":\"https://pokeapi.co/api/v2/pokeathlon-stat/\","
				+ "\"pokedex\":\"https://pokeapi.co/api/v2/pokedex/\","
				+ "\"pokemon\":\"https://pokeapi.co/api/v2/pokemon/\","
				+ "\"pokemon-color\":\"https://pokeapi.co/api/v2/pokemon-color/\","
				+ "\"pokemon-form\":\"https://pokeapi.co/api/v2/pokemon-form/\","
				+ "\"pokemon-habitat\":\"https://pokeapi.co/api/v2/pokemon-habitat/\","
				+ "\"pokemon-shape\":\"https://pokeapi.co/api/v2/pokemon-shape/\","
				+ "\"pokemon-species\":\"https://pokeapi.co/api/v2/pokemon-species/\","
				+ "\"region\":\"https://pokeapi.co/api/v2/region/\"," + "\"stat\":\"https://pokeapi.co/api/v2/stat/\","
				+ "\"super-contest-effect\":\"https://pokeapi.co/api/v2/super-contest-effect/\","
				+ "\"type\":\"https://pokeapi.co/api/v2/type/\","
				+ "\"version\":\"https://pokeapi.co/api/v2/version/\","
				+ "\"version-group\":\"https://pokeapi.co/api/v2/version-group/\"}";

		assertEquals(response, PokeAPI.connectToAPI("https://pokeapi.co/api/v2/"));
		assertThrows(IOException.class, () -> PokeAPI.connectToAPI("https://pokeapi.co/api/v2/123"));
	}

	@Test
	void testCountBerries() throws IOException {
		assertEquals(NUMBER_OF_BERRIES, PokeAPI.countBerries());
	}

	@Test
	void testFindBerries() throws IOException {
		assertEquals(NUMBER_OF_BERRIES, PokeAPI.findBerries().size());
		assertEquals("https://pokeapi.co/api/v2/berry/1/", PokeAPI.findBerries().get(0));
		assertEquals("https://pokeapi.co/api/v2/berry/16/", PokeAPI.findBerries().get(15));
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
	}

	@Test
	void testFindTheLargestBerry() throws IOException {
		List<JSONObject> objects = createTestList();
		assertEquals(objects.get(4), PokeAPI.findTheLargestBerry(objects, "filter", "size"));
	}

}
