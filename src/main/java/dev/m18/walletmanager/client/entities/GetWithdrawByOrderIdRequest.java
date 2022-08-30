package dev.m18.walletmanager.client.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetWithdrawByOrderIdRequest {

	@JsonProperty("merchant_order_id")
	String merchantOrderId;
	
}
