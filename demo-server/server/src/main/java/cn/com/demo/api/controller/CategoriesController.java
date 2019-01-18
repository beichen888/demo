package cn.com.demo.api.controller;


import cn.com.demo.common.Result;
import cn.com.demo.api.entity.Categories;
import cn.com.demo.api.service.ICategoriesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 频道类别前端控制器
 * </p>
 *
 * @author zhiyou
 * @since 2019-01-09
 */
@RestController
@RequestMapping("/api/categories")
public class CategoriesController extends BaseController {

    @Resource
    private ICategoriesService categoriesService;

    @GetMapping
    public Result getAll() {
        List<Categories> categoriesList = categoriesService.getCategories();
        return renderSuccess(categoriesList);
    }
}

