package dev.m18.walletmanager.client.entities;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Pagination {
	
	Integer offset;
	Integer limit;

}
