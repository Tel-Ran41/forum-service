package telran.java41.security.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import telran.java41.accounting.model.UserAccount;

@Service
public class SessionServiceImpl implements SessionService {
	Map<String, UserAccount> users = new ConcurrentHashMap<>();

	@Override
	public UserAccount addUser(String userName, UserAccount userAccount) {
		return users.put(userName, userAccount);
	}

	@Override
	public UserAccount getUser(String userName) {
		return users.get(userName);
	}

	@Override
	public UserAccount removeUser(String userName) {
		return users.remove(userName);
	}
}