/**
 * Adapter for formating dates.
 * 
 * @author fabianorosa
 *
 */

package com.familytree.util;

import java.lang.reflect.Type;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class LocalDateTimeFrontEndAdapter implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
	private final DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss");

	/**
	 * Empty Constructor for LocalDateTimeFrontEndAdapter.
	 */
	public LocalDateTimeFrontEndAdapter() {
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
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
		throws JsonParseException
	{

		String localDateTime = json.getAsString();
		try {
			this.format.parseLocalDateTime(localDateTime);
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
	public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.toString(this.format));
	}
}
