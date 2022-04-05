package telran.java41.security.context;

import telran.java41.accounting.model.UserAccount;

public interface SessionService {
	UserAccount addUser(String userName, UserAccount userAccount);

	UserAccount getUser(String userName);

	UserAccount removeUser(String userName);

}
