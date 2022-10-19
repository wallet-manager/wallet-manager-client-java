package dev.m18.walletmanager.client;

public class WalletManagerConstants {

	public static final int HTTP_CONNECTION_TIMEOUT_MS = 10000;
	public static final int HTTP_READ_TIMEOUT_MS = 20000;
	
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ADDRESS = "X-Message-Address";
    public static final String HEADER_TIMESTAMP = "X-Message-Timestamp";
    public static final String HEADER_SESSION = "X-Message-Session";
    public static final String HEADER_SEQUENCE = "X-Message-Sequence";
    public static final String HEADER_SIGNATURE = "X-Message-Signature";
    
    
    public static class ChainId{
    	public static final Long DefaultMainnet = 1L;
    	public static final Long DefaultTestnet = 2L;
        public static final Long Ethereum = 1L;
        public static final Long Rinkeby = 4L;
        public static final Long BSC = 56L;
        public static final Long BSCT = 97L;
        public static final Long Polygon = 137L;
        public static final Long Mumbai = 80001L;
        public static final Long Sepolia = 11155111L;
    	
    }
	
}
