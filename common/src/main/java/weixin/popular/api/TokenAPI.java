package weixin.popular.api;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import weixin.popular.bean.Ticket;
import weixin.popular.bean.Token;
import weixin.popular.client.JsonResponseHandler;

import java.util.concurrent.ConcurrentHashMap;

public class TokenAPI extends BaseAPI {
	private final static ConcurrentHashMap<String, Token> tokenMap = new ConcurrentHashMap<>();

	/**
	 * 获取access_token
	 * @param appid
	 * @param secret
	 * @return
	 */
	public Token token(String appid,String secret){
		Token token = tokenMap.get(appid);
		if(token==null || !token.isValid()){
			HttpUriRequest httpUriRequest = RequestBuilder.get()
					.setUri(BASE_URI + "/cgi-bin/token?grant_type=client_credential&appid=" + appid +"&secret=" + secret)
					.build();
			token = localHttpClient.execute(httpUriRequest,JsonResponseHandler.createResponseHandler(Token.class));
			tokenMap.putIfAbsent(appid, token);
		}
		return token;
	}

    public Ticket getTicket(String token){
        HttpUriRequest httpUriRequest = RequestBuilder.post()
                .setUri(BASE_URI + "/cgi-bin/ticket/getticket")
                .addParameter("access_token", token)
                .addParameter("type", "jsapi")
                .build();
        return localHttpClient.execute(httpUriRequest,JsonResponseHandler.createResponseHandler(Ticket.class));
    }

}
