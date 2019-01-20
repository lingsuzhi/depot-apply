package com.lsz.depot.apply.service;

import com.lsz.depot.apply.dao.MenuDao;
import com.lsz.depot.core.dto.LayuiNavbarBO;
import com.lsz.depot.core.enums.MenuTypeEnum;
import com.lsz.depot.apply.po.MenuInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class MenuService {

    @Autowired
    private MenuDao menuDao;

    public List<LayuiNavbarBO> menuTree() {
        List<MenuInfo> menuInfos = menuDao.findByStatusTrue();
        if (CollectionUtils.isEmpty(menuInfos)) {
            return null;
        }
        List<LayuiNavbarBO> list = getMenuParentNodes(menuInfos);

        return list;
    }

    private List<LayuiNavbarBO> getMenuParentNodes(List<MenuInfo> list) {
        if (list == null) return null;
        List<LayuiNavbarBO> parentNodes = new ArrayList<>();
        for (MenuInfo menu : list) {
            if (StringUtils.isEmpty(menu.getParentId()) && !MenuTypeEnum.BUTTON.name().equals(menu.getType())) {
                LayuiNavbarBO parentNode = new LayuiNavbarBO();

                parentNode.setId(menu.getMenuId());
                parentNode.setIcon(menu.getIcon());
                parentNode.setSort(menu.getSort());
                parentNode.setSpread(false);
                parentNode.setTitle(menu.getMenuName());
                parentNode.setUrl(menu.getMenuUrl());

                setChildNode(parentNode, list);
                parentNodes.add(parentNode);
            }
        }
        if (!parentNodes.isEmpty()) {
            sortMenuNode(parentNodes);
        }
        return parentNodes;
    }

    /* node表示本节点，list 代表所有菜单 */
    private void setChildNode(LayuiNavbarBO node, List<MenuInfo> list) {
        List<LayuiNavbarBO> childNode = new ArrayList<>();
        for (MenuInfo m : list) {
            if (node.getId().equals(m.getParentId())) {
                LayuiNavbarBO n = new LayuiNavbarBO();
                n.setId(m.getMenuId());
                n.setIcon(m.getIcon());
                n.setSort(m.getSort());
                n.setSpread(false);
                n.setTitle(m.getMenuName());
                n.setUrl(m.getMenuUrl());

                setChildNode(n, list);
                childNode.add(n);
            }
        }
        if (!childNode.isEmpty()) {
            sortMenuNode(childNode);
        }
        node.setChildren(childNode);
    }

    private void sortMenuNode(List<LayuiNavbarBO> list) {
        Collections.sort(list, new Comparator<LayuiNavbarBO>() {
            public int compare(LayuiNavbarBO n1, LayuiNavbarBO n2) {
                int sort1 = n1.getSort();
                int sort2 = n2.getSort();
                if (sort1 > sort2) {
                    return 1;
                } else if (sort1 == sort2) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
    }
}
