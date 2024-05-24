package utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Base64;
import java.util.List;
import com.fasterxml.jackson.databind.JsonSerializer;
import java.io.IOException;
import java.util.Map;

public class JsonUtils {

    public static JsonObject readJsonFile(String fileName) {

        JsonObject object = null;
        Gson gson = new Gson();
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();

        try (InputStream is = classloader.getResourceAsStream(fileName);
             InputStreamReader streamReader = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(streamReader)) {
            object =  gson.fromJson(reader, JsonObject.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    public static List<String> parseJsonArrayToListString(JsonObject jsonObject, String jsonPath){
        JsonElement jsonElement = jsonObject.get(jsonPath);
        Type listType = new TypeToken<List<String>>(){}.getType();
        return new Gson().fromJson(jsonElement, listType);
    }

    public class CustomSerializer extends JsonSerializer<Object> {
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
            jgen.writeStartObject();
            jgen.writeObjectField(value.getClass().getName(), value);
            jgen.writeEndObject();
        }
    }

    public static String getFromJwt(String jwt, String fieldName){
        String jwtPayloadEncoded = jwt.split("\\.")[1];
        String jwtPayloadDecoded = new String(Base64.getDecoder().decode(jwtPayloadEncoded));
        return parseJsonStringToJsonObject(jwtPayloadDecoded).get(fieldName).getAsString();
    }

    public static JsonObject parseJsonStringToJsonObject(String jsonAsString){
        return new JsonParser().parse(jsonAsString).getAsJsonObject();
    }

    public static String parseMapToJsonString(Map<String,String> map){
        return new Gson().toJson(map);
    }

    public static JsonArray getFromJwtAsJsonArray(String jwt, String fieldName){
        String jwtPayloadEncoded = jwt.split("\\.")[1];
        String jwtPayloadDecoded = new String(Base64.getDecoder().decode(jwtPayloadEncoded));
        return parseJsonStringToJsonObject(jwtPayloadDecoded).get(fieldName).getAsJsonArray();
    }
}