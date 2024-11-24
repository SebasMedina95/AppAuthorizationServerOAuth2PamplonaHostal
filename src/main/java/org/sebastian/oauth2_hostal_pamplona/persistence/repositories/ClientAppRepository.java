package org.sebastian.oauth2_hostal_pamplona.persistence.repositories;

import org.sebastian.oauth2_hostal_pamplona.persistence.entities.security.ClientApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface ClientAppRepository extends JpaRepository<ClientApp, Long>, JpaSpecificationExecutor<ClientApp> {

    Optional<ClientApp> findByClientId(String clientId);

}
