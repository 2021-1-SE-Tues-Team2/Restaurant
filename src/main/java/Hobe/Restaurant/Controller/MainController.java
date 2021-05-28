package Hobe.Restaurant.Controller;

import Hobe.Restaurant.Service.BookingService;
import Hobe.Restaurant.Service.MemberService;
import Hobe.Restaurant.Service.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final MemberService memberService;
    private final TableService tableService;
    private final BookingService bookingService;

    @Autowired
    public MainController(MemberService memberService, TableService tableService, BookingService bookingService) {
        this.memberService = memberService;
        this.tableService = tableService;
        this.bookingService = bookingService;
    }


    @GetMapping("/introduction")
    public String Introduction() {
        return "Introduction";
    }
    @GetMapping("/LoginAfter/LoginAfterMainPage")
    public String LoginAfterMainPage(Model model){
        model.addAttribute("data",LoginControl.currentMember.getName());
        return "LoginAfter/LoginAfterMainPage";                           //관리자가 세팅할 때. 이 페이지로 데이터 넘겨야됌.
    }
    @GetMapping("/LoginAfter/LoginAfterIntroduction")
    public String LoginAfterIntroduction(Model model){
        model.addAttribute("Member",LoginControl.currentMember.getName());
        return "LoginAfter/LoginAfterIntroduction";
    }
}

