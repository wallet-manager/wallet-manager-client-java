package dev.m18.walletmanager.client.entities.callback;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.m18.walletmanager.client.enums.ChainType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class BlockNumberCallback implements Callback {

	public final static String BLOCK_NUMBER = "block_number";

	BlockNumberCallbackData data;

	BlockNumberCallback() {

	}

	@Override
	public String getType() {
		return BLOCK_NUMBER;
	}

	@Data
	public static class BlockNumberCallbackData {

		@JsonProperty("chain_type")
		ChainType chainType;

		@JsonProperty("chain_id")
		Long chainId;

		@JsonProperty("block_number")
		Long blockNumber;

		String hash;

		Long time;

		Integer confirmations;

		Boolean confirmed;

	}

}
