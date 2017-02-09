package business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import beans.BidBean;

public interface BidService {
	
	BidBean getBidItemById(String id);
	
	BidBean addBidItem(BidBean item);
	
	BidBean updateBid(String itemid, int bidderid, Float price, Date date);
	
	void deleteBidItem(String id);
	
	ArrayList<BidBean> getAllBidItems();
	
	ArrayList<BidBean> getBidItemsByUser(int userid);

	ArrayList<BidBean> getBidItemsByUserAndStatus(int userid, int status);

	ArrayList<BidBean> getAllWinBidItems();

	BidBean getWinBidItemById(String id);

	BidBean updateBidStatus(String itemid, int newstatus, int oldstatus);

	BidBean updateBidStatusFrom2(String itemid, int newstatus);
}
