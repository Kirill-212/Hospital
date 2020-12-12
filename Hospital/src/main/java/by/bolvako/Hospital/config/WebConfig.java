package by.bolvako.Hospital.config;

import lombok.var;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Bean
//    public ClassLoaderTemplateResolver templateResolver() {
//        var templateResolver = new ClassLoaderTemplateResolver();
//        templateResolver.setPrefix("templates/WEB-INF/");
//        templateResolver.setCacheable(false);
//        templateResolver.setSuffix(".html");
//        templateResolver.setTemplateMode("HTML5");
//        templateResolver.setCharacterEncoding("UTF-8");
//        return templateResolver;
//    }
//
//    @Bean
//    public SpringTemplateEngine templateEngine() {
//        var templateEngine = new SpringTemplateEngine();
//        templateEngine.setTemplateResolver(templateResolver());
//        return templateEngine;
//    }
//
//
//    @Bean
//    public ViewResolver viewResolver() {
//        var viewResolver = new ThymeleafViewResolver();
//        viewResolver.setTemplateEngine(templateEngine());
//        viewResolver.setCharacterEncoding("UTF-8");
//        return viewResolver;
//    }
@Bean
public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder;
}
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/patient").setViewName("Patient");
        registry.addViewController("/doctor").setViewName("Doctor");
        registry.addViewController("/reg").setViewName("Register");
        registry.addViewController("/aut").setViewName("Autorisation");
        registry.addViewController("/admin").setViewName("Admin");
        registry.addViewController("/admin/admin/addDoctor").setViewName("Doctor");
        registry.addViewController("/admin/admin/addPatient").setViewName("Patient");
    }
}
