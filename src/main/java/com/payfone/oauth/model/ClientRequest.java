package com.payfone.oauth.model;

import javax.servlet.http.HttpServletRequest;

public class ClientRequest
{
    private String clientId;
    private String clientSecret;
    private String subClientId;
    private String subClientSecret;
    private String subClientAlias;
    private String grantType;

    public String getClientId()
    {
        return clientId;
    }

    public void setClientId(String clientId)
    {
        this.clientId = clientId;
    }

    public String getClientSecret()
    {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret)
    {
        this.clientSecret = clientSecret;
    }

    public String getSubClientId()
    {
        return subClientId;
    }

    public void setSubClientId(String subClientId)
    {
        this.subClientId = subClientId;
    }

    public String getSubClientSecret()
    {
        return subClientSecret;
    }

    public void setSubClientSecret(String subClientSecret)
    {
        this.subClientSecret = subClientSecret;
    }

    public String getSubClientAlias()
    {
        return subClientAlias;
    }

    public void setSubClientAlias(String subClientAlias)
    {
        this.subClientAlias = subClientAlias;
    }

    public String getGrantType()
    {
        return grantType;
    }

    public void setGrantType(String grantType)
    {
        this.grantType = grantType;
    }

    public static class ClientRequestBuilder
    {
        private static final String CLIENT_ID = "client_id";
        private static final String CLIENT_SECRET = "client_secret";
        private static final String SUB_CLIENT_ID = "sub_client_id";
        private static final String SUB_CLIENT_SECRET = "sub_client_secret";
        private static final String SUB_CLIENT_ALIAS = "sub_client_alias";
        private static final String GRANT_TYPE = "grant_type";

        public static ClientRequest build(HttpServletRequest request)
        {
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setClientId(request.getParameter(CLIENT_ID));
            clientRequest.setClientSecret(request.getParameter(CLIENT_SECRET));
            clientRequest.setSubClientId(request.getParameter(SUB_CLIENT_ID));
            clientRequest.setSubClientSecret(request.getParameter(SUB_CLIENT_SECRET));
            clientRequest.setSubClientAlias(request.getParameter(SUB_CLIENT_ALIAS));
            clientRequest.setGrantType(request.getParameter(GRANT_TYPE));
            return clientRequest;
        }
    }
}
