
/**
 * Adapter for formating dates for the frontend.
 * @author patricia.barros
 */

package com.familytree.util;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTimeFrontEndAdapter implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

	private static final List<DateTimeFormatter> formats =
		Arrays.asList(DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss"), DateTimeFormat.forPattern("HH:mm"));

	DateTime dateFromBegin = new DateTime(0);

	/**
	 * Empty constructor for DateTimeFrontEndAdapter.
	 */
	public DateTimeFrontEndAdapter() {
		// empty constructor
	}

	/**
	 * Takes a json element with a date and deserializes it to a DateTime
	 * object.
	 * 
	 * @param json
	 *            the date in json format
	 * @returns the deserialized date
	 * @throws JsonParseException
	 *             an exception if the json cannot be parsed into a date
	 */
	@Override
	public DateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{

		String date = json.getAsString();
		try {
			if (StringUtils.contains(date, "T")) {
				return formats.get(0).parseDateTime(date);
			} else {
				return formats.get(1).parseDateTime(date);
			}
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * Takes a DateTime object and serializes it to a JsonElement. object.
	 * 
	 * @param src
	 *            the date
	 * @returns the serialized date
	 */
	@Override
	public JsonElement serialize(DateTime src, Type typeOfSrc, JsonSerializationContext context) {

		if (DateTimeComparator.getDateOnlyInstance().compare(src, dateFromBegin) == 0) {
			return new JsonPrimitive(src.toString(formats.get(1)));
		} else {
			return new JsonPrimitive(src.toString(formats.get(0)));
		}
	}

}
