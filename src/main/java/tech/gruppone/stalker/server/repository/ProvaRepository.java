package tech.gruppone.stalker.server.repository;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.OrganizationRole;

@Repository
public interface  ProvaRepository extends ReactiveCrudRepository<OrganizationRole, Long> {

    @Query("SELECT * FROM OrganizationRole")
    public Flux<OrganizationRole> showAll();
}
