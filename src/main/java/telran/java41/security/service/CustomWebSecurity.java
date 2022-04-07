package telran.java41.security.service;

import java.time.LocalDate;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java41.accounting.dao.UserAccountRepository;
import telran.java41.accounting.model.UserAccount;
import telran.java41.forum.dao.PostRepository;
import telran.java41.forum.model.Post;
import telran.java41.security.context.SessionService;

@Service("customSecurity")
@AllArgsConstructor
public class CustomWebSecurity {

	PostRepository postRepository;
	UserAccountRepository userRepository;
	SessionService sessionService;

	public boolean checkPostAuthority(String postId, String userName) {
		Post post = postRepository.findById(postId).orElse(null);
		return post != null && userName.equals(post.getAuthor());
	}

	public boolean checkDateChangePassword(String userName) {
		UserAccount userAccount = sessionService.getUser(userName);
		if(userAccount == null) {
			userAccount = userRepository.findById(userName)
				.orElseThrow(() -> new UsernameNotFoundException(userName));
			sessionService.addUser(userName, userAccount);
		}
		if (LocalDate.now().isBefore(userAccount.getDateChangePassword())) {
			return true;
		}
		sessionService.removeUser(userName);
//		throw new DateChangePasswordExeption(userAccount.getLogin(), userAccount.getDateChangePassword());
		return false;
	}
}