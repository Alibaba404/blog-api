package cn.aikuiba.system.auth;

import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by 蛮小满Sama at 2023/11/25 16:06
 *
 * @description
 */
@Component
public class PermissionScanRunner implements CommandLineRunner {

    @Autowired
    private PermissionScan permissionScan;

    /**
     * Callback used to run the bean.
     *
     * @param args incoming main method arguments
     */
    @Override
    public void run(String... args) {
        new Thread(() -> permissionScan.scanPermission()).start();
    }
}
