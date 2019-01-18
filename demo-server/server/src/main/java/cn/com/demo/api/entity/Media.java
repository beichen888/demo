package cn.com.demo.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 媒体信息
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Media extends BaseEntity<Media> {

    /**
     * 频道id
     */
    @NotNull
    @TableField("channel_id")
    private Long channelId;
    /**
     * 媒体名字
     */
    @NotBlank
    private String title;
    /**
     * 媒体url
     */
    @NotBlank
    private String url;

    /**
     * 排序
     */
    private int sortNo;


    @Override
    public String toString() {
        return "Media{" +
                "id=" + getId() +
                ", channelId=" + channelId +
                ", title=" + title +
                ", url=" + url +
                ", sortNo=" + sortNo +
                ", createDate=" + getCreateDate() +
                ", updateDate=" + getUpdateDate() +
                "}";
    }
}
