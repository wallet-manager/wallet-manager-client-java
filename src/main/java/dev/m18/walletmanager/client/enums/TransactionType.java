package dev.m18.walletmanager.client.enums;

import lombok.Getter;

public enum TransactionType implements IntEnum{

	ClientDeposit(1),
	HotWalletDeposit(2),
	InvokerDeposit(3),
	MerchantDeposit(4),
	LOSS10(10),
	CAUTION11(11),
	SweepToHotWallet(12),
	CAUTION13(13),
	Sweep(14),
	SweepFee(19),
	Withdraw(20),
	WithdrawDeposit(21),
	HotWalletTransfer(22),
	CAUTION23(23),
	CAUTION24(24),
	WithdrawFee(29),
	LOSS30(30),
	ClientWalletTopup(31),
	HotWalletTopup(32),
	InvokerTransfer(33),
	CAUTION34(34),
	TopupFee(39),
	MerchantWithdraw(40),
	CAUTION41(41),
	ProvisionForWithdraw(42),
	CAUTION43(43),
	MerchantTransfer(44),
	MerchantWithdrawFee(49);
	
	@Getter
	private final int value; 
	
	private TransactionType(int value) {
		this.value = value;
	}
	
	
	@Override
	public Integer getIntVal() {
		return this.value;
	}

	
	public static TransactionType getByValue(int value) {
		for(TransactionType e : TransactionType.values()) {
			if(e.getIntVal() == value) {
				return e;
			}
		}
		return null;
	}
}
