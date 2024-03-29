package com.springboot.diplomamanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
public class CustomerLoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {

        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            return;
        }
        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = new ArrayList<String>();
        for (GrantedAuthority a : authorities) {
            roles.add(a.getAuthority());
        }

        String url= "";



        if(roles.contains("Professor")) {
            url = "/professor/home";
        }
        else if(roles.contains("User")) {
            url = "/student/applications";
        }
        return url;
    }

}
