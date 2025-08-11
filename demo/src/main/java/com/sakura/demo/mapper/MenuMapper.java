package com.sakura.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sakura.demo.domain.Menu;
import org.apache.ibatis.annotations.Select;
import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    @Select("SELECT t1.perms FROM sys_menu t1\n" +
            "INNER JOIN sys_role_menu t2 ON t2.`menu_id`=t1.`id`\n" +
            "INNER JOIN sys_role t3 ON t3.`id`=t2.`role_id`\n" +
            "INNER JOIN sys_user_role t4 ON t4.`role_id`=t3.`id`\n" +
            "INNER JOIN sys_user t5 ON t5.`id`=t4.`user_id`\n" +
            "WHERE t5.`id`=#{id}")
    List<String> getMenuByUserId(Long id);
}
