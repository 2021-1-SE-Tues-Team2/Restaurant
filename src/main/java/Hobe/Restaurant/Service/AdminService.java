package Hobe.Restaurant.Service;

import Hobe.Restaurant.Domain.Admin;
import Hobe.Restaurant.Repository.AdminRepository;

public class AdminService {
    private final AdminRepository adminDB;
    private Admin Myadmin;
    public AdminService(AdminRepository adminDB) {
        this.adminDB = adminDB;
        this.Myadmin = new Admin();
    }
    public void adminInfo(Admin admin){
        Myadmin= adminDB.save(admin);
        Myadmin.setOK(true); //저장했으니 OK
        System.out.print(" ㅇㅋ ");
    }
    public Admin outputAdminInfo(){
        return adminDB.output();

    }
    public boolean isAdminInfo(){

        return adminDB.output()!= null?true:false;
    }
    public void UpdateAdminInfo(Admin admin){

        adminDB.UpdateInfo(admin);
    }
}
