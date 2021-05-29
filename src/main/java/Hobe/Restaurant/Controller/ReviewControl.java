package Hobe.Restaurant.Controller;


import Hobe.Restaurant.Domain.Review;
import Hobe.Restaurant.Service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

@Controller
public class ReviewControl {
    private final ReviewService reviewService;

    @Autowired
    public ReviewControl(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("/Review")
    public String ReviewPage(Model model){
        List<Review> reviewList = reviewService.getAll();
        model.addAttribute("reviewList",reviewList);
        model.addAttribute("reviewText",reviewList.get(0).getReviewText());
        System.out.println("보여줄 후기 목록");
        Collections.reverse(reviewList);
        if(reviewList.size() > 10)
            reviewList = reviewList.subList(0,9);
        for(Review Rtext: reviewList){
            System.out.println(Rtext.getReviewText());
        }
        model.addAttribute("reviewList",reviewList);
        return "ReviewPage";
    }

    @PostMapping("/New_Review")
    public String insertReview(ReviewForm reviewForm, Model model){
        Review review = new Review();
        String text = reviewForm.getReviewText();
        System.out.println("새로운 후기: " + text);
        review.setReviewText(text);
        try{
            review.setMemberId(LoginControl.currentMember.getId());
            review.setMemberName(LoginControl.currentMember.getName());
            System.out.println("후기를 등록한 사용자 이름: " + LoginControl.currentMember.getName());
        }
        catch(NullPointerException e){
            review.setMemberId(0);
            review.setMemberName("익명");
            System.out.println("미등록 사용자로 후기를 작성함");
        }
        reviewService.write(review);
        return "signReview";
    }


}
