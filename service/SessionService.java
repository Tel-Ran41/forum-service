package telran.java41.security.service;

import telran.java41.accounting.model.UserAccount;

public interface SessionService {
	UserAccount addUser(String sessionId, UserAccount userAccount);

	UserAccount getUser(String sessionId);

	UserAccount removeUser(String sessionId);

}
