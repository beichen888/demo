package cn.com.demo.common.rpc.service;

import cn.com.demo.common.rpc.entity.RpcHelloWorld;

/**
 * Created by zhangweiqing on 2016/01/18.
 */
public interface IRpcHelloWorldService {

   public static final String HELLOWORLD_SERVICE = "/remote/helloWorldService";

   RpcHelloWorld sayHello(String name);

}
