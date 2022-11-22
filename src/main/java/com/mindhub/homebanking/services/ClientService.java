package com.mindhub.homebanking.services;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Client;
import java.util.List;

public interface ClientService {

    public List<ClientDTO> getclientsDTO();

    public ClientDTO getClientDTO(long id);

    public Client findByEmail(String email);

    public void saveClient(Client client);


}
