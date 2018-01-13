package cn.ddsxy.ddlx.biz;

import cn.ddsxy.ddlx.api.UserService;
import cn.ddsxy.ddlx.dao.UserMapper;
import cn.ddsxy.ddlx.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    //@RequiresRoles("hh")
    public User getId(Integer id){
        return userMapper.selectByPrimaryKey(id);
    }
}
