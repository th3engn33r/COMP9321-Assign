package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.AuctionItemBean;

public interface AuctionService {
	
	AuctionItemBean getItemById(String id);
	
	AuctionItemBean getItemByIdALL(String id);
	
	AuctionItemBean addItem(AuctionItemBean item);
	
	void deleteItem(String id);
	
	AuctionItemBean updatePriceToZero(AuctionItemBean item);

	ArrayList<AuctionItemBean> getItemBySearchKey(String searchKey);
	
	ArrayList<AuctionItemBean> getAllAuctionItems();
	
	ArrayList<AuctionItemBean> getAllActiveAuctionItems();
	
	ArrayList<AuctionItemBean> getAllAuctionItemsByOwner(int uid);
	
	List<AuctionItemBean> getTenRandomAuctionItems();

	AuctionItemBean updateBidPrice(AuctionItemBean item, Float price);

	ArrayList<AuctionItemBean> getItemBySearchKeyAdv(String searchKey,
			String category, float minPrice, float maxPrice);

	ArrayList<AuctionItemBean> getClosedAuctionItemsByOwner(int uid, Date date);
}
