package com.br.assembly.kafka;

import java.util.Map;

import org.apache.kafka.common.serialization.Deserializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonDeserializer<T> implements Deserializer<T> {
	
	public static final String TYPE_CONFIG = "com.br.assembly";
	
	private Class<T> type;
	private final Gson gson = new GsonBuilder().create();
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure(Map<String, ?> configs, boolean isKey) {
		String typeName = String.valueOf(configs.get(TYPE_CONFIG));
		try {
			type = (Class<T>) Class.forName(typeName);
		} catch (Exception e) {
			throw new RuntimeException("Type for deserialization does not exist in the classpath.", e);
		}
	}

	@Override
	public T deserialize(String topic, byte[] bytes) {
		return gson.fromJson(new String(bytes), type);
	}
	
}
