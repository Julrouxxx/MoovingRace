package upsaclay.moovingrace.utils;

import com.google.gson.*;

import java.lang.reflect.Type;

public class MapSerializer implements JsonSerializer<Map>, JsonDeserializer<Map> {
    @Override
    public JsonElement serialize(Map map, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", map.getName());
        jsonObject.addProperty("loop", map.isLoop());
        if( map.getMaxLap().isPresent())
            jsonObject.addProperty("laps", map.getMaxLap().get());
        jsonObject.addProperty("scale", map.getScale());
        JsonArray tracks = new JsonArray();
        for (Track track : map.getTracks()) {
            JsonObject jsonTrack = new JsonObject();
            jsonTrack.addProperty("x", track.getPositionX(1)/64);
            jsonTrack.addProperty("y", track.getPositionY(1)/64);
            jsonTrack.addProperty("rotation", track.getRotation().toString());
            jsonTrack.addProperty("type", track.getType().toString());
            tracks.add(jsonTrack);
        }
        jsonObject.add("tracks", tracks);
        return jsonObject;
    }

    @Override
    public Map deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsonMap = jsonElement.getAsJsonObject();
        boolean isLoop = jsonMap.get("loop").getAsBoolean();
        Map map;
        if(isLoop)
             map = new Map(jsonMap.get("name").getAsString(), jsonMap.get("scale").getAsInt(), jsonMap.get("laps").getAsInt());
        else
             map = new Map(jsonMap.get("name").getAsString(), jsonMap.get("scale").getAsInt());
        JsonArray jsonTrack = jsonMap.get("tracks").getAsJsonArray();
        for (JsonElement arrayTrack : jsonTrack) {
            JsonObject track = arrayTrack.getAsJsonObject();
            Track t = new Track(TrackType.valueOf(track.get("type").getAsString()), TrackRotation.valueOf(track.get("rotation").getAsString()), track.get("x").getAsInt(), track.get("y").getAsInt());
            map.getTracks().add(t);
        }
        return map;
    }
}
