package com.shamik.code.statuspage.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@EnableOAuth2Client
@SpringBootApplication
public class Application extends WebSecurityConfigurerAdapter
{

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    OAuth2ClientContext oauth2ClientContext;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       /* http
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers(
                        "/", "/statusPage**", "/login**", "/webjars/**", "/news**", "/user**", "/console**", "/app/**",
                        "/weather**", "/getGoogleToken**", "/processgtoken**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and().logout().logoutSuccessUrl("/").permitAll()
                .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and().addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
                */

        http.authorizeRequests().antMatchers("/").permitAll();


    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(googleLogin(), oauth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), googleLogin()
                .getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(tokenServices);

        return googleFilter;
    }

    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public AuthorizationCodeResourceDetails googleLogin() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("security.oauth2.resource.userInfoUri")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }



    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }*/

}