package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum Direction implements IntEnum{

    In(1),
    Out(2);
	
	@Getter
	private final int value; 
	
	private Direction(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static Direction getByValue(int value) {
		for(Direction e : Direction.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
