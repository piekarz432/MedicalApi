package pl.com.britenet.javastepone.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
