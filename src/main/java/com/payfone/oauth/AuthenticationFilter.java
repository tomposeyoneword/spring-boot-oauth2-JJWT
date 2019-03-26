package com.payfone.oauth;

import static com.payfone.oauth.AuthenticationConstants.AUTHORITIES;
import static com.payfone.oauth.AuthenticationConstants.EXPIRATION;
import static com.payfone.oauth.AuthenticationConstants.GRANT_TYPE_PASSWORD;
import static com.payfone.oauth.AuthenticationConstants.PAYFONE;
import static com.payfone.oauth.AuthenticationConstants.TOKEN_URI;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payfone.oauth.model.ClientRequest;
import com.payfone.oauth.model.ClientRequest.ClientRequestBuilder;
import com.payfone.oauth.model.ClientResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationFilter(AuthenticationManager authManager)
    {
        this.authenticationManager = authManager;
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(TOKEN_URI, HttpMethod.POST.toString()));
    }

    //@formatter:off
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException
    {
            //ClientRequest clientRequest = objectMapper.readValue(request.getInputStream(), ClientRequest.class);
            ClientRequest clientRequest = ClientRequestBuilder.build(request);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    clientRequest.getSubClientId(),
                    clientRequest.getSubClientSecret(),
                    AuthorityUtils.createAuthorityList(GRANT_TYPE_PASSWORD));

            // Authenticate the user, and use UserDetialsServiceImpl::loadUserByUsername() method to load the user.
            return authenticationManager.authenticate(authToken);

    }
    //@formatter:on

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain,
            Authentication authentication)
            throws IOException, ServletException
    {
        // Upon successful authentication, generate a token. The 'authentication' passed to is the current authenticated user.

        String id = UUID.randomUUID().toString();

        List<String> authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Long now = System.currentTimeMillis();

        int expiresIn = EXPIRATION * 1000;
        Date issuedAt = new Date(now);
        Date expiresAt = new Date(now + expiresIn);
        String token = Jwts.builder()
                .setId(id)
                .setIssuer(PAYFONE)
                .setIssuedAt(issuedAt)
                .setSubject(authentication.getName())
                .claim(AUTHORITIES, authorities)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, "abacadaba_TODO".getBytes()) // TODO:
                .compact();

        String refreshToken = Jwts.builder()
                .setId(id) // Unique refresh id?
                .setIssuer(PAYFONE)
                .setIssuedAt(issuedAt)
                .setSubject(authentication.getName())
                .claim(AUTHORITIES, authorities)
                .setExpiration(expiresAt)
                .signWith(SignatureAlgorithm.HS512, "abacadaba_refresh_token_TODO".getBytes()) // TODO:
                .compact();

        ClientRequest clientRequest = ClientRequestBuilder.build(request);

        ClientResponse clientResponse = new ClientResponse(id);
        clientResponse.setAccessToken(token);
        clientResponse.setRefreshToken(refreshToken);
        clientResponse.setExpiresIn(Integer.toString(expiresIn));
        clientResponse.setScope(""); // TOOD: TBD?
        clientResponse.setClientId(clientRequest.getClientId());
        clientResponse.setSubClientId(clientRequest.getSubClientId());
        clientResponse.setSubClientAlias(clientRequest.getSubClientAlias());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        String jsonResponse = objectMapper.writeValueAsString(clientResponse);
        IOUtils.write(jsonResponse, response.getOutputStream(), Charset.defaultCharset());
    }
}
