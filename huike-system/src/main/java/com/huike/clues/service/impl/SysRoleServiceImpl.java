package com.huike.clues.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysRole;
import org.springframework.stereotype.Service;
import com.huike.common.core.domain.entity.SysRoleDTO;
import com.huike.common.utils.StringUtils;
import com.huike.common.utils.spring.SpringUtils;
import com.huike.clues.mapper.SysRoleMapper;
import com.huike.clues.service.ISysRoleService;

import javax.annotation.Resource;

/**
 * 角色 业务层处理
 * 
 * 
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService
{
    @Resource
    private SysRoleMapper roleMapper;


    /**
     * 查询所有角色
     *
     * @return 角色列表
     */
    @Override
    public List<SysRoleDTO> selectRoleAll()
    {
        return SpringUtils.getAopProxy(this).selectRoleList(new SysRoleDTO());
    }

    /**
     * 根据条件分页查询角色数据
     *
     * @param role 角色信息
     * @return 角色数据集合信息
     */
    @Override
    public List<SysRoleDTO> selectRoleList(SysRoleDTO role)
    {
        return roleMapper.selectRoleList(role);
    }

    /**
     * 根据用户ID获取角色选择框列表
     *
     * @param userId 用户ID
     * @return 选中角色ID列表
     */
    @Override
    public List<Integer> selectRoleListByUserId(Long userId)
    {
        return roleMapper.selectRoleListByUserId(userId);
    }
    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectRolePermissionByUserId(Long userId)
    {
        List<SysRoleDTO> perms = roleMapper.selectRolePermissionByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (SysRoleDTO perm : perms)
        {
            if (StringUtils.isNotNull(perm))
            {
                permsSet.addAll(Arrays.asList(perm.getRoleKey().trim().split(",")));
            }
        }
        return permsSet;
    }
}
