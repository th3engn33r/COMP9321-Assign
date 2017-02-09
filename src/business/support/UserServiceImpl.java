package business.support;

import dao.DataAccessException;
import dao.UserDAO;
import dao.support.UserDAOImpl;
import beans.AuctionItemBean;
import beans.UserBean;
import business.UserLoginFailedException;
import business.UserService;

public class UserServiceImpl implements UserService{

	private UserDAO userDao;
	
	public UserServiceImpl() {
		userDao= new UserDAOImpl();
	}
	
	public UserBean login(String username, String password)
			throws UserLoginFailedException {
		UserBean user = null;
		
		try {
			user = userDao.findByLoginDetails(username, password);
			return user;
		} catch (DataAccessException e) {
			throw new UserLoginFailedException("Unable to find user", e);
		}
	}
	

	public UserBean findById(int id)
			throws UserLoginFailedException {
		UserBean user = null;
		
		try {
			user = userDao.findByID(id);
			return user;
		} catch (DataAccessException e) {
			throw new UserLoginFailedException("Unable to find user", e);
		}
	}
	
	public UserBean register(UserBean user){
		
		UserBean newUser = userDao.addNewUser(user);
		return newUser;
	}
	
	public UserBean editProfile(UserBean user){
		
		UserBean currentUser = userDao.addNewUser(user);
		return currentUser;
	}
	
	public boolean isUsernameAvailable(String username) {
		boolean isAvailable = userDao.isUsernameAvailable(username);
		return isAvailable;
	}
	
	public boolean confirmUserById(int userId){
		boolean user=userDao.unbanUserById(userId);
		return user;
	}
}
