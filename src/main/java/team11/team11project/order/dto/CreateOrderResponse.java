package team11.team11project.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import team11.team11project.common.enums.OrderStatus;
import team11.team11project.user.model.response.OrderMemberResponse;

@Getter
@RequiredArgsConstructor
public class CreateOrderResponse {

    // 속성
    private final Long id;
    private final OrderStatus orderStatus;
    private final int quantity;
    private final OrderMemberResponse customer;

    // 생성자

    // 기능
}
