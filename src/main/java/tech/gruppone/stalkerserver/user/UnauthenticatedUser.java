package tech.gruppone.stalkerserver.user;

import lombok.Data;
import lombok.Getter;

@Data
public class UnauthenticatedUser {

    @Getter String email;
    @Getter String password;
}
