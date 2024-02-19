package com.divergentsl.identity.provider;

import com.divergentsl.domain.CustomGroup;
import com.divergentsl.domain.CustomUser;
import com.divergentsl.service.impl.CustomGroupServiceImpl;
import com.divergentsl.service.impl.CustomUserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.BadUserRequestException;
import org.camunda.bpm.engine.identity.*;
import org.camunda.bpm.engine.impl.context.Context;
import org.camunda.bpm.engine.impl.identity.IdentityOperationResult;
import org.camunda.bpm.engine.impl.identity.ReadOnlyIdentityProvider;
import org.camunda.bpm.engine.impl.identity.WritableIdentityProvider;
import org.camunda.bpm.engine.impl.interceptor.CommandContext;
import org.camunda.bpm.engine.impl.persistence.entity.GroupEntity;
import org.camunda.bpm.engine.impl.persistence.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomIdentityProvider implements ReadOnlyIdentityProvider, WritableIdentityProvider {
    private final CustomUserServiceImpl userService;

    private final CustomGroupServiceImpl groupService;

    public CustomIdentityProvider(CustomUserServiceImpl userService, CustomGroupServiceImpl groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }

    @Override
    public User findUserById(String userId) {
        Optional<CustomUser> userInfo = userService.findByUserId(userId);
        UserEntity defaultUser = null;
        if (userInfo.isPresent()) {
            User user = userInfo.get();
            defaultUser = new UserEntity();
            defaultUser.setId(user.getId());
            defaultUser.setFirstName(user.getFirstName());
            defaultUser.setLastName(user.getLastName());
            defaultUser.setEmail(user.getEmail());
            defaultUser.setPassword(user.getPassword());
            defaultUser.setDbPassword(user.getPassword());
        }
        return defaultUser;
    }

    @Override
    public UserQuery createUserQuery() {
        return new CustomUserQuery(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
    }

    @Override
    public UserQuery createUserQuery(CommandContext commandContext) {
        return new CustomUserQuery();
    }

    @Override
    public NativeUserQuery createNativeUserQuery() {
        throw new BadUserRequestException("not supported");
    }

    public long findUserCountByQueryCriteria(CustomUserQuery query) {
        return findUserByQueryCriteria(query).size();
    }

    public List<User> findUserByQueryCriteria(CustomUserQuery query) {

        Collection<CustomUser> users = userService.findAllUsers();

        if (query.getId() != null)
            users.removeIf(user -> !user.getId().equals(query.getId()));
        if (query.getFirstName() != null)
            users.removeIf(user -> !user.getFirstName().equals(query.getFirstName()));
        if (query.getLastName() != null)
            users.removeIf(user -> !user.getLastName().equals(query.getLastName()));
        if (query.getEmail() != null)
            users.removeIf(user -> !user.getEmail().equals(query.getEmail()));
        if (query.getGroupId() != null)
            users.removeIf(user -> !user.getCustomGroup().getId().equals(query.getGroupId()));

        return new ArrayList<>(users);

    }

    @Override
    public boolean checkPassword(String userId, String password) {

        if (userId == null || password == null || userId.isEmpty() || password.isEmpty())
            return false;

        User user = findUserById(userId);

        if (user == null)
            return false;

        return user.getPassword().equals(password);
    }

    @Override
    public Group findGroupById(String groupId) {
        Optional<CustomGroup> ginfo = groupService.findByGroupId(groupId);
        Group defaultGroup = null;
        if (ginfo.isPresent()) {
            Group group = ginfo.get();
            defaultGroup = new GroupEntity();
            defaultGroup.setId(group.getId());
            defaultGroup.setName(group.getName());
            defaultGroup.setId(group.getType());

        }
        return defaultGroup;
    }

    @Override
    public GroupQuery createGroupQuery() {
        return new CustomGroupQuery(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
    }

    @Override
    public GroupQuery createGroupQuery(CommandContext commandContext) {
        log.info("createGroupQuery");
        return new CustomGroupQuery();
    }

    public long findGroupCountByQueryCriteria(CustomGroupQuery query) {
        return findGroupByQueryCriteria(query).size();
    }

    public List<Group> findGroupByQueryCriteria(CustomGroupQuery query) {
        log.info("findGroupByQueryCriteria");
        return groupService.findAllGroups().stream()
                .filter(group -> group.getId().equals(query.getId()))
                .filter(group -> group.getName().equals(query.getName()))
                .filter(group -> group.getType().equals(query.getType()))
                .collect(Collectors.toList());
    }

    @Override
    public Tenant findTenantById(String tenantId) {
        return null;
    }

    @Override
    public TenantQuery createTenantQuery() {
        return new CustomTenantQuery(Context.getProcessEngineConfiguration().getCommandExecutorTxRequired());
    }

    @Override
    public TenantQuery createTenantQuery(CommandContext commandContext) {
        return new CustomTenantQuery();
    }

    @Override
    public void flush() {

    }

    @Override
    public void close() {

    }

    @Override
    public User createNewUser(String userId) {
        CustomUser customUser = userService.findByUserId(userId)
                .orElseThrow(() -> new BadUserRequestException("User with id " + userId + " does not exist"));
        UserEntity user = new UserEntity();
        user.setId(customUser.getId());
        user.setFirstName(customUser.getFirstName());
        user.setLastName(customUser.getLastName());
        user.setEmail(customUser.getEmail());
        user.setPassword(customUser.getPassword());
        user.setDbPassword(customUser.getPassword());
        return user;
    }

    @Override
    public IdentityOperationResult saveUser(User user) {
        CustomUser customUser = userService. createUser((CustomUser) user);
        return new IdentityOperationResult ("User saved successfully", customUser.getUserId());
    }
    @Override
    public IdentityOperationResult deleteUser(String userId) {
        userService.deleteUser(userId);
        return new IdentityOperationResult( "User deleted successfully", userId);
    }

    @Override
    public IdentityOperationResult unlockUser(String userId) {
        return null;
    }

    @Override
    public Group createNewGroup(String groupId) {
        Optional<CustomGroup> groupInfo = groupService.findByGroupId(groupId);
        if (!groupInfo.isPresent()) {
            throw new BadUserRequestException("Group with id " + groupId + " does not exist");
        }
        GroupEntity groupEntity = new GroupEntity();
        groupEntity.setId(groupInfo.get().getId());
        groupEntity.setName(groupInfo.get().getName());
        groupEntity.setType(groupInfo.get().getType());
        return groupEntity;
    }

    @Override
    public IdentityOperationResult saveGroup(Group group) {
        CustomGroup customGroup = groupService.createGroup((CustomGroup) group);
        return new IdentityOperationResult( "Group saved successfully", customGroup.getId());
    }
    @Override
    public IdentityOperationResult deleteGroup(String groupId) {
        groupService.deleteGroup(groupId);
        return new IdentityOperationResult( "Group deleted successfully", groupId);
    }

    @Override
    public Tenant createNewTenant(String tenantId) {
        return null;
    }

    @Override
    public IdentityOperationResult saveTenant(Tenant tenant) {
        return null;
    }

    @Override
    public IdentityOperationResult deleteTenant(String tenantId) {
        return null;
    }

    @Override
    public IdentityOperationResult createMembership(String userId, String groupId) {
        groupService.addUserToGroup(userId, groupId);
        return new IdentityOperationResult( "Membership created successfully", userId);
    }

    @Override
    public IdentityOperationResult deleteMembership(String userId, String groupId) {
        groupService.removeUserFromGroup(userId, groupId);
        return new IdentityOperationResult( "Membership deleted successfully", userId);
    }

    @Override
    public IdentityOperationResult createTenantUserMembership(String tenantId, String userId) {
        return null;
    }

    @Override
    public IdentityOperationResult createTenantGroupMembership(String tenantId, String groupId) {
        return null;
    }

    @Override
    public IdentityOperationResult deleteTenantUserMembership(String tenantId, String userId) {
        return null;
    }

    @Override
    public IdentityOperationResult deleteTenantGroupMembership(String tenantId, String groupId) {
        return null;
    }
}