package cn.com.demo.common;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User: demo
 * Date: 13-5-14
 * Time: 上午11:30
 */
public class Criteria implements Serializable{
    public Integer page;
    public Integer pageSize;
    public String order;
    public String orderProperty;
    public String orderDirection;
    public final Map<String, Object> conditionMap;

    public Criteria(){
        page = 1;
        conditionMap = new HashMap<>();
    }

    public Criteria(Integer pageSize, String order, Map<String, Object> conditionMap) {
        this.page = 1;
        this.pageSize = pageSize;
        this.order = order;
        this.conditionMap = conditionMap;
    }

    public Criteria(Integer pageSize, String order) {
        this.page = 1;
        this.pageSize = pageSize;
        this.order = order;
        this.conditionMap = Collections.emptyMap();
    }

    public void putAll(Map<String, String> params){
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (StringUtils.isNotBlank(entry.getValue())) {
                String key = entry.getKey();
                StringBuilder sb = new StringBuilder();
                for (Character c : key.toCharArray()) {
                    if (Character.isUpperCase(c)) {
                        sb.append("_").append(Character.toLowerCase(c));
                    } else {
                        sb.append(c);
                    }
                }
                conditionMap.put(sb.toString(), entry.getValue());
            }
        }
    }

    public int getStart(){
        return (page!=null&&pageSize!=null)? (page-1)*pageSize : 0;
    }

    public String getMySqlOrder(){
        return order;
    }

    public String getOrderProperty(){
        return orderProperty;
    }

    public String getOrderDirection(){
        return orderDirection;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}