/**
 * Adpater for strings.
 * @author patricia.barros
 */

package com.familytree.util;

import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class StringAdapter implements JsonSerializer<String> {
	/**
	 * Empty constructor for StringAdapter.
	 */
	public StringAdapter() {
		// empty constructor
	}

	/**
	 * Serializes a string into a JsonElement.
	 * 
	 * @param src
	 *            the string to parse
	 * @return the parsed JsonElement
	 */
	@Override
	public JsonElement serialize(String src, Type typeOfSrc, JsonSerializationContext context) {

		if (StringUtils.equals(src, "-1") || StringUtils.equals(src, "")) {
			return new JsonPrimitive("");
		}

		return new JsonPrimitive(src);
	}

}
