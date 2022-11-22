package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@RestController
@RequestMapping("/api")
public class TransactionController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    AccountService accountService;


    @RequestMapping("/transactions")
    public List<TransactionDTO> getTransactionsDTO(){
        return transactionService.getTransactionsDTO();
    }

    @Transactional
    @PostMapping("/transactions")
    public ResponseEntity<Object> register(
            @RequestParam Double amount, @RequestParam String description,
            @RequestParam String accountOrigin, @RequestParam String accountDestiny, Authentication authentication) {
        if (accountOrigin.isEmpty()) {
            return new ResponseEntity<>("Missing Account Origin", HttpStatus.FORBIDDEN);
        }
        if (accountDestiny.isEmpty()) {
            return new ResponseEntity<>("Missing Account Destiny", HttpStatus.FORBIDDEN);
        }
        if (amount == null || amount <= 0) {
            return new ResponseEntity<>("Missing Amount", HttpStatus.FORBIDDEN);
        }
        if (description.isEmpty()) {
            return new ResponseEntity<>("Missing Description", HttpStatus.FORBIDDEN);
        }
        if (amount>1000){
            return new ResponseEntity<>("Exceed the limit per transaction", HttpStatus.FORBIDDEN);
        }
        if(accountDestiny.equals(accountOrigin)){
            return new ResponseEntity<>("Account destiny and origin are the same", HttpStatus.FORBIDDEN);
        }

        Account accountCurrentDestiny = accountService.findByNumber(accountDestiny);
        if(accountCurrentDestiny == null){
            return new ResponseEntity<>("Destiny account not found", HttpStatus.FORBIDDEN);
        }

        Account accountCurrentorigin = accountService.findByNumber(accountOrigin);
        if(accountCurrentorigin == null){
            return new ResponseEntity<>("Origin account not found", HttpStatus.FORBIDDEN);
        }

        Client clientCurrent = clientService.findByEmail(authentication.getName());
        if (clientCurrent.getAccounts().contains(accountCurrentorigin)== false){
            return new ResponseEntity<>("Origin account is not allowed", HttpStatus.FORBIDDEN);
        }
        if(accountCurrentorigin.getBalance()<amount){
            return new ResponseEntity<>("Your balance is not enough", HttpStatus.FORBIDDEN);
        }

        Transaction transactionDebit= new Transaction(DEBIT, -amount,description, LocalDateTime.now());
        Transaction transactionCredit= new Transaction(CREDIT, amount,description, LocalDateTime.now());

        accountCurrentorigin.addTransaction(transactionDebit);
        accountCurrentDestiny.addTransaction(transactionCredit);

        accountCurrentorigin.setBalance(accountCurrentorigin.getBalance()-amount);
        accountCurrentDestiny.setBalance(accountCurrentDestiny.getBalance()+amount);

        transactionService.saveTransaction(transactionDebit);
        transactionService.saveTransaction(transactionCredit);
        return new ResponseEntity<>("Transaction allowed", HttpStatus.CREATED);
    }
}
