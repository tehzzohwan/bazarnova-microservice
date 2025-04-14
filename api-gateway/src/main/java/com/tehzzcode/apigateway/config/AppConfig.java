package com.tehzzcode.apigateway.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
       return new RestTemplate();
    }
}
//
//    public WebClient webClient(){
//        new WebClient();
//    }






































//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeExchange(exchange -> exchange
//                .pathMatchers(
//                    "/public/**",
//                    "/api/v1/auth/**")
//                .permitAll()
//                .anyExchange()
//                .authenticated()
//            )
//            .oauth2ResourceServer(oauth2 -> oauth2
//                .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
//            );
//        return http.build();
//    }
//
//    private ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
//        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
//            Collection<GrantedAuthority> authorities = jwt.getClaimAsStringList("roles").stream()
//                .map(role -> (GrantedAuthority) () -> "ROLE_" + role.toUpperCase())
//                .collect(Collectors.toList());
//            return (Flux<GrantedAuthority>) authorities;
//        });
//        return converter;
//    }

