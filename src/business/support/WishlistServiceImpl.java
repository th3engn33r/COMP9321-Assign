package business.support;

import java.util.ArrayList;

import dao.WishlistDAO;
import dao.support.WishlistDAOImpl;
import beans.AuctionItemBean;
import beans.WishlistItemBean;
import business.WishlistService;

public class WishlistServiceImpl implements WishlistService{
	
	private WishlistDAO wishlistDao;
	
	public WishlistServiceImpl(){
		wishlistDao = new WishlistDAOImpl();
	}
	public ArrayList<WishlistItemBean> getWishlistFromUserId(int userId){
		ArrayList<WishlistItemBean> wishlist = wishlistDao.getWishlistFromUserId(userId);
		return wishlist;
	}
	
	public ArrayList<WishlistItemBean> getWishlistFromItemId(String itemId){
		ArrayList<WishlistItemBean> wishlist = wishlistDao.getWishlistFromItemId(itemId);
		return wishlist;
	}
	
	
	
	public String addToWishlist(String itemId, int userId) {
		String addedItem = wishlistDao.addToWishlist(itemId,userId);
		return addedItem;
	}
	public WishlistItemBean deleteWishlistItemById(int id) {
		WishlistItemBean deletedItem = wishlistDao.deleteWishlistItemById(id);
		return deletedItem;
	}
	
	public WishlistItemBean deleteWishlistItemByItemAndUser(String itemId,int userId) {
		WishlistItemBean deletedItem = wishlistDao.deleteWishlistItemByItemAndUser(itemId, userId);
		return deletedItem;
	}
	
	
	public boolean deleteWishlistItemByItemId(String itemId){
		boolean deleted = wishlistDao.deleteWishlistItemByItemId(itemId);
		return deleted;
	}
}
