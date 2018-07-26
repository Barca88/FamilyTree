/**
 * Adapter for formating dates for the backend.
 * @author patricia.barros
 */

package com.familytree.util;

import java.lang.reflect.Type;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class DateTimeBackEndAdapter implements JsonDeserializer<DateTime>, JsonSerializer<DateTime> {

	public static final String UTC_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"; //$NON-NLS-1$
	public static final String TIME_FORMAT = "'PT'HH'H'mm'M'ss'S'";
	public static final DateTime dateOfBegin = new DateTime(0);

	/**
	 * Empty constructor for DateTimeBackEndAdapter.
	 */
	public DateTimeBackEndAdapter() {
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
	 *             an exception if the json is not a valid date
	 */
	@Override
	public DateTime deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
		throws JsonParseException
	{
		final String date = json.getAsString();
		if (date.startsWith("/Date")) {
			final String millis = date.substring(6, date.length() - 2);
			return new DateTime(Long.valueOf(millis), DateTimeZone.UTC);
		} else if (json.getAsString().startsWith("PT")) {
			final DateTimeFormatter fmt = DateTimeFormat.forPattern(TIME_FORMAT);
			return fmt.parseDateTime(json.getAsString());
		} else if (json.getAsString().contains("T")) {
			final DateTimeFormatter fmt = DateTimeFormat.forPattern(UTC_DATE_FORMAT);
			return fmt.parseDateTime(json.getAsString());
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
	public JsonElement serialize(final DateTime src, final Type typeOfSrc, final JsonSerializationContext context) {
		if (src != null) {
			if (DateTimeComparator.getDateOnlyInstance().compare(src, dateOfBegin) == 0) {
				return new JsonPrimitive(src.toString(TIME_FORMAT));
			} else {
				return new JsonPrimitive(src.toString(UTC_DATE_FORMAT));
			}
		}
		return null;
	}
}
