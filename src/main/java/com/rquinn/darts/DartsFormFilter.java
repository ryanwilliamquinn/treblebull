package com.rquinn.darts;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: rquinn
 * Date: 12/18/12
 * Time: 8:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class DartsFormFilter extends FormAuthenticationFilter {

    protected void setFailureAttribute(ServletRequest request, AuthenticationException ae) {
        String message = ae.getMessage();
        request.setAttribute("loginFailure", message);
    }

    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        boolean isAllowed = super.isAccessAllowed(request, response, mappedValue);
        return isAllowed;

    }

}
