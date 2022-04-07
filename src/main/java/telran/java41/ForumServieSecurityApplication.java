package telran.java41;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.java41.accounting.dao.UserAccountRepository;
import telran.java41.accounting.model.UserAccount;

@SpringBootApplication
public class ForumServieSecurityApplication implements CommandLineRunner {

	@Autowired
	UserAccountRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ForumServieSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!repository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			UserAccount userAccount = new UserAccount("admin", password, "", "");
			userAccount.addRole("MODERATOR");
			userAccount.addRole("ADMINISTRATOR");
			userAccount.setDateChangePassword(LocalDate.now().plusYears(100));
			repository.save(userAccount);
		}

	}
}