package dev.m18.walletmanager.client;

import dev.m18.walletmanager.client.entities.Config;

public class ConfigUnitTest {
	
	protected Config config;
	
    public void setUp() throws Exception {
    	
    	this.config = Config.read();
			
    }

}
