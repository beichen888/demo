package cn.com.demo.api.interceptor;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.common.jwt.TokenUtils;
import cn.com.demo.common.jwt.UserToken;
import cn.com.demo.api.common.MessageCode;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.*;

/**
 * Jwt拦截器
 *
 * @author alearning
 *         Created by lzg on 16/10/14.
 */
public class JwtInterceptor extends HandlerInterceptorAdapter {
    /**
     * uri path
     */
    private List<String> paths;
    /**
     * 过期时间
     */
    private long expire;

    /**
     * 登录地址
     */
    private String loginUrl;

    Algorithm algorithm = null;

    private JWTVerifier jwtVerifier = null;

    private static Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    public JwtInterceptor() {
        try {
            algorithm = Algorithm.HMAC256(TokenUtils.SECRET);
        } catch (UnsupportedEncodingException exception) {
            logger.error("UTF-8 encoding not supported");
        } catch (JWTCreationException exception) {
            logger.error("Invalid Signing configuration / Couldn't convert Claims");
        }
        if (algorithm != null) {
            jwtVerifier = JWT.require(algorithm).withIssuer(TokenUtils.ISSUER).build();
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException, ServletException {
        // url和参数log
        printLog(request);
        if("OPTIONS".equalsIgnoreCase(request.getMethod())){
            return true;
        }
        String authorization = request.getHeader("Authorization");
        if (authorization == null) {
            authorization = request.getParameter(TokenUtils.TOKEN_NAME);
        }
        if (authorization != null && authorization.startsWith("Bearer ")) {
            authorization = authorization.substring("Bearer ".length());
        }

        String rules = match(request.getRequestURI().substring(request.getContextPath().length()));
        if (rules != null && !Objects.equals(rules, "anon")) {
            boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
            if (authorization == null) {
                logger.info("403: authorization == null");
                writeInvalid(response, 403);
                return false;
            }
            try {
                if (validate(rules, authorization)) {
                    DecodedJWT decodedJWT = jwtVerifier.verify(authorization);
                    Map<String, Claim> claims = decodedJWT.getClaims();
                    UserToken userToken = new UserToken(claims.get("id").asInt(), claims.get("username").asString(), claims.get("name") == null ? null : claims.get("name").asString(), claims.get("role").asString());
                    request.setAttribute("user", userToken);
                    return true;
                } else {
                    logger.info("403:validate(rules, authorization)");
                    writeInvalid(response, 403);
                    return false;
                }
            } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                writeInvalid(response, "登录验证失败, 请反馈给管理员!");
                return false;
            } catch (SignatureException e) {
                writeInvalid(response, "登录验证失败!");
                return false;
            } catch (AppException e) {
                writeInvalid(response, "登录已过期,请重新登录!");
                return false;
            }
        }
        return true;
    }

    private void writeInvalid(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write("{\"success\": false, \"msg\":\"" + msg + "\"}");
        response.getWriter().close();
    }

    private void writeInvalid(HttpServletResponse response, Integer status) throws IOException {
        logger.info("writeInvalid: " + status);
        response.sendError(status);
    }

    private boolean validate(String rule, String authorization) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException, AppException {
        String[] rules = rule.split(",");
        if (rules.length == 0 || rules.length > 2) {
            logger.error("error to config validation rule, pls check your spring xml files");
            return false;
        }

        Map<String, Claim> claims;
        try {
            claims = jwtVerifier.verify(authorization).getClaims();
        } catch (Exception ex) {
            logger.error("错误的token：" + authorization);
            throw new AppException(MessageCode.TOKEN_NOT_CORRECT_ERROR);
        }

        Long datetime = claims.get("datetime").asLong();

        checkExpire(datetime);

        if (rules.length == 2) {
            if ("authc".equals(rules[0].trim()) && rules[1].trim().startsWith("role[")) {
                String roles = rules[1].trim();
                String role = claims.get("role").asString();
                String[] roleNames = roles.substring("role[".length(), roles.length() - 1).split(";");
                for (String roleName : roleNames) {
                    if (roleName.trim().equals(role)) {
                        return true;
                    }
                }
                return false;
            } else {
                logger.error("error to config validation rule, pls check your spring xml files");
                return false;
            }
        }
        return true;
    }

    private void checkExpire(Long datetime) throws AppException {
        if (datetime == null) {
            throw new AppException();
        } else if (Calendar.getInstance().getTimeInMillis() - datetime > expire) {
            throw new AppException(MessageCode.TOKEN_TIME_OUT_ERROR);
        }
    }

    private String match(String uri) {
        for (String path : paths) {
            String[] keyValue = path.split("=");
            String key = keyValue[0].trim();
            if (match(key, uri)) {
                return keyValue[1].trim();
            }
        }
        return null;
    }

    private boolean match(String path, String uri) {
        if ("".equals(uri)) {
            return false;
        }
        if (path.endsWith("/**")) {
            path = path.substring(0, path.lastIndexOf("/"));
        }
        return path.equals(uri) || match(path, uri.substring(0, uri.lastIndexOf("/")));
    }

    private void printLog(HttpServletRequest request) {
        //输出url
        logger.info("request url: " + request.getRequestURI());
        Map<String, String[]> parameterMap = request.getParameterMap();
        Enumeration parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = (String) parameterNames.nextElement();
            String paramValue = request.getParameter(paramName);
        }
    }

    public void setPaths(List<String> paths) {
        this.paths = paths;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public void setExpire(long expire) {
        this.expire = expire;
    }
}
