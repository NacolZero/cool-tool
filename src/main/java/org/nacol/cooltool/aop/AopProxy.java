package org.nacol.cooltool.aop;

import org.springframework.aop.framework.AopContext;

/**
 * @Author Nacol(姚秋实)
 * @Date 2022/10/29
 * @Description AOP代理
 * 背景：
 * 被 AOP 增强的某一个类，调用内部方法时，本质是 this.method()，因此没有通过代理调用 method()，导致 AOP 失效。
 *
 * 解决：
 * 改类可获取当前的代理对象，即 proxy.mehtod()，因此会有AOP增强。
 *
 * 常见场景：
 * @Transactional @Async 或自定义注解的切面处理。
 *
 * 注意：
 * 1. 启动类增加注解 @EnableAspectJAutoProxy(exposeProxy = true)
 * 2. 如果使用多线程的匿名内部类需要将代理对象传入，而不能在匿名内部类中直接使用 self()，否则会获取匿名内部类的代理。
 */
public interface AopProxy<T> {

    default T self(){
        return (T)AopContext.currentProxy();
    }

}
