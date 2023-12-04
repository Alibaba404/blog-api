package cn.aikuiba.system.controller;

import cn.aikuiba.exception.BisException;
import cn.aikuiba.resp.R;
import cn.aikuiba.system.dto.LoginDto;
import cn.aikuiba.system.service.ILogininfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by 蛮小满Sama at 2023/11/28 15:35
 *
 * @description
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private ILogininfoService logininfoService;

    @PostMapping("/account")
    public R<String> accountLogin(@RequestBody LoginDto loginDTO) {
        try {
            return R.success(logininfoService.accountLogin(loginDTO));
        } catch (BisException e) {
            return R.failure(2003, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return R.failure(20001, "服务器繁忙,请稍后再试!");
        }

    }
}
