package pl.com.britenet.javastepone.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class MethodLogger implements BeanPostProcessor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object postProcessAfterInitialization(Object bean, @NonNull String beanName) {
        var annotation = AnnotationUtils.findAnnotation(bean.getClass(), Service.class);
        if (annotation != null) {
            var proxyFactory = new ProxyFactory(bean);
            proxyFactory.addAdvice((MethodBeforeAdvice) (method, methodArgs, target) ->
                    logger.debug("Invoked method: {}#{} with args: {}", bean.getClass().getSimpleName(), method.getName(), methodArgs));
            proxyFactory.setExposeProxy(true);

            return proxyFactory.getProxy();
        }

        return bean;
    }
}
