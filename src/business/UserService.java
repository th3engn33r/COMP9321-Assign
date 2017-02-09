package business;

import beans.UserBean;

public interface UserService {
	UserBean login(String username, String password) throws UserLoginFailedException;
	
	boolean isUsernameAvailable(String username);
	
	UserBean editProfile(UserBean user);

	boolean confirmUserById(int userId);

	UserBean findById(int id) throws UserLoginFailedException;
}