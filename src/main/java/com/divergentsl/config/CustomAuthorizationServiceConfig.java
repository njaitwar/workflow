package com.divergentsl.config;

import org.camunda.bpm.engine.AuthorizationService;
import org.camunda.bpm.engine.authorization.Authorization;
import org.camunda.bpm.engine.authorization.AuthorizationQuery;
import org.camunda.bpm.engine.authorization.Permission;
import org.camunda.bpm.engine.authorization.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class CustomAuthorizationServiceConfig {
    @Bean
    @Primary
    public AuthorizationService customAuthorizationService() {
        return new CustomAuthorizationService();
    }

    public static class CustomAuthorizationService implements AuthorizationService {

        @Override
        public Authorization createNewAuthorization(int authorizationType) {
            return null;
        }

        @Override
        public Authorization saveAuthorization(Authorization authorization) {
            return null;
        }

        @Override
        public void deleteAuthorization(String authorizationId) {

        }

        @Override
        public AuthorizationQuery createAuthorizationQuery() {
            return null;
        }

        @Override
        public boolean isUserAuthorized(String userId, List<String> groupIds, Permission permission, Resource resource) {
            return true;
        }

        @Override
        public boolean isUserAuthorized(String userId, List<String> groupIds, Permission permission, Resource resource, String resourceId) {
            return true;
        }
    }
}
