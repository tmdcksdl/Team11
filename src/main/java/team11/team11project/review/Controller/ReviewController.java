package team11.team11project.review.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team11.team11project.common.aspect.AuthCheck;
import team11.team11project.review.Service.ReviewService;
import team11.team11project.review.dto.request.ReviewAddRequestDto;
import team11.team11project.review.dto.response.ReviewAddResponseDto;
import team11.team11project.review.dto.response.ReviewDto;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor

public class ReviewController {
    private final ReviewService reviewService;

    /**
     * 리뷰 작성
     * @param dto 별점, 리뷰 내용
     * @return
     */
    // FIX : DDL - UPDATE 설정, 엔티티 필드 삭제했는데 DB에서는 삭제가 안되는 이슈가 있었음. 그래서 콘솔로 직접
    @PostMapping("/orders/{orderId}/reviews")
    @AuthCheck("CUSTOMER")
    public ResponseEntity<ReviewAddResponseDto> addReview(@Valid @PathVariable Long orderId,
                                                          @RequestBody ReviewAddRequestDto dto,
                                                          HttpServletRequest servletRequest) {
        return new ResponseEntity<>(reviewService.addReview(orderId, dto, servletRequest), HttpStatus.CREATED);
    }

    /**
     * @return 가게 전체 리뷰 조회
     */
    @GetMapping("/stores/{storeId}/reviews")
    @AuthCheck({"OWNER", "CUSTOMER"})
    public ResponseEntity<Page<ReviewDto>> findReviewsByStore(
            @PathVariable Long storeId,
            @RequestParam(defaultValue = "1") int minRating,
            @RequestParam(defaultValue = "5") int maxRating,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ReviewDto> reviews = reviewService.findByReviewsById(storeId, minRating, maxRating, pageable);

        return ResponseEntity.ok(reviews);
    }
}
