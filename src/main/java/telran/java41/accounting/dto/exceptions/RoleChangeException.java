package telran.java41.accounting.dto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class RoleChangeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4267983824443383296L;

	public RoleChangeException(String message) {
		super(message);
	}
}
