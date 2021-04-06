package app.user.aop;

import app.user.exceptions.UserException;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    public static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(* app.user.service.*.*(..))")
    public void before() throws UserException {
//        LOGGER.info("Before advice called.");
    }

    @After("execution(* app.user.service.*.*(..))")
    public void after() throws UserException {
//        LOGGER.info("After advice called.");
    }

    @AfterReturning(pointcut = "execution(* app.user.service.*.*(..))", returning = "str")
    public void afterReturning(String str) {
        LOGGER.info("Returning Param: " + str);
        LOGGER.info("After returning advice called.");
    }

    @AfterThrowing(pointcut = "execution(* app.user.service.*.*(..))", throwing = "e")
    public void afterThrowing(UserException e) {
        LOGGER.error("Afterthrowing called.");
        LOGGER.error(e.getMessage());
    }
}

