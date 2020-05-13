package tech.gruppone.stalker.server.model.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
// import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
// import com.fasterxml.jackson.databind.annotation.JsonSerialize;
// import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
// import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.NonNull;

@Builder
@Data
public class UserDataDto {
  @NonNull
  String email;

  @NonNull
  String firstName;

  @NonNull
  String lastName;

  @NonNull
  // @JsonDeserialize(using = LocalDateDeserializer.class)
  // @JsonSerialize(using = LocalDateSerializer.class)
  LocalDate birthDate;

  LocalDateTime creationDateTime;

}
