package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.services.CardService;
import com.mindhub.homebanking.services.ClientService;
import com.mindhub.homebanking.utils.CardUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    @RequestMapping("/cards")
    public List<CardDTO> getcardsDTO() {
        return cardService.getcardsDTO();
    }

    @PostMapping("clients/current/cards")
    public ResponseEntity<Object> createCards(
            Authentication authentication, @RequestParam CardType cardType,@RequestParam CardColor cardColor){
        if (cardType == null) {
            return new ResponseEntity<>("Missing Card Type", HttpStatus.FORBIDDEN);
        }
        if (cardColor == null) {
            return new ResponseEntity<>("Missing Card Color", HttpStatus.FORBIDDEN);
        }

        Client clientCurrent = clientService.findByEmail(authentication.getName());
        Set <Card> cards = clientCurrent.getCards();
        Set <Card> cardDate=cards.stream().filter(card -> card.getThruDate().isBefore(card.getFromDate())).collect(Collectors.toSet());

        Set <Card> cardDebit=cards.stream().filter(card ->card.getType()== CardType.DEBIT ).collect(Collectors.toSet());
        Set <Card> cardCredit=cards.stream().filter(card -> card.getType()== CardType.CREDIT).collect(Collectors.toSet());

        if (cardDebit.size()>= 3 && cardType == CardType.DEBIT){
            return new ResponseEntity<>("You can not have more than 3 debit cards",HttpStatus.FORBIDDEN);
        }
        if (cardCredit.size()>= 3 && cardType == CardType.CREDIT){
            return new ResponseEntity<>("You can not have more than 3 Credit cards",HttpStatus.FORBIDDEN);
        }
        if (cardDate.size()>=1){
            return new ResponseEntity<>("Your card is overdue",HttpStatus.FORBIDDEN);
        }


        String cardNumber = CardUtils.getCardNumber();

        int cardCVV = CardUtils.getCVV();

            Card card = new Card(clientCurrent.getFirstname()+" "+clientCurrent.getLastname(),cardType,cardColor,cardNumber,cardCVV,LocalDate.now(),LocalDate.now().plusYears(5));
            clientCurrent.addCards(card);
            cardService.saveCard(card);
            return new ResponseEntity<>("Your card was successfully created",HttpStatus.CREATED);
    }
}
