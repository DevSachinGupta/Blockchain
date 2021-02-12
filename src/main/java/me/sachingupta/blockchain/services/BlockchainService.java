package me.sachingupta.blockchain.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import me.sachingupta.blockchain.modals.Block;
import me.sachingupta.blockchain.modals.TransactionOutput;

public class BlockchainService {
	
	private static Logger log = LoggerFactory.getLogger(BlockchainService.class);
	
	private static ArrayList<Block> chain = new ArrayList<Block>();
	public static HashMap<String, TransactionOutput> UTXOs = new HashMap<String, TransactionOutput>(); //list of all unspent transactions. 
	
	@Value("#{difficultyLevel}")
	private static int difficulty = 4;

	public static float minimumTransaction;
	
	public boolean createBlock(String data) {
		
		if(chain.isEmpty()) {
			chain.add(new Block(data, "0"));
		} else {
			chain.add(new Block(data, chain.get(chain.size()-1).getHash()));
		}
		
		log.info("Trying to Mine block ... ");
		chain.get(chain.size() -1 ).mineBlock(difficulty);
		
		log.info("Trying to validate the chain");
		boolean status = validateChain(chain);

		log.info("The blockchain is " + status);
		
		return true;
	}
	
	public boolean validateChain(List<Block> blockchain) {
		Block currentBlock; 
		Block previousBlock;
		
		//loop through blockchain to check hashes:
		for(int i=1; i < blockchain.size(); i++) {
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			//compare registered hash and calculated hash:
			if(!currentBlock.getHash().equals(currentBlock.calculateHash()) ){
				System.out.println("Current Hashes not equal");			
				return false;
			}
			//compare previous hash and registered previous hash
			if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()) ) {
				System.out.println("Previous Hashes not equal");
				return false;
			}
		}
		return true;
	}
}
