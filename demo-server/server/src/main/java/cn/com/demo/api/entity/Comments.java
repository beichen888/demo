package cn.com.demo.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户评论
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Comments extends BaseEntity<Comments> {

    @NotNull
    @TableField("user_id")
    private Long userId;
    @NotNull
    @TableField("channel_id")
    private Long channelId;
    @NotBlank
    @Length(max = 200,message = "评论内容不能超过200个字符")
    private String content;
    @NotBlank
    private String style;

    @Override
    public String toString() {
        return "Comments{" +
                "id=" + getId() +
                ", userId=" + userId +
                ", channel_id=" + channelId +
                ", content=" + content +
                ", style=" + style +
                "}";
    }
}
