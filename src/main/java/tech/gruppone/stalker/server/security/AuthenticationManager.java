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
import tech.gruppone.stalker.server.model.db.Connection;
import tech.gruppone.stalker.server.model.api.UserRole;
import tech.gruppone.stalker.server.repositories.ConnectionRepository;
import tech.gruppone.stalker.server.services.JwtUtil;

@Component
public class AuthenticationManager implements ReactiveAuthenticationManager {


  @Autowired
  JwtUtil jwtToken;

  @Autowired
  ConnectionRepository connectionRepository;


  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    String token = authentication.getName();
    if ( token== null || jwtToken.isTokenExpired(token) || !jwtToken.isTokenSigned(token)) {
      return Mono.empty();
    } else {
      List<Connection> connectionList = new ArrayList<>();
      Long id = Long.valueOf(jwtToken.getId(token));
      connectionRepository.findConnectedUserById(id).subscribe(Connection -> connectionList.add(Connection));
      List<UserRole> userRoleList = new ArrayList<>();
      try {
        userRoleList = jwtToken.parseUserRoles(token);
      } catch (IOException e) {
        e.printStackTrace();
      }

      userRoleList.stream().filter(UserRole -> connectionList.stream()
        .anyMatch(connection -> connection.getOrganizationId().equals(UserRole.getOrganizationId())));

      List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
      for(UserRole userRole : userRoleList){
        authorityList.add(new SimpleGrantedAuthority(userRole.getRole()));
      }
      UsernamePasswordAuthenticationToken accessToken = new UsernamePasswordAuthenticationToken(jwtToken.getId(token), null, authorityList);
      return Mono.just(accessToken);
    }
  }
}
