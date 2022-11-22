package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.dtos.CreateLoanDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private LoanService loanService;

    @RequestMapping("/loans")
    public List<LoanDTO> getloansDTO(){
        return loanService.getloansDTO();
    }

    @Transactional
    @PostMapping("/loans")
    public ResponseEntity<?> requestLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Account accountdestiny=accountService.findByNumber(loanApplicationDTO.getAccountnumber());

        if (accountdestiny==null){
            return new ResponseEntity<>("Account Number does not exist", HttpStatus.FORBIDDEN);
        }

        Loan loan = loanService.findById(loanApplicationDTO.getId());

        if (loan==null){
            return new ResponseEntity<>("Credit type does not exist", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount()>loan.getMaxAmount()){
            return new ResponseEntity<>("Unable to lend the requested amount", HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayment().contains(loanApplicationDTO.getPayments())){
            return new ResponseEntity<>("The amount of installments requested is not among those established for this credit", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getId()<= 0){
            return new ResponseEntity<>("Please select credit type", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getPayments()<=0){
            return new ResponseEntity<>("Please select payments", HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAccountnumber().isEmpty()){
            return new ResponseEntity<>("Please select account destiny",HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount()<=0){
            return new ResponseEntity<>("Please select amount", HttpStatus.FORBIDDEN);
        }

        Client clientCurrent=clientService.findByEmail(authentication.getName());

        Set<ClientLoan> loanSet=clientCurrent.getLoan();

        Set<ClientLoan> loans= loanSet.stream().filter(clientLoan -> clientLoan.getLoan()== loan).collect(Collectors.toSet());

        if (loans.size()>= 1 && loan.getName().equals("Mortgage")){
            return new ResponseEntity<>("You already have a mortgage loan",HttpStatus.FORBIDDEN);
        }

        if (loans.size()>= 1 && loan.getName().equals("Personal")){
            return new ResponseEntity<>("You already have a personal loan",HttpStatus.FORBIDDEN);
        }

        if (loans.size()>= 1 && loan.getName().equals("Vehicle")){
            return new ResponseEntity<>("You already have a vehicle loan",HttpStatus.FORBIDDEN);
        }

        if (loanSet.size()>=3){
            return new ResponseEntity<>("You can not request more credits", HttpStatus.FORBIDDEN);
        }

        if (!clientCurrent.getAccounts().contains(accountdestiny)){
            return new ResponseEntity<>("Transaction not allowed", HttpStatus.FORBIDDEN);
        }

        Transaction transaction= new Transaction(CREDIT, loanApplicationDTO.getAmount(),loan.getName()+" "+"Loan Approved", LocalDateTime.now());
        accountdestiny.addTransaction(transaction);
        transactionService.saveTransaction(transaction);

        accountdestiny.setBalance(accountdestiny.getBalance()+loanApplicationDTO.getAmount());

        if(loan.getName().equals("Mortgage")){
            loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()*1.2);
        }

        if(loan.getName().equals("Personal")){
            loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()*1.08);
        }

        if(loan.getName().equals("Vehicle")){
            loanApplicationDTO.setAmount(loanApplicationDTO.getAmount()*1.14);
        }

        ClientLoan clientLoan=new ClientLoan(clientCurrent,loan,loanApplicationDTO.getAmount(),loanApplicationDTO.getPayments());
        clientLoanService.saveLoan(clientLoan);
        return new ResponseEntity<>("Credit approved", HttpStatus.CREATED);
    }

    @PostMapping("/post/loans")
    public ResponseEntity<?> createLoan(Authentication authentication, @RequestBody CreateLoanDTO createLoanDTO){
        if (createLoanDTO.getInterest() == 0 || createLoanDTO.getMaxAmount() == 0 || createLoanDTO.getName().isEmpty() || createLoanDTO.getPayments().isEmpty()){
            return new ResponseEntity<>("Admin data is missing please check again", HttpStatus.FORBIDDEN);
        }
        loanService.saveLoan(new Loan(createLoanDTO.getName(), createLoanDTO.getMaxAmount(), createLoanDTO.getPayments(),createLoanDTO.getInterest()));
        return new ResponseEntity<>("Your loan has been created succesfully", HttpStatus.CREATED);
    }
}
