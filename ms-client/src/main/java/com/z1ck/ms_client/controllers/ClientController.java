package com.z1ck.ms_client.controllers;

import com.z1ck.ms_client.entities.ClientEntity;
import com.z1ck.ms_client.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {
    @Autowired
    ClientService clientService;
    @GetMapping("/")
    public ResponseEntity<List<ClientEntity>> listClients() {
        List<ClientEntity> clients = clientService.getClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientEntity> getClientById(@PathVariable Long id) {
        ClientEntity client = clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping("/")
    public ResponseEntity<ClientEntity> saveClient(@RequestBody ClientEntity client) {
        ClientEntity newClient = clientService.saveClient(client);
        return ResponseEntity.ok(newClient);
    }

    @PutMapping("/")
    public ResponseEntity<ClientEntity> updateClient(@RequestBody ClientEntity client) {
        ClientEntity updatedClient = clientService.updateClient(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClientById(@PathVariable Long id) throws Exception {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
