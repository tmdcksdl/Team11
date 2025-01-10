package team11.team11project.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team11.team11project.common.aspect.AuthCheck;
import team11.team11project.order.dto.CreateOrderRequest;
import team11.team11project.order.dto.CreateOrderResponse;
import team11.team11project.order.dto.UpdateOrderRequest;
import team11.team11project.order.dto.UpdateOrderResponse;
import team11.team11project.order.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    // 속성
    private final OrderService orderService;

    // 생성자

    // 기능
    // ::: 주문 생성 API
    @PostMapping
    @AuthCheck("CUSTOMER")
    public ResponseEntity<CreateOrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {

        CreateOrderResponse orderResponse = orderService.save(request);

        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

    // ::: 주문 상태 변경 API
    @PatchMapping("/{orderId}")
    @AuthCheck("OWNER")
    public ResponseEntity<UpdateOrderResponse> updateOrderStatus(
            @Valid @PathVariable Long orderId,
            @RequestBody UpdateOrderRequest request
    ) {

        UpdateOrderResponse updateOrderResponse = orderService.updateOrderStatus(orderId, request);

        return new ResponseEntity<>(updateOrderResponse, HttpStatus.OK);
    }
}
