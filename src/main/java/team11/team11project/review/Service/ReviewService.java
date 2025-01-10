package team11.team11project.review.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import team11.team11project.review.dto.request.ReviewAddRequestDto;
import team11.team11project.review.dto.response.ReviewAddResponseDto;
import team11.team11project.review.dto.response.ReviewDto;

public interface ReviewService {

    ReviewAddResponseDto addReview(Long orderId, ReviewAddRequestDto dto, HttpServletRequest servletRequest);

    Page<ReviewDto> findByReviewsById(Long storeId, int minRating, int maxRating, Pageable pageable);
}
