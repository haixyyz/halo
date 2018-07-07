package org.xujin.halo;

import org.xujin.halo.boot.Bootstrap;
import org.springframework.context.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TestConfig
 *
 * @author xujin 2018-01-06 7:57 AM
 */
@Configuration
@ComponentScan("org.xujin.halo")
@PropertySource(value = {"/sample.properties"})
public class TestConfig {

    @Bean(initMethod = "init")
    public Bootstrap bootstrap() {
        Bootstrap bootstrap = new Bootstrap();
        List<String> packagesToScan  = new ArrayList<>();
        packagesToScan.add("org.xujin.halo.test");
        bootstrap.setPackages(packagesToScan);
        return bootstrap;
    }
}
