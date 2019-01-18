package cn.com.demo.api.entity;

import cn.com.demo.common.exception.AppException;
import cn.com.demo.api.util.ProjectUtil;
import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vdurmont.emoji.EmojiParser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.StringUtils;

/**
 * <p>
 * 用户信息
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-08
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Users extends BaseEntity<Users> {
    @TableField("open_id")
    private String openId;
    @TableField("user_name")
    private String userName;
    @TableField("avatar_url")
    private String avatarUrl;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;
    /**
     * 昵称
     */
    private String nickname;

    private String email;

    private String phone;

    private EnumConstant role;

    @JsonIgnore
    public String getHashPassword() throws AppException {
        if (this.password != null && !this.password.isEmpty()) {
            return hashPassword(this.password);
        }
        return null;
    }

    public String hashPassword(String password) throws AppException {
        if (this.salt != null && !this.salt.isEmpty()) {
            return ProjectUtil.hashString(password, this.salt);
        }
        return null;
    }

    /**
     * 将表情转换成对应别名字符
     */
    public void parseEmojiToAliases() {
        if (StringUtils.isNotBlank(nickname)) {
            nickname = EmojiParser.parseToAliases(nickname);
        }
    }

    /**
     * 再次转换回表情
     */
    public void parseEmojiToUnicode() {
        if (StringUtils.isNotBlank(nickname)) {
            nickname = EmojiParser.parseToUnicode(nickname);
        }
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + getId() +
                ", userName=" + userName +
                ", nickname=" + nickname +
                ", email=" + email +
                ", phone=" + phone +
                ", role=" + role +
                ", createDate=" + getCreateDate() +
                ", updateDate=" + getUpdateDate() +
                "}";
    }
}
