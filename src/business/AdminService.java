package business;

import java.util.ArrayList;

import beans.AuctionItemBean;
import beans.UserBean;
import beans.WishlistItemBean;

public interface AdminService {
	
	UserBean login(String username, String password) throws UserLoginFailedException;
	
	boolean haltAuctionById(String id);

	boolean unhaltAuctionById(String itemIdToUnhalt);
	
	AuctionItemBean deleteItemById(String id);
	
	boolean banUserById(int uid);

	boolean unbanUserById(int userId);
	
	ArrayList<UserBean> getAllUsers();

	ArrayList<UserBean> getAllRegularUsers();

	ArrayList<WishlistItemBean> getAllWishlists();

}
