package tech.gruppone.stalker.server.model.api;

import java.sql.Timestamp;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class UserData {

  @NonNull
  String email;

  @NonNull
  String firstName;

  @NonNull
  String lastName;

  @NonNull
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  LocalDate birthDate;

  //LocalDateTime
  Timestamp creationDateTime;

}
