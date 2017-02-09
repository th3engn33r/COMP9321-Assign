package business;

import java.util.ArrayList;

import beans.AuctionItemBean;
import beans.WishlistItemBean;

public interface WishlistService {

	ArrayList<WishlistItemBean> getWishlistFromUserId(int userId);
	
	String addToWishlist(String itemId,int userId);
	
	WishlistItemBean deleteWishlistItemById(int id);

	WishlistItemBean deleteWishlistItemByItemAndUser(String itemId, int userId);

	boolean deleteWishlistItemByItemId(String itemId);
	
	ArrayList<WishlistItemBean> getWishlistFromItemId(String itemId);
}
