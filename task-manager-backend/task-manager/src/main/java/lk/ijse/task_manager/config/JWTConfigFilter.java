package lk.ijse.task_manager.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lk.ijse.task_manager.service.JWTService;
import lk.ijse.task_manager.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTConfigFilter extends OncePerRequestFilter {


    private final JWTService jwtService;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String initToken = request.getHeader("Authorization");
        String jwtToken;
        String userEmail;

        // Check if the Authorization header contains a Bearer token
        if (StringUtils.isEmpty(initToken) || !initToken.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Token Extraction
        jwtToken = initToken.substring(7);
        userEmail = jwtService.extractUserName(jwtToken);

        // User Email Validation
        if (!StringUtils.isEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Load user details based on email
            UserDetails loadedUser = userService.userDetailsService().loadUserByUsername(userEmail);
            if (jwtService.isTokenValid(jwtToken, loadedUser)) {
                SecurityContext emptyContext = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(loadedUser, null, loadedUser.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetails(request));
                emptyContext.setAuthentication(authToken);
                SecurityContextHolder.setContext(emptyContext);
            }
        }
        filterChain.doFilter(request, response);
    }
}
