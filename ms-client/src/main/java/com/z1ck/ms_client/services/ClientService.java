package com.z1ck.ms_client.services;

import com.z1ck.ms_client.entities.ClientEntity;
import com.z1ck.ms_client.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public ArrayList<ClientEntity> getClients(){return (ArrayList<ClientEntity>) clientRepository.findAll();}

    public ClientEntity saveClient(ClientEntity client){return clientRepository.save(client);}

    public ClientEntity getClientById(Long id){return clientRepository.findById(id).get();}

    public ClientEntity updateClient(ClientEntity client){return clientRepository.save(client);}

    public boolean deleteClient(Long id) throws Exception{
        try{
            clientRepository.deleteById(id);
            return true;
        }catch (Exception e){
            throw  new Exception(e.getMessage());
        }
    }
}
