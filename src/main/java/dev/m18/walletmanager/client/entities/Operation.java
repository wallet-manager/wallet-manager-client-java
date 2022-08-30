package dev.m18.walletmanager.client.entities;

import java.math.BigInteger;

import dev.m18.walletmanager.client.enums.OperationBatchStatus;
import dev.m18.walletmanager.client.enums.OperationType;
import lombok.Data;

@Data
public class Operation {

	  Long merchant_id;
	  Long batch_id;
	  Long operation_seq;
	  Long merchant_order_id;
	  String path;
	  String from_address;
	  String to_addresss;
	  String invoker_address;
	  OperationType operation_type;
	  BigInteger amount;
	  String asset_name;
	  OperationBatchStatus status;
	  Long create_date;
	  Long last_modified_date;
	
}
