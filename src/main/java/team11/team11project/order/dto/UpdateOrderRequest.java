package team11.team11project.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team11.team11project.common.enums.OrderStatus;

@Getter
@RequiredArgsConstructor
public class UpdateOrderRequest {

    // 속성
    @NotBlank(message = "주문 상태 변경은 필수입니다.")
    private final OrderStatus orderStatus;

    // 생성자

    // 기능
}
