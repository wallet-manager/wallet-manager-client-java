package dev.m18.walletmanager.client.entities;

import java.util.List;

import lombok.Data;

@Data
public class GetDepositByAddressResult {

	List<TransferTransaction> transactions;	
}
