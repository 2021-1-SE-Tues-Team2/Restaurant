package Hobe.Restaurant.Repository;


import Hobe.Restaurant.Domain.Review;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository {
    Review save(Review review);
    //Optional<Review> findById(Long id);
    List<Review> findAll();

}
