package Hobe.Restaurant.Domain;

import java.sql.Time;
import java.sql.Timestamp;

public class Review {
    String reviewText;
    long memberId;
    Timestamp createdAt;

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    String memberName;

    public String getReviewText(){return reviewText;}

    public void setReviewText(String reviewText)
    {
        this.reviewText = reviewText;
    }

    public long getMemberId()
    {
        return memberId;
    }

    public void setMemberId(long memberId){
        this.memberId = memberId;
    }

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt)
    {
        this.createdAt = createdAt;
    }

}
