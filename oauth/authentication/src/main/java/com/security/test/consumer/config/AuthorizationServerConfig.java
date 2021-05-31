package com.security.test.consumer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public CustomTokenService customTokenService() {
        CustomTokenService defaultTokenServices = new CustomTokenService();
        defaultTokenServices.setAccessTokenEnhancer(jwtAccessTokenConverter);
        defaultTokenServices.setTokenStore(tokenStore);
        return defaultTokenServices;
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients()
                .checkTokenAccess("permitAll()")
                .tokenKeyAccess("permitAll()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
//        clients.inMemory()
//                .withClient("client5")
//                .secret(passwordEncoder.encode("secret"))
//                .scopes("web")
//                .authorizedGrantTypes("password");
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
        endpoints.authenticationManager(authenticationManager)
                .tokenStore(tokenStore);
//                .tokenServices(customTokenService())
//                .userDetailsService(userDetailsService);
    }
}
