package telran.java41.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class UserExistsException extends RuntimeException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -7934715644687565584L;

	public UserExistsException(String login) {
		super("User " + login + " exists");
	}

}
