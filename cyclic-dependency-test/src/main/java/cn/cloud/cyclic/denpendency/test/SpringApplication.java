package cn.cloud.cyclic.denpendency.test;

import cn.cloud.cyclic.denpendency.test.spring.SpringBeanA;
import cn.cloud.cyclic.denpendency.test.spring.SpringBeanB;
import cn.cloud.cyclic.denpendency.test.spring.SpringBeanC;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Spring
 *
 * @Author liangqiang
 * @Date 2020-03-14 14:52
 */
@SpringBootApplication
public class SpringApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext =
            org.springframework.boot.SpringApplication.run(SpringApplication.class, args);

        /*Object beanA = getBean(applicationContext, SpringBeanA.class);

        Object beanB = getBean(applicationContext, SpringBeanB.class);
        Object beanC = getBean(applicationContext, SpringBeanC.class);*/

        System.out.println(1);
    }

}
