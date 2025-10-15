package org.fourstack.gatewayserver.security;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class KeyCloakRoleConverter implements Converter<Jwt, Collection<GrantedAuthority>> {
    @Override
    public Collection<GrantedAuthority> convert(Jwt source) {
        // Extract the Map with key realm_access from JWT token
        Object object = source.getClaims().get("realm_access");
        if (object instanceof Map<?, ?> realmAccess) {
            if (realmAccess.isEmpty()) {
                return new ArrayList<>();
            }

            // Extract the roles list from the map and convert each of them to GrantedAuthority objects.
            Object rolesObj = realmAccess.get("roles");
            if (rolesObj instanceof List<?> roles) {
                return roles.stream().map(role -> "ROLE_" + role)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
