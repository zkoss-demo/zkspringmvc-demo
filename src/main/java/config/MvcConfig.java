package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import org.zkoss.zkspringmvc.ZKUrlBasedViewResolver;
import org.zkoss.zkspringmvc.ZKView;
import org.zkoss.zkspringmvc.config.ZKWebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "org.zkoss.zkspringmvc.demo" })
public class MvcConfig extends ZKWebMvcConfigurerAdapter {

    @Bean
    public ViewResolver getViewResolver() {
        ZKUrlBasedViewResolver resolver = new ZKUrlBasedViewResolver();
        resolver.setViewClass(new ZKView().getClass());
        resolver.setPrefix("/WEB-INF/");
        resolver.setSuffix("");
        return resolver;
    }

}
