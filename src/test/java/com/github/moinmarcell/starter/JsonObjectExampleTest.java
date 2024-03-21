package com.github.moinmarcell.starter;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonObjectExampleTest {

  @Test
  void test() {
    JsonObject object = new JsonObject();
    object.put("id", 1);
    object.put("name", "Marcell");
    object.put("loves_vertx", true);

    String encoded = object.encode();
    JsonObject decoded = new JsonObject(encoded);

    assertEquals(object, decoded);
  }

  @Test
  void test2() {
    Map<String, Object> myMap = new HashMap<>();
    myMap.put("id", 1);
    myMap.put("name", "Marcell");
    myMap.put("loves_vertx", true);
    JsonObject asJsonObject = new JsonObject(myMap);
    assertEquals(myMap, asJsonObject.getMap());
    assertEquals("Marcell", asJsonObject.getString("name"));
    assertEquals(1, asJsonObject.getInteger("id"));
    assertEquals(true, asJsonObject.getBoolean("loves_vertx"));
  }

  @Test
  void jsonArrayCanBeMapped() {
    JsonArray myJsonArray = new JsonArray();
    myJsonArray.add(new JsonObject()
      .put("id", 1)
      .put("name", "Marcell")
    );

    assertEquals("[{\"id\":1,\"name\":\"Marcell\"}]", myJsonArray.encode());
  }

  @Test
  void canMapJavaObjects(){
    Person person = new Person(1, "Marcell", true);
    JsonObject marcell = JsonObject.mapFrom(person);
    assertEquals(person.getId(), marcell.getInteger("id"));
    assertEquals(person.getName(), marcell.getString("name"));
    assertEquals(person.isLovesVertx(), marcell.getBoolean("lovesVertx"));

    Person person2 = marcell.mapTo(Person.class);
    assertEquals(person.getId(), person2.getId());
    assertEquals(person.getName(), person2.getName());
    assertEquals(person.isLovesVertx(), person2.isLovesVertx());
  }
}
