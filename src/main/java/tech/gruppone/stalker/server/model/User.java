package tech.gruppone.stalker.server.model;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

//TODO rewrite class in a right way
@Data
@NoArgsConstructor
public class User {

  @Id
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
