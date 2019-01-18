package cn.com.demo.api.controller.dto;

import java.io.Serializable;

/**
 * @author alearning
 * Created by miguo on 2017/10/20.
 */
public class PageDto implements Serializable {

    /**
     * 页码
     */
    private Integer pageNum;

    /**
     * 页面大小
     */
    private Integer pageSize;

    public PageDto() {
    }

    public Integer getPageNum() {
        if (pageNum == null) {
            pageNum = 1;
        }
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (pageSize == null) {
            pageSize = 20;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
