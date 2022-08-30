package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum OperationTransactionStatus implements IntEnum{

    BroadcastFailed(-2),
    ConfirmedFail(-1),
    Signed(1),
    Broadcasted(2),
    Unconfirmed(3),
    ConfirmedSuccess(4);
    
	
	@Getter
	private final int value; 
	
	private OperationTransactionStatus(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static OperationTransactionStatus getByValue(int value) {
		for(OperationTransactionStatus e : OperationTransactionStatus.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
