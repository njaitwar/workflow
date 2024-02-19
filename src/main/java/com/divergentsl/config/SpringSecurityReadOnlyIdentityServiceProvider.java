package com.divergentsl.config;

import org.camunda.bpm.engine.AuthenticationException;
import org.camunda.bpm.engine.impl.identity.db.DbReadOnlyIdentityServiceProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Configuration
public class SpringSecurityReadOnlyIdentityServiceProvider extends DbReadOnlyIdentityServiceProvider {
    private final AuthenticationManager authenticationManager;

    public SpringSecurityReadOnlyIdentityServiceProvider(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Checks if username and password is valid.
     *
     * @param userId   Username
     * @param password Password
     * @return True if authentication succeeded
     */
    @Override
    public boolean checkPassword(String userId, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        } catch (AuthenticationException e) {
            return false;
        }
        return true;
    }
}
