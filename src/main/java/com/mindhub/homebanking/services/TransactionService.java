package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {

    public List<TransactionDTO> getTransactionsDTO();

    public void saveTransaction(Transaction transaction);
}
