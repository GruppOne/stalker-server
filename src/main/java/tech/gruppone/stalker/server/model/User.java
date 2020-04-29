package tech.gruppone.stalker.server.model;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.data.annotation.Id;
import lombok.NonNull;
import lombok.Value;

//TODO rewrite class in a right way
@Value
public class User {

  @Id
  @NonNull
  Long id;

  @NonNull
  String email;

  @NonNull
  String password;

  @NonNull
  String firstName;

  @NonNull
  String lastName;

  @NonNull
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonSerialize(using = LocalDateSerializer.class)
  LocalDate birthDate;

}
