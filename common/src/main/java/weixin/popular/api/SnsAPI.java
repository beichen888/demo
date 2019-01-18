package weixin.popular.api;

import cn.com.demo.common.exception.AppException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.utils.URIBuilder;
import weixin.popular.bean.SnsToken;
import weixin.popular.bean.User;
import weixin.popular.client.JsonResponseHandler;

import java.net.URISyntaxException;

/**
 * 网页授权
 * @author LiYi
 *
 */
public class SnsAPI extends BaseAPI {

    /**
     * 通过code换取网页授权access_token
     * @param appid
     * @param secret
     * @param code
     * @return
     */
    public SnsToken oauth2AccessToken(String appid,String secret,String code){
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/sns/oauth2/access_token")
                .addParameter("appid", appid)
                .addParameter("secret", secret)
                .addParameter("code", code)
                .addParameter("grant_type", "authorization_code")
                .build();
        return localHttpClient.execute(httpUriRequest,JsonResponseHandler.createResponseHandler(SnsToken.class));
    }

    /**
     * 刷新access_token
     * @param appid
     * @param refresh_token
     * @return
     */
    public SnsToken oauth2RefreshToken(String appid,String refresh_token){
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/sns/oauth2/refresh_token")
                .addParameter("appid", appid)
                .addParameter("refresh_token", refresh_token)
                .addParameter("grant_type", "refresh_token")
                .build();
        return localHttpClient.execute(httpUriRequest,JsonResponseHandler.createResponseHandler(SnsToken.class));
    }

    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * @param access_token
     * @param openid
     * @param lang 国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
     * @return
     */
    public User userinfo(String access_token,String openid,String lang){
        HttpUriRequest httpUriRequest = RequestBuilder.get()
                .setUri(BASE_URI + "/sns/userinfo")
                .addParameter("access_token", access_token)
                .addParameter("openid", openid)
                .addParameter("lang", lang)
                .build();
        return localHttpClient.execute(httpUriRequest,JsonResponseHandler.createResponseHandler(User.class));
    }

    /**
     * 获得Code(需scope为 snsapi_base)
     */
    public User userbase(String appid){
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/connect/oauth2/authorize")
                .addParameter("appid", appid)
                .addParameter("redirect_uri", REDIRECT_URI)
                .addParameter("response_type", "code")
                .addParameter("scope", "snsapi_base")
                .setUri("#wechat_redirect")
                .build();
        return localHttpClient.execute(httpUriRequest,JsonResponseHandler.createResponseHandler(User.class));
    }

    public String oauth2Code(String appid, String url, String state) throws AppException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("open.weixin.qq.com")
                .setPath("/connect/oauth2/authorize")
                .addParameter("appid", appid)
                .addParameter("redirect_uri", url)
                .addParameter("response_type", "code")
                .addParameter("scope", "snsapi_base")
                .addParameter("state", state).setFragment("wechat_redirect");
        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new AppException();//TODO: add params to app exception
        }
    }

    /**
     * snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
     * @param appid
     * @param url
     * @param state
     * @return
     * @throws AppException
     */
    public String oauth2CodeInfo(String appid, String url, String state) throws AppException {
        URIBuilder uriBuilder = new URIBuilder();
        uriBuilder.setScheme("https").setHost("open.weixin.qq.com")
                .setPath("/connect/oauth2/authorize")
                .addParameter("appid", appid)
                .addParameter("redirect_uri", url)
                .addParameter("response_type", "code")
                .addParameter("scope", "snsapi_userinfo")
                .addParameter("state", state).setFragment("wechat_redirect");
        try {
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            throw new AppException();//TODO: add params to app exception
        }
    }
}
