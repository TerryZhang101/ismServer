package com.ismServer.common.aspect;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ismServer.common.annotation.SessionCheck;
import com.ismServer.common.domain.LoginLog;
import com.ismServer.common.form.BaseForm;
import com.ismServer.common.repository.LoginLogRepository;
import com.ismServer.common.service.SessionService;
import com.ismServer.common.util.RequestUtil;

/**
 * 控制器切面
 *
 * @author Terry Zhang
 * @date 2017-09-19 九月 21:03
 * @modify
 **/
@Aspect
@Order(-1)
@Component
public class ControllerAspect {

    private Logger logger = LoggerFactory.getLogger(getClass());

    ThreadLocal<Long> startTime = new ThreadLocal<>();
    
    @Autowired
    private SessionService sessionService;
    
    @Autowired
    private LoginLogRepository loginLogRepository;
    
    @Around("execution(* com.ismServer.bussiness..*Controller.*(..))")
    public Object doAspect(ProceedingJoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());

        String sessionToken = null;
        String clientIp = null;
        String httpSessionId = null;

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //提取参数
        Object[] joinPointArguments = joinPoint.getArgs();

        // 记录下请求内容
        logger.info("URL : " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD : " + request.getMethod());
        logger.info("IP : " + request.getRemoteAddr());
        logger.info("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS : " + Arrays.toString(joinPoint.getArgs()));

        clientIp = RequestUtil.getClientIP(request);
        httpSessionId = request.getSession().getId();
        if(StringUtils.isEmpty(httpSessionId)) {
            httpSessionId = "httpSessionId";
        }

        try {
            for(Object obj : joinPoint.getArgs()){
                if(obj instanceof BaseForm){
                    BaseForm baseForm = (BaseForm) obj;
                    sessionToken = baseForm.getRequestToken();
                    baseForm.setClientIp(clientIp);
                    baseForm.setHttpSessionId(httpSessionId);
                }
            }
        } catch (Exception e) {
            logger.error("ControllerAspect.doBefore --> Exception:{}", e);
            throw e;
        }


        SessionCheck sessionCheckAnnotation = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(SessionCheck.class);
        /*
         * 安全检查,提取注解@SessionCheck,只有写了@SessionCheck(false)才不检查
         */
        if(sessionCheckAnnotation == null || sessionCheckAnnotation.value()) {
//        	sessionService.checkSessionAndAccessAuth(sessionToken, httpSessionId, clientIp);
        }

        LoginLog loginLog = new LoginLog();
        loginLog.setClientIp(clientIp);
        loginLog.setCreateDate(new Date());
        loginLog.setSessionToken(sessionToken);
        loginLogRepository.saveAndFlush(loginLog);
        
        Object result = joinPoint.proceed(joinPointArguments);
        if(sessionCheckAnnotation == null || sessionCheckAnnotation.value()) {
        	sessionService.updateSession(sessionToken, httpSessionId, clientIp);
        }
        
        return result;

    }
}
