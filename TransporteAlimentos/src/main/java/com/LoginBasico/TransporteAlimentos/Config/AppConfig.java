package com.LoginBasico.TransporteAlimentos.Config;

import com.LoginBasico.TransporteAlimentos.Security.AutenticacionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<AutenticacionFilter> filtroAutenticacion(AutenticacionFilter autenticacionFilter) {
        FilterRegistrationBean<AutenticacionFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(autenticacionFilter);
        registrationBean.addUrlPatterns("/api/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
