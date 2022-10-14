package com.lotus.common.configure;

import com.lotus.common.utils.Constants;
import com.lotus.common.utils.SpringContextUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.annotation.PostConstruct;

/**
 * @program: common
 * @description:
 * @author: changjiz
 * @create: 2019-10-14 19:02
 **/
@Configuration
@Import(value = SpringContextUtils.class)
public class ApplyConfiguration {

    @Value("${spring.profiles.active}")
    private String SPRING_ACTIVE;

    @Value("${spring.application.name}")
    private String APPLY;

    @Value("${apply.id:1}")
    private Long APPLY_ID;

    @PostConstruct
    public void setApplyData() {
        Constants.ACTIVE = SPRING_ACTIVE;
        Constants.APPLY = APPLY;
        Constants.APPLY_ID = APPLY_ID;
    }

}
