package telran.java41.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import telran.java41.forum.dao.PostRepository;
import telran.java41.forum.dto.exceptions.PostNotFoundException;
import telran.java41.forum.model.Post;
import telran.java41.security.context.SecurityContext;
import telran.java41.security.context.User;

@Service
@Order(21)
@AllArgsConstructor
public class PostDelFilter implements Filter {

	PostRepository postRepository;
	SecurityContext context;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			String[] arrPathElem = request.getServletPath().split("/");
			String pathId = arrPathElem[arrPathElem.length - 1];
			Post post = postRepository.findById(pathId).orElseThrow(() -> new PostNotFoundException(pathId));
			String principalLogin = request.getUserPrincipal().getName();
			if (!principalLogin.equals(post.getAuthor())) {
				User user = context.getUser(principalLogin);
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