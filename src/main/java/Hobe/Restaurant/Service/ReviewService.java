package Hobe.Restaurant.Service;

import Hobe.Restaurant.Domain.Member;
import Hobe.Restaurant.Domain.Review;
import Hobe.Restaurant.Repository.MemberRepository;
import Hobe.Restaurant.Repository.ReviewRepository;

import java.util.List;
import java.util.Optional;

public class ReviewService {
    private final ReviewRepository reviewDB;
    private final MemberRepository memberDB;

    public ReviewService(ReviewRepository reviewRepository ,MemberRepository memberRepository){
        reviewDB = reviewRepository;
        memberDB = memberRepository;
    }

    public boolean write(Review review){
        Review newReview = reviewDB.save(review);
        return true;

    }

    public List<Review> getAll(){
        return reviewDB.findAll();
    }

    public List<Review> recent10Review(){
        return reviewDB.findAll();
    }

//    public String writerNames(long id){
//        Optional<Member> writer = memberDB.findById(id);
//        if (Member.isPresent()) {
//
//        } else {
//
//        }
//        return writer.getName();
//    }


}
