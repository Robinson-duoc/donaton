package com.donaton.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SesionInterceptor())
                .addPathPatterns(
                        "/donaciones/registro",
                        "/donaciones/guardar",
                        "/donaciones/enviar",
                        "/panel-donaciones",
                        "/panel-inventario",
                        "/inventario/**"
                );
        // Nota: /usuarios/registro, /usuarios/guardar, /login, /logout, "/" y los recursos
        // estáticos quedan fuera de esta lista, por lo tanto siguen siendo públicos.
    }
}
