package com.payfone.oauth.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.payfone.oauth.AuthenticationConstants;

@JsonInclude(Include.NON_EMPTY)
public class ClientResponse
{
    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("token_type")
    private final String tokenType = AuthenticationConstants.TOKEN_TYPE_BEARER;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty("scope")
    private String scope;

    @JsonProperty("client_id")
    private String clientId;

    @JsonProperty("sub_client_id")
    private String subClientId;

    @JsonProperty("sub_client_alias")
    private String subClientAlias;

    @JsonProperty("jti")
    private final String jti;

    public ClientResponse(String jti)
    {
        this.jti = jti;
    }

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getTokenType()
    {
        return tokenType;
    }

    public String getRefreshToken()
    {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }

    public String getExpiresIn()
    {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn)
    {
        this.expiresIn = expiresIn;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getSubClientId()
    {
        return subClientId;
    }

    public void setSubClientId(String subClientId)
    {
        this.subClientId = subClientId;
    }

    public String getSubClientAlias()
    {
        return subClientAlias;
    }

    public void setSubClientAlias(String subClientAlias)
    {
        this.subClientAlias = subClientAlias;
    }

    public String getJti()
    {
        return jti;
    }
}
