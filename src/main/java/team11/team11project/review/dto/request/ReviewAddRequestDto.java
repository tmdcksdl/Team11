package team11.team11project.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewAddRequestDto {

    @NotBlank(message = "별점 입력은 필수 입니다.")
    @Size(min = 1, max = 5, message = "별점은 최소 1점 이상, 최대 5점 이하여야합니다.")
    private int rating;

    @NotBlank(message = "리뷰 입력은 필수 입니다.")
    @Size(min = 1, max = 100, message = "리뷰 내용은 1자 이상, 100자 이하여야합니다.")
    private String comment;
    //추가
    @NotBlank
    private Long customerId;

}
