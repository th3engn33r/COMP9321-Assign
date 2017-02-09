package dao;

import java.util.ArrayList;
import java.util.Date;

import beans.AuctionItemBean;

public interface AuctionItemDAO {
	
	AuctionItemBean getAuctionItemById(String id);
	
	AuctionItemBean getAuctionItemByIdALL(String id);
	
	AuctionItemBean addAuctionItem(AuctionItemBean item);
	
	AuctionItemBean deleteAuctionItemById(String id);

	ArrayList<AuctionItemBean> getAuctionItemBySearchKey(String searchKey);
	
	AuctionItemBean updatePriceToZero(AuctionItemBean item);
	
	ArrayList<AuctionItemBean> getAllAuctionItems();
	
	boolean haltAuctionItemById(String id);
	
	boolean unhaltAuctionItemById(String id);

	ArrayList<AuctionItemBean> getAllAuctionItemsByOwner(int uid);

	AuctionItemBean updateCurrentPrice(AuctionItemBean item, Float price);

	ArrayList<AuctionItemBean> getAuctionItemBySearchKeyAdv(String searchKey,
			String category, float minPrice, float maxPrice)
			throws DataAccessException;

	ArrayList<AuctionItemBean> getClosedAuctionItemsByOwner(int uid);
	
}