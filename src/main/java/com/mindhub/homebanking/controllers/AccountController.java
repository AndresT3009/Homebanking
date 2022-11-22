package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/account")
    public List<AccountDTO> getaccountsDTO() {
        return accountService.getaccountsDTO();
    }

    @RequestMapping("account/{id}")
    public AccountDTO getAccountDTO(@PathVariable long id){
        return accountService.getAccountsDTO(id);
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @PostMapping("clients/current/account")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam String accountType){

        Client clientCurrent = clientService.findByEmail(authentication.getName());
        if (accountType.isEmpty()){
            return new ResponseEntity<>("Missing Account Type", HttpStatus.FORBIDDEN);
        }
        if (clientCurrent.getAccounts().size()==3) {
            return new ResponseEntity<>("You have reached the maximum number of accounts", HttpStatus.FORBIDDEN);
        } else {
            Account account = new Account("VIN"+getRandomNumber(10000000,99999999), LocalDateTime.now(),0,accountType);
            clientCurrent.addAccount(account);
            accountService.saveAccount(account);
            return new ResponseEntity<>("Your account was successfully created",HttpStatus.CREATED);
            }
        }
    }