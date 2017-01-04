package by.danceform.app.security;

import org.junit.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for the SecurityUtils utility class.
 *
 * @see SecurityUtils
 */
public class SecurityUtilsUnitTest {

    @Test
    public void testGetCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Authentication auth = new UsernamePasswordAuthenticationToken("admin", "admin");
        securityContext.setAuthentication(auth);
        SecurityContextHolder.setContext(securityContext);
        String login = SecurityUtils.getCurrentUserLogin();
        assertThat(login).isEqualTo("admin");

        auth = new UsernamePasswordAuthenticationToken(new User("admin2", "admin2", Collections.emptyList()), "admin");
        securityContext.setAuthentication(auth);
        login = SecurityUtils.getCurrentUserLogin();
        assertThat(login).isEqualTo("admin2");
    }

    @Test
    public void testIsAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("admin", "admin"));
        SecurityContextHolder.setContext(securityContext);
        boolean isAuthenticated = SecurityUtils.isAuthenticated();
        assertThat(isAuthenticated).isTrue();

        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("test",
            "test",
            Collections.emptyList()));
        isAuthenticated = SecurityUtils.isAuthenticated();
        assertThat(isAuthenticated).isTrue();
    }

    @Test
    public void testIsUserAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.USER));
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("user", "user", authorities));
        boolean isAuthenticated = SecurityUtils.isAuthenticated();
        assertThat(isAuthenticated).isTrue();
    }

    @Test
    public void testNotAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(null);
        SecurityContextHolder.setContext(securityContext);
        boolean isAuthenticated = SecurityUtils.isAuthenticated();
        assertThat(isAuthenticated).isFalse();
    }

    @Test
    public void testAnonymousIsNotAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken("anonymous",
            "anonymous",
            authorities));
        SecurityContextHolder.setContext(securityContext);
        boolean isAuthenticated = SecurityUtils.isAuthenticated();
        assertThat(isAuthenticated).isFalse();
    }

    @Test
    public void testIsAdmin() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(securityContext);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ADMIN));
        Authentication auth = new UsernamePasswordAuthenticationToken(new User("admin", "admin", authorities), "admin");
        securityContext.setAuthentication(auth);

        boolean isInRole = SecurityUtils.isAdmin();
        assertThat(isInRole).isTrue();
    }

    @Test
    public void testIsCurrentUserInRole() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(securityContext);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        Authentication auth = new UsernamePasswordAuthenticationToken(new User("admin", "admin", authorities), "admin");
        securityContext.setAuthentication(auth);

        boolean isInRole = SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ANONYMOUS);
        assertThat(isInRole).isTrue();
    }

    @Test
    public void testNotIsCurrentUserInRole() {
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(securityContext);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(AuthoritiesConstants.ANONYMOUS));
        Authentication auth = new UsernamePasswordAuthenticationToken(new User("admin", "admin", authorities), "admin");
        securityContext.setAuthentication(auth);

        boolean isInRole = SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN);
        assertThat(isInRole).isFalse();

        auth = new UsernamePasswordAuthenticationToken(new User("admin", "admin", Collections.emptyList()), "admin");
        securityContext.setAuthentication(auth);
        isInRole = SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN);
        assertThat(isInRole).isFalse();

        securityContext.setAuthentication(null);
        isInRole = SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN);
        assertThat(isInRole).isFalse();
    }
}
