package Hobe.Restaurant.Controller;

import Hobe.Restaurant.Domain.Admin;
import Hobe.Restaurant.Domain.Table;
import Hobe.Restaurant.Service.AdminService;
import Hobe.Restaurant.Service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

@Controller
public class AdminControl {

    private final AdminService adminService;
    private final TableService tableService;

    @Autowired
    public AdminControl(AdminService adminService, TableService tableService) {
        this.adminService = adminService;
        this.tableService = tableService;
    }
    @GetMapping("/admin")
    public String HelloAdmin(){
        System.out.println("dz");
        return "LoginAfter/admin";
    }
    @PostMapping("/admin/adminForm")
    public String adminFormHandler(AdminForm adminForm, Model model){
        int id = 1;
        Admin admin = new Admin();
        admin.setAddress(adminForm.getAddress());
        admin.setStartTime(adminForm.getStartTime());
        admin.setEndTime(adminForm.getEndTime());
        admin.setEmail(adminForm.getEmail());
        admin.setPhoneNumber(adminForm.getPhoneNum());
        admin.setId(id);
        adminService.adminInfo(admin);
        return "redirect:/";
    }
    @PostMapping("/admin/adminForm_update")
    public String adminFormUpdate(AdminForm adminForm,Model model){
//        변경하는거니까 일단 기존에 데이터베이스에 있는 가게 정보를 삭제해야됌.
        Admin admin = new Admin();
        admin.setAddress(adminForm.getAddress());
        admin.setStartTime(adminForm.getStartTime());
        admin.setEndTime(adminForm.getEndTime());
        admin.setEmail(adminForm.getEmail());
        admin.setPhoneNumber(adminForm.getPhoneNum());
        admin.setId(1);
        adminService.UpdateAdminInfo(admin);
        return "redirect:/";
    }
    @PostMapping("/admin/TableForm_insert")
    public String TableForm_insert(MultiTableForm tableFormList, Count_Form totalPorm, Model model){
        for(TableForm t : tableFormList.getTableFormList()){
            Table newTable = new Table();
            newTable.setTableNumber(Integer.valueOf(t.getTableNumber()));
            newTable.setMaxNumber(Integer.valueOf(t.getTableMaxNumber()));
            newTable.setAvailable(true);
            tableService.saveTable(newTable);
        }


        return "redirect:/";
    }

}
