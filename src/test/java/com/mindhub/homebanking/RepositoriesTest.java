package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RepositoriesTest {

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Test
    public void amountAccounts(){
        Client clientCurrent = clientRepository.findByEmail("melba.lorenzo@mindhub.com");
        Set<Account> accounts = clientCurrent.getAccounts();
        assertThat(accounts.size(),lessThan(3));
    }

    @Test
    public void clientLoans(){
        Client clientCurrent = clientRepository.findByEmail("melba.lorenzo@mindhub.com");
        assertThat(clientCurrent.getLoan().size(),greaterThan(0));
    }

    @Test
    public void amountAccounts2(){
        Client clientCurrent = clientRepository.findByEmail("melba.lorenzo@mindhub.com");
        Set<Account> accounts = clientCurrent.getAccounts();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void amountCards(){
        Client clientCurrent =clientRepository.findByEmail("melba.lorenzo@mindhub.com");
        Set<Card> cards = clientCurrent.getCards();
        Set <Card> cardDebit=cards.stream().filter(card ->card.getType()== CardType.DEBIT ).collect(Collectors.toSet());
        assertThat(cardDebit.size(),lessThan(3));
    }

    @Test
    public void cardTypeClient(){
        Client clientCurrent =clientRepository.findByEmail("melba.lorenzo@mindhub.com");
        Set<Card> cards = clientCurrent.getCards();
        List <Card> cardDebit=cards.stream().filter(card ->card.getType()== CardType.DEBIT ).collect(Collectors.toList());
        assertThat(cardDebit,hasItem(hasProperty("cvv", is(either(greaterThan(000)).or(lessThan(1000))))));
    }

    @Test
    public void existAccounts(){
        List <Account> accounts = accountRepository.findAll();
        assertThat(accounts,is(not(empty())));
    }

    @Test
    public void accountNumber(){
        List<Account> accounts=accountRepository.findAll();
        assertThat(accounts,hasItem(hasProperty("accountType", is("CURRENT"))));
    }

    @Test
    public void cardNumber(){
        List<Card> cards=cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("number", is(not(empty())))));
    }

    @Test
    public void cardCVV(){
        List<Card> cards=cardRepository.findAll();
        assertThat(cards,hasItem(hasProperty("cvv", is(either(greaterThan(0)).or(lessThan(00))))));
    }


   @Test
    public void existLoans(){
        List <Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,hasItem(hasProperty("name", is("Personal"))));
    }

}
