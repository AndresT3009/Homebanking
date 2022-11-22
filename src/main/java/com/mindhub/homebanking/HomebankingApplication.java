package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository,PasswordEncoder passwordEncoder){
		return args -> {
			Client client1= new Client("Melba","Lorenzo","melba.lorenzo@mindhub.com",passwordEncoder.encode("1"));
			Client client2= new Client("Darwin","Tangarife","darwin.tangarife@gmail.com",passwordEncoder.encode("2"));
			Client client3= new Client("Nata","Zapata","nata.zapata@gmail.com",passwordEncoder.encode("3"));

			Account account1 = new Account("VIN001", LocalDateTime.now(),5000,"SAVINGS");
			Account account2 = new Account("VIN002", LocalDateTime.now().plusDays(1), 8500,"SAVINGS");
			Account account3 = new Account("VIN003", LocalDateTime.now(), 8500,"CURRENT");
			Account account4 = new Account("VIN004", LocalDateTime.now(), 28500,"CURRENT");

			Transaction transaction1= new Transaction(DEBIT, -3000.0,"University Payment",LocalDateTime.now());
			Transaction transaction2= new Transaction(CREDIT, 500.0,"Salary Payment",LocalDateTime.now());
			Transaction transaction3= new Transaction(DEBIT, -5000.0,"Buy market",LocalDateTime.now());
			Transaction transaction4=new Transaction(CREDIT,8000.0,"Rental income",LocalDateTime.now());

			List<Integer> paymentHipotecario = List.of(12,24,36,48,60);
			List<Integer> paymentPersonal= List.of(6,12,24);
			List<Integer> paymentAutomotriz= List.of(6,12,24,36);

			Loan Mortgage= new Loan("Mortgage",500000.00,paymentHipotecario, 1.2);
			Loan Personal= new Loan("Personal",100000.00,paymentPersonal,1.08);
			Loan Vehicle= new Loan("Vehicle",300000.00,paymentAutomotriz,1.14);



			Client client4= new Client("Nata","Zapata","andres.avendaño@gmail.com",passwordEncoder.encode("3"));
			clientRepository.save(client4);
			Card card5 =new Card("Darwin Tangarife",CardType.CREDIT,CardColor.TITANIUM,"2345-3430-0458-2108",654,LocalDate.now().minusMonths(2),LocalDate.now().minusDays(1));
			client4.addCards(card5);

			cardRepository.save(card5);




			ClientLoan clientLoan1=new ClientLoan(client1,Mortgage,400000.00,60);
			ClientLoan clientLoan2=new ClientLoan(client1,Personal,50000.00,12);
			ClientLoan clientLoan3=new ClientLoan(client2,Personal,100000.00,24);
			ClientLoan clientLoan4=new ClientLoan(client2,Vehicle,200000.00,36);
			ClientLoan clientLoan5=new ClientLoan(client1,Vehicle,100000.00,12);

			Card card1 = new Card("Melba Lorenzo",CardType.DEBIT,CardColor.GOLD,"0013-5890-4534-3492",123,LocalDate.now(), LocalDate.now().plusYears(5));
			Card card2 = new Card("Melba Lorenzo",CardType.CREDIT,CardColor.TITANIUM,"0049-3395-8945-9596",456,LocalDate.now(),LocalDate.now().plusYears(5));
			Card card3 = new Card("Darwin Tangarife",CardType.CREDIT,CardColor.SILVER,"2345-2344-4567-5367-",789,LocalDate.now(),LocalDate.now().plusYears(5));
			Card card4 =new Card("Nata Zapata",CardType.DEBIT,CardColor.TITANIUM,"2345-3430-0458-2108",654,LocalDate.now(),LocalDate.now().plusYears(5));

			client1.addAccount(account1);
			client1.addAccount(account2);
			client2.addAccount(account3);
			client3.addAccount(account4);

			client1.addCards(card1);
			client1.addCards(card2);
			client2.addCards(card3);
			client3.addCards(card4);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction4);
			account2.addTransaction(transaction3);
			account3.addTransaction(transaction2);

			clientRepository.save(client1);
			clientRepository.save(client2);
			clientRepository.save(client3);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

			loanRepository.save(Mortgage);
			loanRepository.save(Personal);
			loanRepository.save(Vehicle);

			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);
			clientLoanRepository.save(clientLoan5);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);
			cardRepository.save(card4);
		};
	}
}
