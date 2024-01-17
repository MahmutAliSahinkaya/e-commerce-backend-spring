package com.ecommerce.paymentservice.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable("getUsername()");
    }

//    private String getUsername() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
//            return ((UserDetails) authentication.getPrincipal()).getUsername();
//        }
//        throw new UserNotAuthenticatedException("User is not authenticated");
//    }
}
