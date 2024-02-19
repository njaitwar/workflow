package com.divergentsl.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.camunda.bpm.engine.identity.Group;

import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "`group`")
public class CustomGroup implements Group {
    @Id
    @NotBlank(message = "Group ID cannot be blank")
    private String groupId;

    @NotBlank(message = "Group name cannot be blank")
    @Column(name = "`name`")
    private String name;

    @NotBlank(message = "Group type cannot be blank")
    @Column(name = "`type`")
    private String type;

    @Override
    public String getId() {
        return groupId;
    }

    @Override
    public void setId(String id) {
        this.groupId = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setType(String type) {
        this.type = type;
    }

    public CustomGroup(String groupId, String name, String type) {
        super();
        this.groupId = groupId;
        this.name = name;
        this.type = type;
    }

    public CustomGroup() {
        super();
    }

    public Collection<CustomUser> getUsers() {
        return null;
    }
}