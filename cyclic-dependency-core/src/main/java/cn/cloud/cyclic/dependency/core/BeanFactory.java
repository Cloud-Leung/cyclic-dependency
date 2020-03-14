package cn.cloud.cyclic.dependency.core;

import cn.cloud.cyclic.dependency.core.util.ReflectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @Author liangqiang
 * @Date 2020-03-14 14:53
 */
@SuppressWarnings("unchecked")
public class BeanFactory {

    /**
     * 待实例化对象的类型
     */
    private Class clazz;

    /**
     * 依赖的对象类型
     */
    private Set<Field> dependencyFields;

    /**
     * 依赖的对象类型
     */
    private Set<Class> dependencys;

    /**
     * 依赖的构造函数参数
     */
    private Set<Class> constructorPrams;

    /**
     * 可实例化的构造方法
     */
    private Constructor constructor;

    /**
     * 是否是有参构造方法
     */
    private boolean hasParamConstructor;

    public BeanFactory(Class clazz) {
        this.clazz = clazz;
        this.dependencyFields = new HashSet<>();
        this.dependencys = new HashSet<>();
        this.constructorPrams = new HashSet<>();
        this.init();
    }

    public Object getObject(Object ... initargs) {
        try {
            return constructor.newInstance(initargs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Class getClazz() {
        return clazz;
    }

    public Set<Field> getDependencyFields() {
        return dependencyFields;
    }

    public Set<Class> getConstructorPrams() {
        return constructorPrams;
    }

    public boolean isHasParamConstructor() {
        return hasParamConstructor;
    }

    private void init() {
        this.initField();
        this.initConstructor();
    }

    private void initConstructor() {
        Iterable<Constructor<?>> constructors = ReflectionUtils.constructorsof(clazz);
        for (Constructor<?> constructor : constructors) {
            this.constructor = constructor;
            Class<?>[] classes = constructor.getParameterTypes();
            this.constructorPrams.addAll(Arrays.asList(classes));
            this.dependencys.addAll(this.constructorPrams);
            this.hasParamConstructor = classes.length > 0;
            break;
        }
    }

    private void initField() {
        Iterable<Field> fields = ReflectionUtils.fieldsOf(this.clazz);
        fields.forEach(o -> {
            Autowired autowired = o.getAnnotation(Autowired.class);
            if (Objects.nonNull(autowired)) {
                this.dependencys.add(o.getType());
                this.dependencyFields.add(o);
            }
        });
    }
}
