package com.familytree.util;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public final class GsonFactory implements Serializable {

	/**
	 * Gson is a Java library that can be used to convert Java Objects into
	 * their JSON representation. It can also be used to convert a JSON string
	 * to an equivalent Java object.
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static final GsonFactory INSTANCE = new GsonFactory();

	public static GsonFactory getInstance() {
		return INSTANCE;
	}

	private GsonFactory() {
	}

	public Gson createDefaultGson() {
		return createaDefaultJsonBuilder().create();
	}

	public Gson createFrontEndGson() {
		return createFrontendJsonBuilder().create();
	}

	public Gson createBackEndGson() {
		return createBackendJsonBuilder().create();
	}

	public Gson createAnnotatedGson() {
		final GsonBuilder gb = createaDefaultJsonBuilder();
		return gb.excludeFieldsWithoutExposeAnnotation().create();
	}

	private GsonBuilder createaDefaultJsonBuilder() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeFrontEndAdapter());
		gsonBuilder.registerTypeAdapter(String.class, new StringAdapter());
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.disableHtmlEscaping();
		return gsonBuilder;
	}

	private GsonBuilder createBackendJsonBuilder() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeBackEndAdapter());
		gsonBuilder.registerTypeAdapter(String.class, new StringAdapter());
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.serializeNulls();
		return gsonBuilder;
	}

	private GsonBuilder createFrontendJsonBuilder() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(DateTime.class, new DateTimeFrontEndAdapter());
		gsonBuilder.registerTypeAdapter(String.class, new StringAdapter());
		gsonBuilder.setPrettyPrinting();
		gsonBuilder.disableHtmlEscaping();
		gsonBuilder.serializeNulls();
		return gsonBuilder;
	}

	private JsonReader createJsonReader(String json) throws IOException {
		JsonReader reader = new JsonReader(new StringReader(json));
		reader.setLenient(true);
		return reader;
	}

	public <T> T load(String json, JsonLoadingJob<T> job) {
		JsonReader reader = null;
		try {
			reader = createJsonReader(json);
			return job.loadFromJson(reader);
		} catch (IOException e) {
			// logger.error(e.getMessage());
			return null;
		}
	}

	private interface JsonLoadingJob<T> {
		T loadFromJson(JsonReader reader);
	}

	/**
	 * Parses a string and turns it into a JsonObject.
	 * 
	 * @param jsonString
	 *            the string to parse
	 * @return the jsonObject
	 */
	public static JsonObject parseGenericJsonObject(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonReader reader = new JsonReader(new StringReader(jsonString));
		reader.setLenient(true);
		return (JsonObject) parser.parse(reader);
	}

	/**
	 * Parses a string into a JsonArray.
	 * 
	 * @param jsonString
	 *            the string to parse
	 * @return the JsonArray
	 */
	public static JsonArray parseGenericJsonArray(String jsonString) {
		JsonParser parser = new JsonParser();
		return (JsonArray) parser.parse(jsonString);
	}
}
