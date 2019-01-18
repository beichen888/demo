package cn.com.demo.common.rpc.service;

import weixin.popular.bean.Ticket;
import weixin.popular.bean.Token;

public interface IRpcWxService {
    String WXService = "/WxService";

    Token accessToken(String appid, String appSecret);

    Ticket getTicket(String appid, String appSecret);
}
