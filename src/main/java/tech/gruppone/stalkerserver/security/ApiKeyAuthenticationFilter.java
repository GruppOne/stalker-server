/*package com.example.Stalkerserver.security;


import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import javax.servlet.http.HttpServletRequest;

public class ApiKeyAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

  String requestHeader;

  public ApiKeyAuthenticationFilter(String requestHeader) {
    this.requestHeader = requestHeader;
  }


  @Override
  protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
    return request.getHeader(requestHeader);
  }

  @Override
  protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
    return  "credentials";
  }


}*/
