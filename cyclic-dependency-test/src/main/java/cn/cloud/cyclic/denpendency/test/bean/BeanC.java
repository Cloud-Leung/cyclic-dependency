package cn.cloud.cyclic.denpendency.test.bean;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author liangqiang
 * @Date 2020-03-14 15:11
 */
public class BeanC {

    @Autowired
    private BeanA beanA;

   /* public BeanC(BeanA beanA) {
        this.beanA = beanA;
    }*/

    @Override
    public String toString() {
        return "BeanC{" + "beanA=" + beanA + '}';
    }
}
