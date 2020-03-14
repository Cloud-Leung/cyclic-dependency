package cn.cloud.cyclic.denpendency.test.bean;

/**
 * @Author liangqiang
 * @Date 2020-03-14 15:10
 */
public class BeanA {

    private BeanB beanB;

    public BeanA(BeanB beanB) {
        this.beanB = beanB;
    }

}
