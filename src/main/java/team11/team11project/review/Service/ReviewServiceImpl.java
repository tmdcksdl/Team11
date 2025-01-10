package team11.team11project.review.Service;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import team11.team11project.common.entity.Member;
import team11.team11project.common.entity.Orders;
import team11.team11project.common.entity.Review;
import team11.team11project.common.entity.Store;
import team11.team11project.common.enums.OrderStatus;
import team11.team11project.common.exception.NotFoundException;
import team11.team11project.common.exception.ReviewNotAllowedException;
import team11.team11project.common.exception.UnauthorizedAccessException;
import team11.team11project.order.repository.OrderRepository;
import team11.team11project.review.dto.request.ReviewAddRequestDto;
import team11.team11project.review.dto.response.ReviewAddResponseDto;
import team11.team11project.review.dto.response.ReviewDto;
import team11.team11project.review.repository.ReviewRepository;
import team11.team11project.store.repository.StoreRepository;
import team11.team11project.user.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;

    @Override
    public ReviewAddResponseDto addReview(Long orderId, ReviewAddRequestDto dto, HttpServletRequest servletRequest) {

        Long memberId = (Long) servletRequest.getAttribute("memberId");

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("찾는 주문이 없습니다."));

        validateOrder(order, memberId);

        Store store = storeRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("찾는 가게가 없습니다."));
        Member customer = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("찾는 고객이 없습니다."));

        Review review = Review.createReview(dto, store ,order, customer);
        reviewRepository.save(review);

        return ReviewAddResponseDto.toDto(review);
    }

    // FIX : GetReviewTime를 없애고 getCreateAt으로 교체
    // TODO : 가게 정보를 다건으 조회는 하는데 리뷰를 별점 범위에 따라 조회. 따로 봐야하는건지 같이 봐야하는건지 잘 모르겠음.
    @Override
    public Page<ReviewDto> findByReviewsById(Long storeId, int minRating, int maxRating, Pageable pageable) {
        Page<ReviewDto> reviewByStoreIdAndRating
                = reviewRepository.findReviewByStoreIdAndRating(storeId, minRating, maxRating, pageable);
        return reviewByStoreIdAndRating;
    }

    /**
     * 주문 검증, 권한 확인 검증을 메서드로 별도 분리
     * @param order
     * @param memberId
     */
    private static void validateOrder(Orders order, Long memberId) {
        if (order.getOrderStatus() != OrderStatus.COMPLETED) {
            throw new ReviewNotAllowedException("배달 완료 되지 않은 주문은 리뷰를 작성할 수 없습니다.");
        }

        if (!order.getCustomer().getId().equals(memberId)) {
            throw new UnauthorizedAccessException("이 주문에 대한 권한이 없습니다.");
        }
    }
}
