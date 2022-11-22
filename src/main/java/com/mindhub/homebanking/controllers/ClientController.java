package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.AccountService;
import com.mindhub.homebanking.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @RequestMapping("/clients")
    public List<ClientDTO> getclientsDTO(){
        return clientService.getclientsDTO();
    }

    @RequestMapping("clients/{id}")
    public ClientDTO getClientDTO(@PathVariable long id){
        return clientService.getClientDTO(id);
    }

    @PatchMapping("/clients/current")
    public ResponseEntity<?> changePassword(Authentication authentication,@RequestParam String password){

        Client clientCurrent = clientService.findByEmail(authentication.getName());

        clientService.saveClient(clientCurrent);

        return new ResponseEntity<>(clientCurrent,HttpStatus.OK);}

    @PostMapping("/clients")
    public ResponseEntity<Object> register(
            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password, @RequestParam String accountType) {
        if (firstName.isEmpty()) {
            return new ResponseEntity<>("Missing First Name", HttpStatus.FORBIDDEN);
        }
        if (lastName.isEmpty()) {
            return new ResponseEntity<>("Missing Last Name", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()) {
            return new ResponseEntity<>("Missing Email", HttpStatus.FORBIDDEN);
        }
        if (password.isEmpty()) {
            return new ResponseEntity<>("Missing Password", HttpStatus.FORBIDDEN);
        }
        if (accountType.isEmpty()){
            return new ResponseEntity<>("Missing Account Type", HttpStatus.FORBIDDEN);
        }
        if (clientService.findByEmail(email) !=  null) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }
        Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));

        clientService.saveClient(newClient);

        Account account= new Account("VIN"+getRandomNumber(10000000,100000000), LocalDateTime.now(),0,accountType);
        newClient.addAccount(account);
        accountService.saveAccount(account);
        return new ResponseEntity<>("Your account was successfully created",HttpStatus.CREATED);
    }

    @RequestMapping("clients/current")
    public ClientDTO getClient(Authentication authentication){
        return new ClientDTO(clientService.findByEmail(authentication.getName()));
    }
}
