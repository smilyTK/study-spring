package com.ausware.thinking.in.spring.ioc.bean.scope;

import com.ausware.thinking.in.spring.ioc.overview.entity.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.util.Map;

/**
 * bean的作用域实例
 *
 * @author
 * @create 2020-04-19 11:45
 **/
public class BeanScopeDemo implements DisposableBean {

    @Bean
    //默认 scope 是 singleton
    public static User singletonUser() {
        return createUser();
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public static User prototypeUser() {
        return createUser();
    }

    /**
     * 创建User方法
     * @return User
     */
    private static User createUser() {
        User user = new User();
        user.setId(System.nanoTime());
        return user;
    }

    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser;
    @Autowired
    @Qualifier("singletonUser")
    private User singletonUser1;

    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser;
    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser1;
    @Autowired
    @Qualifier("prototypeUser")
    private User prototypeUser2;

    @Autowired
    private Map<String, User> users;

    @Autowired
    private ConfigurableListableBeanFactory beanFactory;

    public static void main(String[] args) {

        //创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        //注册 Configuration class
        applicationContext.register(BeanScopeDemo.class);

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.addBeanPostProcessor(new BeanPostProcessor() {
                @Override
                public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {

                    return bean;
                }
            });
        });

        //启动应用上下文
        applicationContext.refresh();

        //依赖查找
        //结论一 ：
        // 依赖注入对象时
        // singletonUser bean 无论依赖查找还是依赖注入，均为一个对象
        // prototypeUser bean 无论依赖查找还是依赖注入，均为新生对象


        //结论二：
        //  依赖注入集合对象时，Singleton Bean 和 Prototype Bean 均会存在一个
        // prototypeUser bean 有别于其他地方的依赖注入 Prototype Bean


        //结论三：
        // singleton bean 、prototypeUser bean 均会执行初始化方法回调
        // 仅 singleton bean 会执行销毁方法回调
        scopeBeanByLookup(applicationContext);

        //依赖注入
        scopeBeanByInjection(applicationContext);

        //关闭应用上下文
        applicationContext.close();
    }

    private static void scopeBeanByLookup(AnnotationConfigApplicationContext applicationContext) {
        for (int i = 0; i < 3; i++) {
            // singletonUser 是共享 Bean 对象
            User singletonUser = applicationContext.getBean("singletonUser", User.class);
            System.out.println("singletonUser = " + singletonUser);
            // prototypeUser 是每次依赖查找均生成了新的 Bean 对象
            User prototypeUser = applicationContext.getBean("prototypeUser", User.class);
            System.out.println("prototypeUser = " + prototypeUser);
        }
    }

    private static void scopeBeanByInjection(AnnotationConfigApplicationContext applicationContext) {
       BeanScopeDemo demo = applicationContext.getBean(BeanScopeDemo.class);
        System.out.println(" demo.singletonUser = " + demo.singletonUser);
        System.out.println(" demo.singletonUser1 = " + demo.singletonUser1);
        System.out.println(" demo.prototypeUser = " + demo.prototypeUser);
        System.out.println(" demo.prototypeUse1 = " + demo.prototypeUser1);
        System.out.println(" demo.prototypeUse2 = " + demo.prototypeUser2);
        System.out.println(" demo.users = " + demo.users);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("销毁prototype bean。。。");
        this.prototypeUser.destory();
        this.prototypeUser1.destory();
        this.prototypeUser2.destory();
        //需要销毁依赖注入的集合类型对象
        for(Map.Entry<String, User> entry : users.entrySet()) {
            BeanDefinition bd = beanFactory.getBeanDefinition(entry.getKey());
            if(bd.isPrototype()) {
                User user = entry.getValue();
                //手动销毁bean
                user.destory();
            }
        }
    }

    // prototype User 无法完成bean的生命周期


}
