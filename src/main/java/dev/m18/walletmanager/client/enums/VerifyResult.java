package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum VerifyResult implements IntEnum{
	
	InvalidAddress(-2),
    Expired(-1),
    SignatureNotMatch(0),
    Verified(1);

	@Getter
	final private int value;
	
	VerifyResult(int value){
		this.value = value;
	}

	@Override
	public Integer getIntVal() {
		return this.value;
	}

}
