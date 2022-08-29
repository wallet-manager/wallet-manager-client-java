package dev.m18.walletmanager.client.entities;

import lombok.Data;

@Data
public class Error<T> {
	
	Integer code;
	String message;
	T data;

}
