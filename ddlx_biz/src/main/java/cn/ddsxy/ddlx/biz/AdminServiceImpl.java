package cn.ddsxy.ddlx.biz;

import cn.ddsxy.ddlx.api.AdminService;
import com.ddsxy.ddlx.model.Admin;
import org.springframework.stereotype.Service;

@Service("adminService")
public class AdminServiceImpl implements AdminService {


    public Admin queryAdmin() {
        return new Admin();
    }
}
