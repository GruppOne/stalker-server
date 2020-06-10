package tech.gruppone.stalker.server.repositories;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.influxdb.impl.InfluxDBMapper;
import org.springframework.stereotype.Repository;

@Log4j2
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Repository
public class MeasurementsRepository {
  InfluxDBMapper influxDBMapper;

  public <T> void save(final T model) {
    influxDBMapper.save(model);
  }

  public List<LocationInfo> findByOrganizationId(long organizationId) {

    throw new NotImplementedException();
  }
}
