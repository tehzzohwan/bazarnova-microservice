//package com.tehzzcode.apigateway.filter;
//
//import com.tehzzcode.apigateway.util.JwtUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//
//@Component
//public class JwtAuthFilter extends AbstractGatewayFilterFactory<JwtAuthFilter.Config> {
//    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);
//
//    private final RouteValidator routeValidator;
//
//    private final JwtUtil jwtUtil;
//
//    public JwtAuthFilter(JwtUtil jwtUtil, RouteValidator routeValidator) {
//        super(Config.class);
//        this.jwtUtil = jwtUtil;
//        this.routeValidator = routeValidator;
//    }
//
//    @Override
//    public GatewayFilter apply(Config config) {
//        return (exchange, chain) -> {
//            String requestPath = exchange.getRequest().getURI().getPath();
//            log.info("Incoming request path: {}", requestPath);
//
//            // Skip JWT validation for public routes
//            if (routeValidator.isSecured.negate().test(exchange.getRequest())) {
//                log.info("Skipping authentication for public route: {}", requestPath);
//                return chain.filter(exchange);
//            }
//
//            if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
//                throw new RuntimeException("Missing authorization header");
//            }
//
//            String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//            if (authHeader != null && authHeader.startsWith("Bearer ")) {
//                authHeader = authHeader.substring("Bearer ".length());
//            }
//
//            try {
//                jwtUtil.validateToken(authHeader);
//            } catch (Exception e) {
//                log.info("Token validation failed: {}", e.getMessage());
//                throw new RuntimeException(e.getMessage());
//            }
//
//            return chain.filter(exchange);
//        };
//    }
//
//    public static class Config {}
//
//}
