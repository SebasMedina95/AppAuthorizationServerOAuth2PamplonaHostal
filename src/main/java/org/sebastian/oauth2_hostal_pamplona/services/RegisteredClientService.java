package org.sebastian.oauth2_hostal_pamplona.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sebastian.oauth2_hostal_pamplona.common.exceptions.NotFound;
import org.sebastian.oauth2_hostal_pamplona.mappers.ClientAppMapper;
import org.sebastian.oauth2_hostal_pamplona.persistence.entities.security.ClientApp;
import org.sebastian.oauth2_hostal_pamplona.persistence.repositories.ClientAppRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisteredClientService implements RegisteredClientRepository {

    private final ClientAppRepository clientAppRepository;

    @Override
    public void save(RegisteredClient registeredClient) {}

    @Override
    public RegisteredClient findById(String id) {
        ClientApp clientApp = clientAppRepository.findByClientId(id)
                .orElseThrow(() -> new NotFound("Cliente no encontrado por ID"));
        return ClientAppMapper.toRegisteredClient(clientApp);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.findById(clientId);
    }

}


