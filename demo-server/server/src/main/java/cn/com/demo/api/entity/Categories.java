package cn.com.demo.api.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 频道类别
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class Categories extends BaseEntity<Categories> {
    private String name;
    @TableField("image_url")
    private String imageUrl;

    @Override
    public String toString() {
        return "Categories{" +
                "id=" + getId() +
                ", name=" + name +
                ", imageUrl=" + imageUrl +
                "}";
    }
}
