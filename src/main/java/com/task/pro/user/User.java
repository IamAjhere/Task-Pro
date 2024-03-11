package com.task.pro.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.task.pro.task.Task;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private  String email;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> tasks;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        Map<Role, List<String>> roleAuthoritiesMap = Map.of(
                Role.INDIVIDUAL, Arrays.asList(
                        "TASK", "TASK:ADD", "TASK:VIEW", "TASK:DELETE", "TASK:UPDATE"
                ),
                Role.TEAM_MEMBER, Arrays.asList(
                        "TASK", "TASK:VIEW", "TASK:UPDATE"
                ),
                Role.TEAM_OWNER, Arrays.asList(
                        "TASK", "TASK:ADD", "TASK:VIEW", "TASK:DELETE", "TASK:UPDATE",
                        "TEAM_MEMBER", "TEAM_MEMBER:ADD", "TEAM_MEMBER:VIEW", "TEAM_MEMBER:DELETE", "TEAM_MEMBER:UPDATE"
                )
        );
        // Get the authorities based on the user's role
        List<String> roleAuthorities = roleAuthoritiesMap.get(role);
        if (roleAuthorities != null) {
            roleAuthorities.forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority)));
        }

        return authorities;
    }
    @Override
    public String getUsername() {
        return email; 
    }

    @Override
    public boolean isAccountNonExpired() {
         return true;
    }

    @Override
    public boolean isAccountNonLocked() {  
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
