package tech.gruppone.stalkerserver.configuration;

import java.time.Instant;
import org.influxdb.annotation.*;
import jdk.jfr.Timestamp;

@Measurement(name = "accessLog")
public class InfluxDBPoint{

    @Timestamp
    @Column(name = "time")
    private Instant time;

    // to change type "String" in "java.sql.Timestamp" after POC
    @Column(name = "timestampMs")
    private String timestampMs;

    @Column(name = "userId")
    private int userId;

    //@Column(name = "anonymous") after POC
    //private String anonymous;

    @Column(name = "anonymous")
    private boolean anonymous;

    @Column(name = "placeId")
    private int placeId;

    @Column(name = "inside")
    private boolean inside;

    //Methods useful to print field keys (with "" in addField)
    public String nameMeasurement(){
        return "accessLog";
    }

    public String nameTimestampMs(){
        return "timestampMs";
    }

    public String nameUserId(){
        return "userId";
    }

    /*public String nameAnonymousKey(){
        return "anonymousKey";
    }*/

    public String nameAnonymous(){
        return "anonymous";
    }

    public String namePlaceId(){
        return "placeId";
    }

    public String nameInside(){
        return "inside";
    }

}
