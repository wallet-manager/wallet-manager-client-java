package dev.m18.walletmanager.client.entities;

import dev.m18.walletmanager.client.enums.ChainType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Builder
@Data
@EqualsAndHashCode(callSuper=false)
public class GetAllLatestBlocksOptions{

	ChainType chainType;  
	Long chainId;
	
}
