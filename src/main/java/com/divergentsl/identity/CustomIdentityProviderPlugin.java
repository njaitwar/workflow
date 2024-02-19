package com.divergentsl.identity;

import com.divergentsl.identity.provider.CustomIdentityProviderFactory;
import com.divergentsl.service.impl.CustomGroupServiceImpl;
import com.divergentsl.service.impl.CustomUserServiceImpl;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.stereotype.Component;

@Component
public class CustomIdentityProviderPlugin implements ProcessEnginePlugin {
    private final CustomUserServiceImpl userServiceImpl;

    private final CustomGroupServiceImpl groupServiceImpl;

    public CustomIdentityProviderPlugin(CustomUserServiceImpl userServiceImpl, CustomGroupServiceImpl groupServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.groupServiceImpl = groupServiceImpl;
    }

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

        CustomIdentityProviderFactory identityProviderFactory = new CustomIdentityProviderFactory(userServiceImpl,
                groupServiceImpl);
        processEngineConfiguration.setIdentityProviderSessionFactory(identityProviderFactory);
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }

}