package telran.java41.accounting.dto.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(HttpStatus.CONFLICT)
public class DateChangePasswordExeption extends RuntimeException {
	
	/**
	* 
	*/
	private static final long serialVersionUID = -9116331476173938501L;

	public DateChangePasswordExeption(String login, LocalDateTime dateChangePassword) {
		super("You need to change the password for the user " + login + " " + dateChangePassword);
	}
}