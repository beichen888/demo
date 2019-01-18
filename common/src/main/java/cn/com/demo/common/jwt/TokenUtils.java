package cn.com.demo.common.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

import java.io.UnsupportedEncodingException;

/**
 * @author demo
 */
public class TokenUtils {

    public static final String SECRET = "@#@#@#3434FGFGf";

    public static final String ISSUER = "demo-api";

    public static String TOKEN_NAME = "access_token";

    public static String sign(UserToken userToken) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            String token = JWT.create().withIssuer(ISSUER)
                    .withClaim("id", userToken.getId())
                    .withClaim("username", userToken.getUsername())
                    .withClaim("name", userToken.getName())
                    .withClaim("role", userToken.getRole())
                    .withClaim("datetime", userToken.getDatetime())
                    .sign(algorithm);
            return token;
        } catch (UnsupportedEncodingException ex) {
            //UTF-8 encoding not supported
        } catch (JWTCreationException ex) {
            //Invalid Signing configuration / Couldn't convert Claims.
        }
        return null;
    }
}
