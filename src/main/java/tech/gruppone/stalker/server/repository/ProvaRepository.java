package tech.gruppone.stalker.server.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import tech.gruppone.stalker.server.model.OrganizationRole;

public interface  ProvaRepository extends ReactiveCrudRepository<OrganizationRole, Long> {

    @Query("select * from OrganizationRole")
    public Flux<OrganizationRole> showAll();
}
