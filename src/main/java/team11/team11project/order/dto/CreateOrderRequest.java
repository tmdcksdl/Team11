package team11.team11project.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderRequest {

    // 속성
    @NotBlank(message = "수량 입력은 필수 입니다.")
    @Size(min = 1, message = "수량은 1개 이상이여야합니다.")
    private final int quantity;

    @NotBlank
    private final Long customer_id;
    @NotBlank
    private final Long menu_id;

    // 생성자

    // 기능
}
