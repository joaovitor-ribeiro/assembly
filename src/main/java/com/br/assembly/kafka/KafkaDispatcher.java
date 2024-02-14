package com.br.assembly.kafka;

import java.io.Closeable;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher<T> implements Closeable {
	
	private final KafkaProducer<String, T> producer;

	public KafkaDispatcher() {
		producer = new KafkaProducer<>(properties());
	}
	
	public void send(String topic, String key, T value) {
		var record = new ProducerRecord<>(topic, key, value);
		Callback callback = (data, ex) -> {
			if (ex != null) {
				ex.printStackTrace();
				return;
			}
		};
		producer.send(record, callback);
	}
	
	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9091");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GsonSerializer.class.getName());
		return properties;
	}

	@Override
	public void close() {
		producer.close();
	}


}
