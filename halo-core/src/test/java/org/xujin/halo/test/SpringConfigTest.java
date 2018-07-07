package org.xujin.halo.test;

import org.xujin.halo.TestConfig;
import org.xujin.halo.boot.Bootstrap;
import org.xujin.halo.boot.RegisterFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringConfigTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TestConfig.class);
        Bootstrap bootstrap = (Bootstrap)context.getBean("bootstrap");
        RegisterFactory registerFactory = (RegisterFactory)context.getBean("registerFactory");
        System.out.println(registerFactory);
        System.out.println(bootstrap.getPackages());
    }
}
