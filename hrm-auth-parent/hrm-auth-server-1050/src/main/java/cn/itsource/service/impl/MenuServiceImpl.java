package cn.itsource.service.impl;

import cn.itsource.domain.Menu;
import cn.itsource.mapper.MenuMapper;
import cn.itsource.service.IMenuService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @desc desc 菜单表
 * @author tangrufei
 * @date 2022/8/31 13:17
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
