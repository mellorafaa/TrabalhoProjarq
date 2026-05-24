package com.bcopstein.ex4_lancheriaddd_v1;
//Configuração MVC que registra o JWTFilter como interceptor em todas as rotas, exceto as públicas
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.bcopstein.ex4_lancheriaddd_v1.Adaptadores.Apresentacao.Seguranca.JWTFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  
  private final JWTFilter jwtFilter;
  
  public WebConfig(JWTFilter jwtFilter) {
    this.jwtFilter = jwtFilter;
  }
  
  //Registra o JWTFilter para interceptar todas as rotas, liberando login, h2, health e cadastro
  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(jwtFilter)
      .addPathPatterns("/**")
      .excludePathPatterns("/autenticacao/login", "/h2/**", "/health", "/clientes");
  }
}
