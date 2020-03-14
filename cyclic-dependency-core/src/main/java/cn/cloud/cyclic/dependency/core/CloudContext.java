package cn.cloud.cyclic.dependency.core;

import cn.cloud.cyclic.dependency.core.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * @Author liangqiang
 * @Date 2020-03-14 14:46
 */
@SuppressWarnings("unchecked")
public class CloudContext {

    /**
     * 一级缓存 缓存已经实例化好的对象
     */
    private static final ConcurrentHashMap<Class<?>, Object> beanMap = new ConcurrentHashMap<>();

    /**
     * 二级缓存 缓存已经创建的实例但还没做属性初始化的对象
     */
    private static final ConcurrentHashMap<Class<?>, Object> earlyBeanMap = new ConcurrentHashMap<>();

    /**
     * bean工厂
     */
    private static final ConcurrentHashMap<Class<?>, BeanFactory> beanFacotyMap = new ConcurrentHashMap<>();

    /**
     * bean创建后的实例化监听
     */
    public static final ConcurrentHashMap<Class, List<Consumer>> eventConsumer = new ConcurrentHashMap<>();

    public static final List<Class> createing = new ArrayList<>();

    public <T> T getBean(Class<?> clazz) {
        // 1,首先从一级缓存beanMap中获取
        Object o = beanMap.get(clazz);
        if (Objects.isNull(o)) {
            o = earlyBeanMap.get(clazz);
            if (Objects.isNull(o)) {
                o = this.createBean(clazz);
            }
        }
        return (T)o;
    }

    private Object createBean(Class<?> clazz) {
        BeanFactory beanFactory = createBeanFacoty(clazz);
        if (beanFactory.isHasParamConstructor()) {
            Set<Object> set = new HashSet<>();
            if (createing.contains(clazz)) {
                throw new RuntimeException("出现循环依赖了: " + toError());
            }
            createing.add(clazz);
            beanFactory.getConstructorPrams().forEach(o -> {
                Object object = this.getBean(o);
                set.add(object);
            });
            Object o = beanFactory.getObject(set.toArray());
            createing.remove(clazz);
            earlyBeanMap.put(clazz, o);
            execueteConsuemr(o);
            this.initDependency(beanFactory, o);
            earlyBeanMap.remove(clazz);
            beanMap.put(clazz, o);
            return o;
        } else {
            createing.add(clazz);
            Object o = beanFactory.getObject();
            createing.remove(clazz);
            earlyBeanMap.put(clazz, o);
            execueteConsuemr(o);
            this.initDependency(beanFactory, o);
            earlyBeanMap.remove(clazz);
            beanMap.put(clazz, o);
            return o;
        }
    }

    private void initDependency(BeanFactory beanFactory, Object object) {
        Set<Field> set = beanFactory.getDependencyFields();
        set.forEach(o -> {
            if (createing.contains(o.getType())) {
                List<Consumer> list = eventConsumer.getOrDefault(o.getType(), new ArrayList<>());
                list.add(p -> ReflectionUtils.setFieldValue(o, object, p));
                eventConsumer.put(o.getType(), list);
            } else {
                Object bean = this.getBean(o.getType());
                ReflectionUtils.setFieldValue(o, object, bean);
            }
        });
    }

    private String toError() {
        StringBuilder sb = new StringBuilder();
        createing.forEach(o -> {
            sb.append(o.getSimpleName());
            sb.append(" -> ");
        });
        sb.append(createing.get(0).getSimpleName());
        return sb.toString();
    }

    private BeanFactory createBeanFacoty(Class<?> clazz) {
        BeanFactory beanFactory = new BeanFactory(clazz);
        beanFacotyMap.put(clazz, beanFactory);
        return beanFactory;
    }

    private void execueteConsuemr(Object bean) {
        List<Consumer> list = eventConsumer.getOrDefault(bean.getClass(), new ArrayList<>());
        list.forEach(o -> o.accept(bean));
        eventConsumer.remove(bean.getClass());
    }
}
