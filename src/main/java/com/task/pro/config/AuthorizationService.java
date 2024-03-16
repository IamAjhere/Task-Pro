package com.task.pro.config;
import com.task.pro.user.Role;
import com.task.pro.user.User;
import com.task.pro.user.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    public Authentication getCurrentAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public boolean hasAnyAuthority(String... authorities) {
        Authentication authentication = getCurrentAuthentication();
        for (String authority : authorities) {
            if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(authority))) {
                return true;
            }
        }
        return false;
    }

    public Long getCurrentUserId() {
        Authentication authentication = getCurrentAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getId();
        }
        return null;
    }

    public Role getCurrentUserRole() {
        Authentication authentication = getCurrentAuthentication();
        Object principal = authentication.getPrincipal();
        if (principal instanceof User) {
            return ((User) principal).getRole();
        }
        return null;
    }

    public String getCurrentUserEmail() {
        Authentication authentication = getCurrentAuthentication();
        return authentication.getName();
    }
}
