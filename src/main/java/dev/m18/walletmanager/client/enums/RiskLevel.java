package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum RiskLevel implements IntEnum{

    NoAssessment(-1),
    NoRiskDetected(0),
    Info(1),
    Caution(2),
    Medium(3),
    High(4),
    Critical(5);
	
	@Getter
	private final int value; 
	
	private RiskLevel(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static RiskLevel getByValue(int value) {
		for(RiskLevel e : RiskLevel.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
