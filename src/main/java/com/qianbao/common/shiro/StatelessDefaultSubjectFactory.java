package com.qianbao.common.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;

/**
 * @author lijiechu
 * @create on 17/9/12
 * @description 关闭session的创建
 */
public class StatelessDefaultSubjectFactory extends DefaultWebSubjectFactory {

    @Override
    public Subject createSubject(SubjectContext context){
        // 不创建session
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
    }
}
