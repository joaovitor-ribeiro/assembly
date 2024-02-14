package com.br.assembly.kafka;

import java.io.Closeable;
import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class KafkaService<T> implements Closeable {
	
	private final KafkaConsumer<String, T> consumer;
	private final ConsumerFunction<T>      parse;
	
	public KafkaService(String groupId, String topic, ConsumerFunction<T> parse, Class<T> type, Map<String, String> properties) {
		this(groupId, parse, type, properties);
		this.consumer.subscribe(Collections.singletonList(topic));
	}
	
	private KafkaService(String groupId, ConsumerFunction<T> parse, Class<T> type, Map<String, String> properties) {
		this.parse    = parse;
		this.consumer = new KafkaConsumer<String, T>(getProperties(type, groupId, properties));
	}
	
	public void run() {
		var records = consumer.poll(Duration.ofMillis(100));
		if (!records.isEmpty()) {
			System.out.println("Encotrei " + records.count() + " registros");
			records.forEach(record -> {
				parse.consume(record);
			});
		}
	}
	
	private Properties getProperties(Class<T> type, String groupId, Map<String, String> overrideProperties) {
		var properties = new Properties();
		properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091");
		properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, GsonDeserializer.class.getName());
		properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		properties.setProperty(ConsumerConfig.CLIENT_ID_CONFIG, UUID.randomUUID().toString());
		properties.setProperty(GsonDeserializer.TYPE_CONFIG, type.getName());
		properties.putAll(overrideProperties);
		return properties;
	}

	@Override
	public void close() {
		consumer.close();
	}


}
