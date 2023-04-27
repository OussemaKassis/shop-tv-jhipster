package tn.softtodo.shoptv.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.softtodo.shoptv.domain.Client;
import tn.softtodo.shoptv.repository.AppUserRepository;
import tn.softtodo.shoptv.repository.ClientRepository;
import tn.softtodo.shoptv.service.ClientService;

/**
 * Service Implementation for managing {@link Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    private final AppUserRepository appUserRepository;

    public ClientServiceImpl(ClientRepository clientRepository, AppUserRepository appUserRepository) {
        this.clientRepository = clientRepository;
        this.appUserRepository = appUserRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        Long appUserId = client.getAppUser().getId();
        appUserRepository.findById(appUserId).ifPresent(client::appUser);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        log.debug("Request to update Client : {}", client);
        Long appUserId = client.getAppUser().getId();
        appUserRepository.findById(appUserId).ifPresent(client::appUser);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getPhoneNumber() != null) {
                    existingClient.setPhoneNumber(client.getPhoneNumber());
                }
                if (client.getGender() != null) {
                    existingClient.setGender(client.getGender());
                }
                if (client.getDateOfBirth() != null) {
                    existingClient.setDateOfBirth(client.getDateOfBirth());
                }
                if (client.getCurrentPlanOffer() != null) {
                    existingClient.setCurrentPlanOffer(client.getCurrentPlanOffer());
                }
                if (client.getJob() != null) {
                    existingClient.setJob(client.getJob());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
