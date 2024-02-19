package com.divergentsl.identity.provider;

import com.divergentsl.service.impl.CustomGroupServiceImpl;
import com.divergentsl.service.impl.CustomUserServiceImpl;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.Session;
import org.camunda.bpm.engine.impl.interceptor.SessionFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomIdentityProviderFactory implements SessionFactory {
    private final CustomUserServiceImpl userService;

    private final CustomGroupServiceImpl groupService;

    public CustomIdentityProviderFactory(CustomUserServiceImpl userService, CustomGroupServiceImpl groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public Class<?> getSessionType() {
        return ReadOnlyIdentityProvider.class;
    }

    @Override
    public Session openSession() {
        return new CustomIdentityProvider(userService, groupService);
    }
}