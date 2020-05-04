package tech.gruppone.stalker.server.security;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tech.gruppone.stalker.server.model.Connection;
import tech.gruppone.stalker.server.model.UserRoles;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {


  @Autowired
  JwtUtil jwtToken;

  @Autowired
  ConnectionRepository connectionRepository;


  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getCredentials().toString();
    String username = authentication.getName();
    if ( username== null || jwtToken.isTokenExpired(token) || !jwtToken.isTokenSigned(token)) {
      return Mono.empty();
    } else {
      List<Connection> connectionList = new ArrayList<>();
      Long id = Long.valueOf(jwtToken.getId(token));
      connectionRepository.findConnectedUserById(id).subscribe(Connection -> connectionList.add(Connection));
      List<UserRoles> userRolesList= new ArrayList<>();
      try {
        userRolesList = jwtToken.parseUserRoles(token);
      } catch (IOException e) {
        e.printStackTrace();
      }

      userRolesList.stream().filter(UserRoles-> connectionList.stream()
        .anyMatch(connection -> connection.getOrganizationId().equals(UserRoles.getOrganizationId())));


      List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
      for(UserRoles userRoles : userRolesList){
        authorityList.add(new SimpleGrantedAuthority(userRoles.getRole()));
      }
      UsernamePasswordAuthenticationToken accessToken = new UsernamePasswordAuthenticationToken(jwtToken.getUsername(
        token), null, authorityList);
      return Mono.just(accessToken);
    }
  }
}
