package business.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.BidDAO;
import dao.support.BidDAOImpl;
import beans.BidBean;
import business.BidService;

public class BidServiceImpl implements BidService{

	private BidDAO bidDao;
	
	public BidServiceImpl(){
		bidDao = new BidDAOImpl();
	}
	
	
	public BidBean addBidItem(BidBean item) {
		BidBean addedItem = bidDao.addBidItem(item);
		return addedItem;
	}
	
	
	public BidBean updateBid(String itemid, int bidderid, Float price, Date date){
		BidBean updatedItem = bidDao.updateBid(itemid, bidderid, price, date);
		return updatedItem;
	}
	
	
	public BidBean updateBidStatus(String itemid, int newstatus, int oldstatus){
		BidBean updatedItem = bidDao.updateBidStatus(itemid, newstatus, oldstatus);
		return updatedItem;
	}
	
	
	public BidBean updateBidStatusFrom2(String itemid, int newstatus){
		BidBean updatedItem = bidDao.updateBidStatusFrom2(itemid, newstatus);
		return updatedItem;
	}

	
	public BidBean getBidItemById(String id) {
		BidBean item = bidDao.getBidItemById(id);
		return item;
	}
	

	public BidBean getWinBidItemById(String id) {
		BidBean item = bidDao.getWinBidItemById(id);
		return item;
	}

	
	public ArrayList<BidBean> getAllBidItems() {
		ArrayList<BidBean> allItems = bidDao.getAllBidItems();
		return allItems;
	}
	
	
	public ArrayList<BidBean> getAllWinBidItems() {
		ArrayList<BidBean> allItems = bidDao.getAllWinBidItems();
		return allItems;
	}
	
	
	public void deleteBidItem(String id) {
		bidDao.deleteBidItemById(id);
	}
	
	public void deleteAllBidItems(){
		System.out.println("delete All Bid Items");
		bidDao.deleteAllBidItems();
	}
	
	public ArrayList<BidBean> getBidItemsByUser(int userid){
		return bidDao.getBidItemsByUser(userid);
	}
	
	
	public ArrayList<BidBean> getBidItemsByUserAndStatus(int userid, int status){
		return bidDao.getBidItemsByUserAndStatus(userid, status);
	}

}
