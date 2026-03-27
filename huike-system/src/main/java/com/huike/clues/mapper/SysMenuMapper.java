package com.huike.clues.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.huike.clues.domain.SysMenu;
import com.huike.common.core.domain.entity.SysMenuDTO;

/**
 * 菜单表 数据层
 *
 * 
 */
public interface SysMenuMapper extends BaseMapper<SysMenu>
{
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    public List<String> selectMenuPermsByUserId(Long userId);

    /**
     * 根据用户ID查询菜单
     *
     * @return 菜单列表
     */
    public List<SysMenuDTO> selectMenuTreeAll();

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户ID
     * @return 菜单列表
     */
    public List<SysMenuDTO> selectMenuTreeByUserId(Long userId);
}
