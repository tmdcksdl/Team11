package team11.team11project.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import team11.team11project.common.entity.Review;
import team11.team11project.review.dto.response.ReviewDto;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select new team11.team11project.review.dto.response.ReviewDto(" +
            "r.id, r.rating, r.comment, r.customer.memberName, r.createdAt) " +
            "from Review  r " +
            "where r.store.id = :storeId " +
            "and r.rating between :minRating and :maxRating " +
            "order by r.createdAt desc ")
    Page<ReviewDto> findReviewByStoreIdAndRating(Long storeId, int minRating, int maxRating, Pageable pageable);
}
