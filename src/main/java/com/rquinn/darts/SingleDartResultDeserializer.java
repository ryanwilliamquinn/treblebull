package com.rquinn.darts;

import com.google.gson.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 3/25/13
 * Time: 9:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class SingleDartResultDeserializer implements JsonDeserializer<SingleDartResult> {

    private static final Logger slf4jLogger = LoggerFactory.getLogger(SingleDartResultDeserializer.class);

    @Override
    public SingleDartResult deserialize(JsonElement json, Type typeofT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jo = json.getAsJsonObject();
        slf4jLogger.debug("wtf get it as a string");
        try {
        slf4jLogger.debug(json.toString());
        } catch (Exception e) {
          slf4jLogger.debug("errrorrrz", e);
          slf4jLogger.error("pssssh", e);
        }
        String type = jo.get("type").getAsString();
        String dart = jo.get("dart").getAsString();
        SingleDartResult singleDartResult = new SingleDartResult();
        singleDartResult.setTarget(type);
        singleDartResult.setDart(dart);
        try {
            Integer score = jo.get("score").getAsInt();
            singleDartResult.setScore(score);
            Long dateMilliseconds = jo.get("dateMilliseconds").getAsLong();
            singleDartResult.getDateTimeManagement().setDateMilliseconds(dateMilliseconds);
        } catch (NumberFormatException e) {
            slf4jLogger.error("problem formatting score or dateMillis", e);
        }
        return singleDartResult;
    }
}
