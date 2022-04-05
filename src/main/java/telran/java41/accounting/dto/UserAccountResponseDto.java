package telran.java41.accounting.dto;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserAccountResponseDto {
	String login;
	String firstName;
	String lastName;
	LocalDateTime dateChangePassword;
	@Singular
	Set<String> roles;
}
