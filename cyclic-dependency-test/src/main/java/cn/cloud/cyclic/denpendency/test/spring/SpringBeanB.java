package cn.cloud.cyclic.denpendency.test.spring;

import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author liangqiang
 * @Date 2020-03-14 15:11
 */
@Component
public class SpringBeanB {

    private SpringBeanC beanC;

    public SpringBeanB(SpringBeanC beanC) {
        this.beanC = beanC;
    }

    @Override
    public String toString() {
        return Objects.isNull(beanC) ? "缺少beanC属性" : "创建成功";
    }
}
