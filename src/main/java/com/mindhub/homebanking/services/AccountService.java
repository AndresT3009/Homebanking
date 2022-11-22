package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import java.util.List;

public interface AccountService {

    public List<AccountDTO> getaccountsDTO();

    public AccountDTO getAccountsDTO(long id);

    public void saveAccount(Account account);

    public Account findByNumber (String account);

}
