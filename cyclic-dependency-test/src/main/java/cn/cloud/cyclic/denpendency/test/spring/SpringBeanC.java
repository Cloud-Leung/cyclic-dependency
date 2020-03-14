package cn.cloud.cyclic.denpendency.test.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @Author liangqiang
 * @Date 2020-03-14 15:11
 */
@Component
public class SpringBeanC {

    @Autowired
    private SpringBeanA beanA;

    @Override
    public String toString() {
        return Objects.isNull(beanA) ? "缺少beanA属性" : "创建成功";
    }
}
