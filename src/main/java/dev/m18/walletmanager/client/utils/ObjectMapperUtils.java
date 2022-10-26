package dev.m18.walletmanager.client.utils;

import java.io.IOException;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import dev.m18.walletmanager.client.enums.ChainType;
import dev.m18.walletmanager.client.enums.Direction;
import dev.m18.walletmanager.client.enums.IntEnum;
import dev.m18.walletmanager.client.enums.OperationBatchStatus;
import dev.m18.walletmanager.client.enums.OperationStatus;
import dev.m18.walletmanager.client.enums.OperationTransactionStatus;
import dev.m18.walletmanager.client.enums.OperationType;
import dev.m18.walletmanager.client.enums.TransactionStatus;
import dev.m18.walletmanager.client.enums.TransactionType;
import dev.m18.walletmanager.client.enums.WalletType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectMapperUtils {

	protected static ObjectMapper objectMapper = null;
	
	
	public static ObjectMapper getObjectMapper() {
		if(objectMapper == null) {
			objectMapper = _getObjectMapper();
		}
		return objectMapper;
	}
	
	private static ObjectMapper _getObjectMapper() {
		
		if(objectMapper == null) {
			objectMapper = new ObjectMapper()
					.setSerializationInclusion(Include.NON_NULL)
					.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			
			SimpleModule module = new SimpleModule();

			// Long
			module.addSerializer(Long.class, new JsonSerializer<Long>() {

				@Override
				public void serialize(Long value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeString(value.toString());
				}

			});

			module.addDeserializer(Long.class, new JsonDeserializer<Long>() {

				@Override
				public Long deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return Long.parseLong(p.getValueAsString());
				}
			});			

			// java.util.Date
			
			module.addSerializer(Date.class, new JsonSerializer<Date>() {

				@Override
				public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeNumber(value.getTime());
				}

			});

			module.addDeserializer(Date.class, new JsonDeserializer<Date>() {

				@Override
				public Date deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					String dateStr = p.getValueAsString();
					try {
						if(StringUtils.isNumeric(dateStr)) {
							if(dateStr.length() <= 10 ) {
								// 1662535759  * 1000
								return new Date(Long.parseLong(dateStr) * 1000);
							}else {
								return new Date(Long.parseLong(dateStr)); 
							}
						}else {
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
							return format.parse(dateStr);
						}						
					}catch(Exception e){
						log.error("Parse date string error " + dateStr, e);
						return null; 
					}

				}
			});	
			
			// BigInteger
			
			module.addSerializer(BigInteger.class, new JsonSerializer<BigInteger>() {

				@Override
				public void serialize(BigInteger value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeString(value.toString());
				}

			});

			module.addDeserializer(BigInteger.class, new JsonDeserializer<BigInteger>() {

				@Override
				public BigInteger deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return new BigInteger(p.getValueAsString());
				}
			});	
			
			// Enum
			
			module.addSerializer(IntEnum.class, new JsonSerializer<IntEnum>() {

				@Override
				public void serialize(IntEnum value, JsonGenerator gen, SerializerProvider serializers)
						throws IOException {
					gen.writeNumber(value.getIntVal());
				}

			});

			// ChainType
			module.addDeserializer(ChainType.class, new JsonDeserializer<ChainType>() {

				@Override
				public ChainType deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return ChainType.getByValue(p.getValueAsInt());
				}
			});
			
			// Direction
			module.addDeserializer(Direction.class, new JsonDeserializer<Direction>() {

				@Override
				public Direction deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return Direction.getByValue(p.getValueAsInt());
				}
			});
			
			// OperationBatchStatus
			module.addDeserializer(OperationBatchStatus.class, new JsonDeserializer<OperationBatchStatus>() {

				@Override
				public OperationBatchStatus deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return OperationBatchStatus.getByValue(p.getValueAsInt());
				}
			});
			
			// OperationStatus
			module.addDeserializer(OperationStatus.class, new JsonDeserializer<OperationStatus>() {

				@Override
				public OperationStatus deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return OperationStatus.getByValue(p.getValueAsInt());
				}
			});
			
			// OperationTransactionStatus
			module.addDeserializer(OperationTransactionStatus.class, new JsonDeserializer<OperationTransactionStatus>() {

				@Override
				public OperationTransactionStatus deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return OperationTransactionStatus.getByValue(p.getValueAsInt());
				}
			});
			
			// OperationType
			module.addDeserializer(OperationType.class, new JsonDeserializer<OperationType>() {

				@Override
				public OperationType deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return OperationType.getByValue(p.getValueAsInt());
				}
			});
			
			// TransactionStatus
			module.addDeserializer(TransactionStatus.class, new JsonDeserializer<TransactionStatus>() {

				@Override
				public TransactionStatus deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return TransactionStatus.getByValue(p.getValueAsInt());
				}
			});
			
			// TransactionType
			module.addDeserializer(TransactionType.class, new JsonDeserializer<TransactionType>() {

				@Override
				public TransactionType deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return TransactionType.getByValue(p.getValueAsInt());
				}
			});
			
			// WalletType
			module.addDeserializer(WalletType.class, new JsonDeserializer<WalletType>() {

				@Override
				public WalletType deserialize(JsonParser p, DeserializationContext ctxt)
						throws IOException, JsonProcessingException {
					return WalletType.getByValue(p.getValueAsInt());
				}
			});
			
			objectMapper.registerModule(module);			
		}

		
		return objectMapper;
	}
	
}
