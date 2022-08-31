package dev.m18.walletmanager.client.entities;

import lombok.Data;

@Data
public class ServerConfig {
	int serverPort;
	int messageExpiredInMs;

}