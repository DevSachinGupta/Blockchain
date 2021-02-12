package me.sachingupta.blockchain.modals;

import java.util.Date;

import lombok.Data;
import me.sachingupta.blockchain.utils.CommanUtility;

@Data
public class Block {
	
//	private Object data;
//	private Optional<Object> additionalData;
	
	private String data;
	
	private String hash;
	private String previousHash;
	
	private long blockNumber;
	private long timestamp;
	private long nonce;
	
	public Block(String data, String previousHash) {
		this.data = data;
		this.previousHash = previousHash;
		
		this.timestamp = new Date().getTime();
		this.hash = calculateHash();
		
	}
	
	public String calculateHash() {
		return CommanUtility.generateHash(this.previousHash + String.valueOf(this.timestamp) + Long.toString(nonce) + this.data);
	}
	
	public void mineBlock(int difficulty) {
		String target = new String(new char[difficulty]).replace('\0', '0'); //Create a string with difficulty * "0" 
		while(!hash.substring( 0, difficulty).equals(target)) {
			nonce ++;
			hash = calculateHash();
		}
		System.out.println("Block Mined!!! : " + hash);
	}
}
