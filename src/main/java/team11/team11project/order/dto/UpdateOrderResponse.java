package team11.team11project.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team11.team11project.common.enums.OrderStatus;

@Getter
@RequiredArgsConstructor
public class UpdateOrderResponse {

    // 속성
    private final Long id;
    private final OrderStatus orderStatus;

    // 생성자

    // 기능
}
