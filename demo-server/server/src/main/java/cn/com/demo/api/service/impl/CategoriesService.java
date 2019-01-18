package cn.com.demo.api.service.impl;

import cn.com.demo.api.entity.Categories;
import cn.com.demo.api.mapper.CategoriesMapper;
import cn.com.demo.api.service.ICategoriesService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 频道类别服务实现类
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class CategoriesService extends BaseServiceImpl<CategoriesMapper, Categories> implements ICategoriesService {
    @Resource
    private CategoriesMapper categoriesMapper;

    @Override
    public List<Categories> getCategories() {
        Wrapper<Categories> wrapper = new EntityWrapper<>();
        wrapper.orderBy("id", true);
        return categoriesMapper.selectList(wrapper);
    }
}
