package dao;

import java.util.HashMap;
import java.util.Map;

import dao.support.AuctionItemDAOImpl;
import dao.support.UserDAOImpl;

public class DAOFactory {

private static final String USER_DAO = "userDAO";
	
	private static final String AUCTION_ITEM_DAO = "contactDAO";
	
	private Map daos;
	
	private static DAOFactory instance = new DAOFactory();
	
	/** Creates a new instance of DAOFactory */
	private DAOFactory() {
		daos = new HashMap();
		daos.put(USER_DAO, new UserDAOImpl());
		daos.put(AUCTION_ITEM_DAO, new AuctionItemDAOImpl());
	}
	
	/**
	 * Finds the user dao
	 * @return
	 */
	public UserDAO getUserDAO() {
		return (UserDAO) daos.get(USER_DAO);
	}

	/**
	 * Retrieves the contacts dao
	 * @return
	 */
	public AuctionItemDAO getContactDAO() {
		return (AuctionItemDAO) daos.get(AUCTION_ITEM_DAO);
	}
	
	public static DAOFactory getInstance() {
		return instance;
	}
	
}
