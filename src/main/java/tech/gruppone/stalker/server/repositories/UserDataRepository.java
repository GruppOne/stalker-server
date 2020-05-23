package tech.gruppone.stalker.server.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tech.gruppone.stalker.server.model.db.UserDataDao;

public interface UserDataRepository extends ReactiveCrudRepository<UserDataDao, Long> {}
