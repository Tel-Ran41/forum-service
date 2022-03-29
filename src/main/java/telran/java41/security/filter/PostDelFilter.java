package telran.java41.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.java41.accounting.dao.UserAccountRepository;
import telran.java41.accounting.model.UserAccount;
import telran.java41.forum.dao.PostRepository;
import telran.java41.forum.dto.exceptions.PostNotFoundException;
import telran.java41.forum.model.Post;

@Service
@Order(21)
public class PostDelFilter implements Filter {

	PostRepository postRepository;
	UserAccountRepository userRepository;

	@Autowired
	public PostDelFilter(PostRepository postRepository, UserAccountRepository userRepository) {
		this.postRepository = postRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			String pathId = request.getServletPath().split("/")[3];
			Post post = postRepository.findById(pathId).orElseThrow(() -> new PostNotFoundException());
			String principalLogin = request.getUserPrincipal().getName();
			if (!principalLogin.equals(post.getAuthor())) {
				UserAccount user = userRepository.findById(principalLogin).get();
				if (!user.getRoles().contains("Moderator".toUpperCase())) {
					response.sendError(403);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return path.matches("/forum/post/\\w+/?") && ("DELETE".equalsIgnoreCase(method));
	}
}