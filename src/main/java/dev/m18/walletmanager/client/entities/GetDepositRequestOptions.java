package dev.m18.walletmanager.client.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class GetDepositRequestOptions implements Pagination{

	String blockHash; 
	Boolean txStatus; 
	Boolean	valid; 
	Integer offset;
	Integer limit;
	
}
