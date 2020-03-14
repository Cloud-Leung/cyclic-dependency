package cn.cloud.cyclic.denpendency.test.bean;

/**
 * @Author liangqiang
 * @Date 2020-03-14 15:11
 */
public class BeanB {

    private BeanC beanC;

    public BeanB(BeanC beanC) {
        this.beanC = beanC;
    }
}
