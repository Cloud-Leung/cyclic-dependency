package cn.cloud.cyclic.denpendency.test;

import cn.cloud.cyclic.denpendency.test.spring.SpringBeanA;
import cn.cloud.cyclic.denpendency.test.spring.SpringBeanB;
import cn.cloud.cyclic.denpendency.test.spring.SpringBeanC;
import cn.cloud.cyclic.dependency.core.CloudContext;

/**
 * 用自己的方式实现的解决bean循环依赖问题，
 * <p>
 * 如果只是简单的属性注入依赖则可以直接解决
 * 如果是构造器依赖出现循环，也可以自动发现构造器循环并抛出异常
 *
 * @Author liangqiang
 * @Date 2020-03-14 14:52
 */
public class Application {

    public static void main(String[] args) {
        CloudContext context = new CloudContext();
        SpringBeanA beanA = context.getBean(SpringBeanA.class);
        System.out.println(beanA);

        SpringBeanB beanB = context.getBean(SpringBeanB.class);
        System.out.println(beanB);

        SpringBeanC beanC = context.getBean(SpringBeanC.class);
        System.out.println(beanC);

    }

}
