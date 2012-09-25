package com.sohu.sur.test;

import com.sohu.sur.spring.SurDomainConfig;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * User: 郭勇
 * Date: 2011-3-14 16:32:01
 */
public class SpringTests {

    public static ApplicationContext ctx;

    @BeforeClass
    public static void beforeClass() {
        ctx = new AnnotationConfigApplicationContext(SurDomainConfig.class);
    }

    protected <T> T getBean(Class<T> requiredType) {
        return ctx.getBean(requiredType);
    }

    protected Object getBean(String beanName) {
        return ctx.getBean(beanName);
    }

    protected String getDbName() {
        return ctx.getBean(SurDomainConfig.class).getDbName();
    }

//    @Test
//    public void testEnv() {
//
//        Mongo mongo = getBean(Mongo.class);
//        Datastore datastore = getBean(Datastore.class);
//
//        Assert.assertNotNull(mongo);
//        Assert.assertNotNull(datastore);
//
//    }
}
