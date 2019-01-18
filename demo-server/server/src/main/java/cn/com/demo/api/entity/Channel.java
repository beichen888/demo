package cn.com.demo.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 频道信息
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Channel extends BaseEntity<Channel> {

    private static final long serialVersionUID = 1L;

    /**
     * 类别
     */
    @NotNull
    private Long categoryId;
    /**
     * 频道封面
     */
    @NotBlank
    private String cover;
    /**
     * 频道标题
     */
    @NotBlank
    private String title;
    /**
     * 频道介绍
     */
    @Length(max = 1000,message = "频道介绍不能超过1000个字符")
    @NotBlank(message = "频道介绍不能为空")
    private String introduction;
    /**
     * 朗读者
     */
    @NotBlank
    private String reader;
    /**
     * 朗读者头像
     */
    @NotBlank
    @TableField("reader_avatar")
    private String readerAvatar;
    /**
     * 朗读者介绍
     */
    @Length(max = 1000,message = "朗读者介绍不能超过1000个字符")
    @TableField("reader_introduction")
    private String readerIntroduction;

    @Override
    public String toString() {
        return "Channel{" +
        "id=" + getId() +
        ", category=" + categoryId +
        ", cover=" + cover +
        ", title=" + title +
        ", introduction=" + introduction +
        ", reader=" + reader +
        ", readerAvatar=" + readerAvatar +
        ", readerIntroduction=" + readerIntroduction +
        ", createDate=" + getCreateDate() +
        ", updateDate=" + getUpdateDate() +
        "}";
    }
}
