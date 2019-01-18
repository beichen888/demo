package cn.com.demo.api.controller.dto.response;

import cn.com.demo.api.entity.Comments;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by miguo on 2019/1/14.
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CommentsDto extends Comments {
    private String nickName;

    private String avatarUrl;
}
