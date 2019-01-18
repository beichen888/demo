package cn.com.demo.common.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * User: demo
 * Date: 14-4-30
 * Time: 下午3:09
 */
public class HasAnyRoleFilter extends AuthorizationFilter {
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        Subject subject = getSubject(request, response);
        String[] rolesArray = (String[]) mappedValue;
        if (rolesArray == null || rolesArray.length == 0) {
            return true;
        }

        boolean hasRole = false;
        for(String role : rolesArray){
            if(subject.hasRole(role.trim())){
                hasRole = true;
                break;
            }
        }
        return hasRole;
    }
}
