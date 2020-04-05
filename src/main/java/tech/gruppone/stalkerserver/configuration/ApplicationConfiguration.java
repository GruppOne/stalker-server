package tech.gruppone.stalkerserver.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.Getter;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin
@PropertySources({
  @PropertySource("classpath:application.properties"),
  @PropertySource("classpath:local.properties")
})
@Configuration
//FIXME should probably not be a RestController
@RestController
@Getter
public class ApplicationConfiguration {

  @Autowired
  public InfluxDB influxDB;

  public InfluxDBPoint point = new InfluxDBPoint();

  @NonNull
  private final String version;

  public ApplicationConfiguration(@Value("${spring.application.version}") String version) {
    this.version = version;
  }

  @GetMapping(value = {"/", "/version"})
  public Mono<String> currentServerVersion() {

//    FIXME should not be done like this
    final String versionObject = "{\"version\":\"" + version + "\"}";
    return Mono.just(versionObject);
  }

// FIXME THIS IS NOT THE ROLE OF THIS CLASS
  @PostMapping("/location/update")
  @ResponseBody
  public String locationUpdate(@RequestBody Map<String, Object> body) {
    JSONObject jsonObject = new JSONObject(body);
    List<String> listPlaceData = new ArrayList<String>();
    JSONArray jArray = jsonObject.getJSONArray(point.namePlaceId());
    if (jArray != null) {
      for (int i = 0; i < jArray.length(); i++) listPlaceData.add(jArray.get(i).toString());
    }

    if( (body.get(point.nameUserId()).toString() != "") && (Boolean.parseBoolean(body.get(point.nameAnonymous()).toString())) == false ){
      for(int i = 0; i < listPlaceData.size(); i++) {
        influxDB.write(Point.measurement(point.nameMeasurement())
          .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
          //.addField(point.nameTimestampMs(), java.sql.Timestamp.valueOf(body.get(point.nameTimestampMs()).toString())) -> not available addField(String, Timestamp)
          .addField(point.nameTimestampMs(), body.get(point.nameTimestampMs()).toString())
          .addField(point.nameUserId(), Integer.parseInt(body.get(point.nameUserId()).toString()))
          .addField(point.nameAnonymous(), Boolean.parseBoolean((body.get(point.nameAnonymous()).toString())))
          .addField(point.namePlaceId(), Integer.parseInt(listPlaceData.get(i)))
          .addField(point.nameInside(), Boolean.parseBoolean(body.get(point.nameInside()).toString()))
          .build());
      }
    }else if( (body.get(point.nameUserId()).toString() == "") && (Boolean.parseBoolean(body.get(point.nameAnonymous()).toString())) == true ){
      for(int i = 0; i < listPlaceData.size(); i++) {
        influxDB.write(Point.measurement(point.nameMeasurement())
          .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
          //.addField(point.nameTimestampMs(), java.sql.Timestamp.valueOf(body.get(point.nameTimestampMs()).toString())) -> not available addField(String, Timestamp)
          .addField(point.nameTimestampMs(), body.get(point.nameTimestampMs()).toString())
          .addField(point.nameAnonymous(), Boolean.parseBoolean((body.get(point.nameAnonymous()).toString())))
          .addField(point.namePlaceId(), Integer.parseInt(listPlaceData.get(i)))
          .addField(point.nameInside(), Boolean.parseBoolean(body.get(point.nameInside()).toString()))
          .build());
      }
    }else{
      influxDB.close();
      return "Query malformed";
    }

    influxDB.close();

    return "Tracking inside ok";
  }

    /* Endpoint to do after POC--------------------------------------------------------------------------

    @PostMapping("/tracking/{userId}/{organizationId}/unknownIdentity")
    public Type anonymousTrackingUser(){

    }

    @PostMapping("tracking/{userId}/{organizationId}/knownIdentity")
    public Type knownTrackingUser(){

    }

    @PostMapping("/tracking/organization/place")
    public Type addUserInPlace(){

    }

    @DeleteMapping("/tracking/organization/place")
    public Type exitUserInPlace(){

    }

    ------------------------------------------------------------------------------------------------------*/

}
