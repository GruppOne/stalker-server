package tech.gruppone.stalker.server.security.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import tech.gruppone.stalker.server.security.annotations.Authorized;

@Aspect
public class AuthorizationAspect {

  @Pointcut("within(tech.gruppone.stalker.server.controller..*) && execution(public Mono<+> *(..))")
  private void controller() {}

  @Pointcut("@annotation(tech.gruppone.stalker.server.security.annotations.Authorized)")
  private void authorizationProtected() {}

  @Around("controller() && authorizationProtected()")
  public Object checkAuthorization(ProceedingJoinPoint joinPoint, Authorized authorized) throws Throwable{
    

    return joinPoint.proceed();
  }

}
