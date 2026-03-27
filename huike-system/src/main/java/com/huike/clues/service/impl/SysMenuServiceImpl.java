package com.huike.clues.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.huike.clues.domain.SysMenu;
import org.springframework.stereotype.Service;
import com.huike.common.constant.UserConstants;
import com.huike.common.core.domain.entity.SysMenuDTO;
import com.huike.common.utils.SecurityUtils;
import com.huike.common.utils.StringUtils;
import com.huike.clues.domain.vo.MetaVo;
import com.huike.clues.domain.vo.RouterVo;
import com.huike.clues.mapper.SysMenuMapper;
import com.huike.clues.service.ISysMenuService;

import javax.annotation.Resource;

/**
 * 菜单 业务层处理
 *
 *
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements ISysMenuService
{
    public static final String PREMISSION_STRING = "perms[\"{0}\"]";

    @Resource
    private SysMenuMapper menuMapper;



    /**
     * 根据用户ID查询权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    @Override
    public Set<String> selectMenuPermsByUserId(Long userId)
    {
        List<String> perms = menuMapper.selectMenuPermsByUserId(userId);
        Set<String> permsSet = new HashSet<>();
        for (String perm : perms)
        {
            if (StringUtils.isNotEmpty(perm))
            {
                permsSet.addAll(Arrays.asList(perm.trim().split(",")));
            }
        }
        return permsSet;
    }

    /**
     * 根据用户ID查询菜单
     *
     * @param userId 用户名称
     * @return 菜单列表
     */
    @Override
    public List<SysMenuDTO> selectMenuTreeByUserId(Long userId)
    {
        List<SysMenuDTO> menus = null;
        if (SecurityUtils.isAdmin(userId))
        {
            menus = menuMapper.selectMenuTreeAll();
        }
        else
        {
            menus = menuMapper.selectMenuTreeByUserId(userId);
        }
        return getChildPerms(menus, 0);
    }



    /**
     * 构建前端路由所需要的菜单
     *
     * @param menus 菜单列表
     * @return 路由列表
     */
    @Override
    public List<RouterVo> buildMenus(List<SysMenuDTO> menus)
    {
        List<RouterVo> routers = new LinkedList<RouterVo>();
        for (SysMenuDTO menu : menus)
        {
            RouterVo router = new RouterVo();
            router.setHidden("1".equals(menu.getVisible()));
            router.setName(getRouteName(menu));
            router.setPath(getRouterPath(menu));
            router.setComponent(getComponent(menu));
            router.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
            List<SysMenuDTO> cMenus = menu.getChildren();
            if (!cMenus.isEmpty() && cMenus.size() > 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType()))
            {
                router.setAlwaysShow(true);
                router.setRedirect("noRedirect");
                router.setChildren(buildMenus(cMenus));
            }
            else if (isMeunFrame(menu))
            {
                List<RouterVo> childrenList = new ArrayList<RouterVo>();
                RouterVo children = new RouterVo();
                children.setPath(menu.getPath());
                children.setComponent(menu.getComponent());
                children.setName(StringUtils.capitalize(menu.getPath()));
                children.setMeta(new MetaVo(menu.getMenuName(), menu.getIcon(), StringUtils.equals("1", menu.getIsCache())));
                childrenList.add(children);
                router.setChildren(childrenList);
            }
            routers.add(router);
        }
        return routers;
    }










    /**
     * 获取路由名称
     *
     * @param menu 菜单信息
     * @return 路由名称
     */
    public String getRouteName(SysMenuDTO menu)
    {
        String routerName = StringUtils.capitalize(menu.getPath());
        // 非外链并且是一级目录（类型为目录）
        if (isMeunFrame(menu))
        {
            routerName = StringUtils.EMPTY;
        }
        return routerName;
    }

    /**
     * 获取路由地址
     *
     * @param menu 菜单信息
     * @return 路由地址
     */
    public String getRouterPath(SysMenuDTO menu)
    {
        String routerPath = menu.getPath();
        // 非外链并且是一级目录（类型为目录）
        if (0 == menu.getParentId().intValue() && UserConstants.TYPE_DIR.equals(menu.getMenuType())
                && UserConstants.NO_FRAME.equals(menu.getIsFrame()))
        {
            routerPath = "/" + menu.getPath();
        }
        // 非外链并且是一级目录（类型为菜单）
        else if (isMeunFrame(menu))
        {
            routerPath = "/";
        }
        return routerPath;
    }

    /**
     * 获取组件信息
     *
     * @param menu 菜单信息
     * @return 组件信息
     */
    public String getComponent(SysMenuDTO menu)
    {
        String component = UserConstants.LAYOUT;
        if (StringUtils.isNotEmpty(menu.getComponent()) && !isMeunFrame(menu))
        {
            component = menu.getComponent();
        }
        else if (StringUtils.isEmpty(menu.getComponent()) && isParentView(menu))
        {
            component = UserConstants.PARENT_VIEW;
        }
        return component;
    }

    /**
     * 是否为菜单内部跳转
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isMeunFrame(SysMenuDTO menu)
    {
        return menu.getParentId().intValue() == 0 && UserConstants.TYPE_MENU.equals(menu.getMenuType())
                && menu.getIsFrame().equals(UserConstants.NO_FRAME);
    }

    /**
     * 是否为parent_view组件
     *
     * @param menu 菜单信息
     * @return 结果
     */
    public boolean isParentView(SysMenuDTO menu)
    {
        return menu.getParentId().intValue() != 0 && UserConstants.TYPE_DIR.equals(menu.getMenuType());
    }

    /**
     * 递归列表
     *
     * @param list
     * @param t
     */
    private void recursionFn(List<SysMenuDTO> list, SysMenuDTO t)
    {
        // 得到子节点列表
        List<SysMenuDTO> childList = getChildList(list, t);
        t.setChildren(childList);
        for (SysMenuDTO tChild : childList)
        {
            if (hasChild(list, tChild))
            {
                recursionFn(list, tChild);
            }
        }
    }
    /**
     * 根据父节点的ID获取所有子节点
     *
     * @param list 分类表
     * @param parentId 传入的父节点ID
     * @return String
     */
    public List<SysMenuDTO> getChildPerms(List<SysMenuDTO> list, int parentId)
    {
        List<SysMenuDTO> returnList = new ArrayList<SysMenuDTO>();
        for (Iterator<SysMenuDTO> iterator = list.iterator(); iterator.hasNext();)
        {
            SysMenuDTO t = (SysMenuDTO) iterator.next();
            // 一、根据传入的某个父节点ID,遍历该父节点的所有子节点
            if (t.getParentId() == parentId)
            {
                recursionFn(list, t);
                returnList.add(t);
            }
        }
        return returnList;
    }



    /**
     * 得到子节点列表
     */
    private List<SysMenuDTO> getChildList(List<SysMenuDTO> list, SysMenuDTO t)
    {
        List<SysMenuDTO> tlist = new ArrayList<SysMenuDTO>();
        Iterator<SysMenuDTO> it = list.iterator();
        while (it.hasNext())
        {
            SysMenuDTO n = (SysMenuDTO) it.next();
            if (n.getParentId().longValue() == t.getMenuId().longValue())
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<SysMenuDTO> list, SysMenuDTO t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }
}
