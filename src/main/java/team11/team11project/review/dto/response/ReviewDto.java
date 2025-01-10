package team11.team11project.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReviewDto {

    private Long reviewId;
    private int rating;
    private String comment;
    private String memberName;
    private LocalDate createdAt;
}
