package tech.gruppone.stalker.server.repositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import tech.gruppone.stalker.server.model.db.UserDataDao;

public interface UserDataRepository extends ReactiveCrudRepository<UserDataDao, Long> {}
