package dev.m18.walletmanager.client.entities;

import lombok.Data;

@Data
public class Response<T> {

	T result;
	Error<?> error;
	
}
