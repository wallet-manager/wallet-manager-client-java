package dev.m18.walletmanager.client.entities;

import lombok.Data;

@Data
public class ClientConfig {
	int instanceId;
	String baseURL;
	boolean contentTypeJson;
}