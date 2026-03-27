package com.huike.clues.service;

import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.huike.clues.domain.SysMenu;
import com.huike.common.core.domain.entity.SysMenuDTO;
import com.huike.clues.domain.vo.RouterVo;

/**
 * 菜单 业务层
 *
 *
 */
public interface ISysMenuService extends IService<SysMenu>
{




    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public Set<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单树信息
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenuDTO> selectMenuTreeByUserId(Long userId);



    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    public List<RouterVo> buildMenus(List<SysMenuDTO> menus);


}
