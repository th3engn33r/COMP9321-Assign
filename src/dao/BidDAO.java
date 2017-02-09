package dao;

import java.util.ArrayList;
import java.util.Date;

import beans.BidBean;

public interface BidDAO {
	BidBean getBidItemById(String item_id);
	
	BidBean addBidItem(BidBean item);
	
	BidBean deleteBidItemById(String id);
	
	BidBean updateBid(String itemid, int bidderid, Float price, Date date);
	
	ArrayList<BidBean> getAllBidItems();
	
	ArrayList<BidBean> getBidItemsByUser(int userid);
	
	BidBean deleteAllBidItems();

	BidBean getWinBidItemById(String item_id);

	BidBean getLoseBidItemById(String item_id);

	ArrayList<BidBean> getAllWinBidItems();

	ArrayList<BidBean> getBidItemsByUserAndStatus(int userid, int status);

	BidBean updateBidStatus(String itemid, int newstatus, int oldstatus);

	BidBean updateBidStatusFrom2(String itemid, int newstatus);
}
