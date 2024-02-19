package com.divergentsl.identity.provider;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.impl.GroupQueryImpl;
import org.camunda.bpm.engine.impl.Page;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.interceptor.CommandExecutor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomGroupQuery extends GroupQueryImpl {
    public CustomGroupQuery() {
        super();
    }

    public CustomGroupQuery(CommandExecutor commandExecutor) {
        super(commandExecutor);
    }

    @Override
    public long executeCount(CommandContext commandContext) {
        log.info("executeCount");
        final CustomIdentityProvider provider = getCustomIdentityProvider(commandContext);
        return provider.findGroupCountByQueryCriteria(this);
    }

    @Override
    public List<Group> executeList(CommandContext commandContext, Page page) {
        log.info("executeList");
        final CustomIdentityProvider provider = getCustomIdentityProvider(commandContext);
        return provider.findGroupByQueryCriteria(this);
    }

    protected CustomIdentityProvider getCustomIdentityProvider(CommandContext commandContext) {
        return (CustomIdentityProvider) commandContext.getReadOnlyIdentityProvider();
    }
}
