package cn.cloud.cyclic.denpendency.test.spring;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author liangqiang
 * @Date 2020-03-14 15:10
 */
@Component
public class SpringBeanA {

    private SpringBeanB beanB;

    public SpringBeanA(SpringBeanB beanB) {
        this.beanB = beanB;
    }

    @Override
    public String toString() {
        return Objects.isNull(beanB) ? "缺少beanB属性" : "创建成功";
    }
}
