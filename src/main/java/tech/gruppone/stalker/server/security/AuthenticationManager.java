package tech.gruppone.stalker.server.security;


import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Connections;
import tech.gruppone.stalker.server.model.UserRoles;
import tech.gruppone.stalker.server.repository.ConnectionRepository;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {


  @Autowired
  JwtUtil jwtToken;

  @Autowired
  ConnectionRepository connectionRepository;


  @SneakyThrows
  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();
    String username = authentication.getName();
    if ( username!= null && jwtToken.isTokenExpired(token) && !jwtToken.isTokenSigned(token)) {
      return Mono.empty();
    } else {
      List<Connections> connectionsList = new ArrayList<>();
      Long id = Long.getLong(jwtToken.getId(token));
      connectionRepository.findConnectedUserById(id).subscribe(Connections -> connectionsList.add(Connections));
      List<UserRoles> userRolesList= jwtToken.parseUserRoles(token);

      userRolesList.stream().filter(UserRoles-> connectionsList.stream()
        .anyMatch(connections -> connections.getOrganizationId().equals(UserRoles.getOrganizationId())));

      System.out.println(userRolesList);
      List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
      UsernamePasswordAuthenticationToken accessToken = new UsernamePasswordAuthenticationToken(jwtToken.getUsername(
        token), null, null);
      return Mono.just(accessToken);
    }
  }
}
