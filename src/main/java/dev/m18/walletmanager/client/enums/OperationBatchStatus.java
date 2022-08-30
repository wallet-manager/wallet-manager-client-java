package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum OperationBatchStatus implements IntEnum{

    SignFailed(-3),
    Failed(-2),
    Rejected(-1),
    Requested(1),
    Submitted(2),
    Signed(6),
    Executing(3),
    Completed(4),
    PartialCompleted(5);
    
	
	@Getter
	private final int value; 
	
	private OperationBatchStatus(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static OperationBatchStatus getByValue(int value) {
		for(OperationBatchStatus e : OperationBatchStatus.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
