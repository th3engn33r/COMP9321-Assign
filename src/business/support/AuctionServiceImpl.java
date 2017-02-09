package business.support;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.AuctionItemDAO;
import dao.support.AuctionItemDAOImpl;
import beans.AuctionItemBean;
import business.AuctionService;

public class AuctionServiceImpl implements AuctionService{

	private AuctionItemDAO auctionItemDao;
	
	public AuctionServiceImpl(){
		auctionItemDao = new AuctionItemDAOImpl();
	}
	
	
	public AuctionItemBean addItem(AuctionItemBean item) {
		AuctionItemBean addedItem = auctionItemDao.addAuctionItem(item);
		return addedItem;
	}

	
	public AuctionItemBean getItemById(String id) {
		AuctionItemBean item = auctionItemDao.getAuctionItemById(id);
		return item;
	}
	
	
	public AuctionItemBean getItemByIdALL(String id) {
		System.out.println("service");
		AuctionItemBean item = auctionItemDao.getAuctionItemByIdALL(id);
		return item;
	}
	
	public AuctionItemBean updatePriceToZero(AuctionItemBean item){
		AuctionItemBean updated = auctionItemDao.updatePriceToZero(item);
		return item;
	}
	
	
	public AuctionItemBean updateBidPrice(AuctionItemBean item, Float price){
		AuctionItemBean updated = auctionItemDao.updateCurrentPrice(item, price);
		return item;
	}

	
	public ArrayList<AuctionItemBean> getItemBySearchKey(String searchKey) {
		ArrayList<AuctionItemBean> searchResults = auctionItemDao.getAuctionItemBySearchKey(searchKey);
		return searchResults;
	}
	
	
	public ArrayList<AuctionItemBean> getItemBySearchKeyAdv(String searchKey, String category, float minPrice, float maxPrice) {
		ArrayList<AuctionItemBean> searchResults = auctionItemDao.getAuctionItemBySearchKeyAdv(searchKey, category, minPrice, maxPrice);
		return searchResults;
	}
	
	
	public ArrayList<AuctionItemBean> getAllAuctionItems() {
		ArrayList<AuctionItemBean> allItems = auctionItemDao.getAllAuctionItems();
		return allItems;
	}
	
	
	public ArrayList<AuctionItemBean> getAllActiveAuctionItems() {
		ArrayList<AuctionItemBean> allItems = auctionItemDao.getAllAuctionItems();
		ArrayList<AuctionItemBean> allActiveItems = new ArrayList<AuctionItemBean>();
		for(int i = 0 ; i < allItems.size(); i++){
			if(allItems.get(i).getEndTime().getTime() > System.currentTimeMillis())
				allActiveItems.add(allItems.get(i));
		}
		return allActiveItems;
	}
	
	
	public List<AuctionItemBean> getTenRandomAuctionItems() {
		ArrayList<AuctionItemBean> allItems = getAllAuctionItems();
		List<AuctionItemBean> shuffledItems = new LinkedList<AuctionItemBean>(allItems);
		Collections.shuffle(shuffledItems);
		return shuffledItems.subList(0, 10);
	}
	
	
	public void deleteItem(String id) {
		auctionItemDao.deleteAuctionItemById(id);
	}
	
	
	public ArrayList<AuctionItemBean> getAllAuctionItemsByOwner(int uid){
		return auctionItemDao.getAllAuctionItemsByOwner(uid);
	}
	
	
	public ArrayList<AuctionItemBean> getClosedAuctionItemsByOwner(int uid, Date date){
		return auctionItemDao.getClosedAuctionItemsByOwner(uid);
	}

}
