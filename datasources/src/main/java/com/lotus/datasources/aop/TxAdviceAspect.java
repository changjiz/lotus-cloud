package com.lotus.datasources.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: datasources
 * @description:
 * @author: changjiz
 * @create: 2019-07-10 13:58
 **/
@Aspect
@Configuration
@ConditionalOnProperty("sys.config.txpointcut")
@Slf4j
public class TxAdviceAspect {

    @Value("${sys.config.txpointcut}")
    private String TX_POINTCUT;
//    private static final String AOP_POINTCUT_EXPRESSION = "execution (* com.sys.*.*.impl.*.*(..))";

    @Autowired(required = false)
    private PlatformTransactionManager transactionManager;

    @Bean
    public TransactionInterceptor txAdvice() {
        log.info(" txpointcut ------->  " + TX_POINTCUT);
        NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();
        /* 当前没有事务：只有select，非事务执行；有update，insert，delete操作，自动提交；
         * 当前有事务：如果有update，insert，delete操作，支持当前事务 */
        RuleBasedTransactionAttribute readOnlyTx = new RuleBasedTransactionAttribute();
        readOnlyTx.setReadOnly(true);
        readOnlyTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_SUPPORTS);
        /* 当前存在事务就使用当前事务，当前不存在事务就创建一个新的事务 */
        RuleBasedTransactionAttribute requiredTx = new RuleBasedTransactionAttribute();
        requiredTx.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class)));
        requiredTx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        // requiredTx.setTimeout(TX_METHOD_TIMEOUT);
        Map<String, TransactionAttribute> txMap = new HashMap<>();
        txMap.put("list*", readOnlyTx);
        txMap.put("query*", readOnlyTx);
        txMap.put("get*", readOnlyTx);
        txMap.put("find*", readOnlyTx);
        txMap.put("*", requiredTx);
        source.setNameMap(txMap);
        TransactionInterceptor txAdvice = new TransactionInterceptor(transactionManager, source);
        return txAdvice;
    }

    @Bean
    @Lazy
    public Advisor txAdviceAdvisor(TransactionInterceptor txAdvice) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        pointcut.setExpression("execution (" + TX_POINTCUT + ")");
        return new DefaultPointcutAdvisor(pointcut, txAdvice);
    }
}
