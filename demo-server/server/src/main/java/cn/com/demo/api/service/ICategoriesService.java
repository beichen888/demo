package cn.com.demo.api.service;

import cn.com.demo.api.entity.Categories;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  频道类别服务类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
public interface ICategoriesService extends IService<Categories> {
    List<Categories> getCategories();
}
