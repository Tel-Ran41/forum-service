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

@Service
@Order(19)
public class UserDelFilter implements Filter {

	UserAccountRepository repository;

	@Autowired
	public UserDelFilter(UserAccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		if (checkEndPoint(request.getMethod(), request.getServletPath())) {
			String pathLogin = request.getServletPath().split("/")[3];
			String principalLogin = request.getUserPrincipal().getName();
			if (!principalLogin.equals(pathLogin)) {
				UserAccount userAccount = repository.findById(principalLogin).get();
				if (!userAccount.getRoles().contains("Administrator".toUpperCase())) {
					response.sendError(403);
					return;
				}
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return path.matches("/account/user/\\w+/?") && ("DELETE".equalsIgnoreCase(method));
	}
}