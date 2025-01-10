package team11.team11project.order.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team11.team11project.common.entity.Member;
import team11.team11project.common.entity.Menu;
import team11.team11project.common.entity.Orders;
import team11.team11project.common.enums.OrderStatus;
import team11.team11project.common.exception.InvalidOrderStatusChangeException;
import team11.team11project.common.exception.MinimumOrderAmountException;
import team11.team11project.common.exception.NotFoundException;
import team11.team11project.common.exception.OutOfOperatingHoursException;
import team11.team11project.menu.repository.MenuRepository;
import team11.team11project.order.dto.CreateOrderRequest;
import team11.team11project.order.dto.CreateOrderResponse;
import team11.team11project.order.dto.UpdateOrderRequest;
import team11.team11project.order.dto.UpdateOrderResponse;
import team11.team11project.order.repository.OrderRepository;
import team11.team11project.user.model.response.OrderMemberResponse;
import team11.team11project.user.repository.MemberRepository;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    // 속성
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;

    // 생성자

    // 기능
    // ::: 주문 생성 서비스
    public CreateOrderResponse save(CreateOrderRequest request) {

        Member customer = memberRepository.findById(request.getCustomer_id()).orElseThrow(() ->
                new NotFoundException("해당 id의 멤버가 존재하지 않습니다."));

        Menu menu = menuRepository.findById(request.getMenu_id()).orElseThrow(() ->
                new NotFoundException("해당 id의 메뉴가 존재하지 않습니다."));

        LocalTime now = LocalTime.now();
        // 가게 운영 시간이 아닐 때 주문을 하는 경우 예외 처리
        if (now.isBefore(menu.getStore().getOpenTime()) || now.isAfter(menu.getStore().getCloseTime())) {
            throw new OutOfOperatingHoursException("가게 운영 시간이 아닙니다. 가게 운영 시간일 때 주문해주세요.");
        }

        int totalPrice = menu.getPrice() * request.getQuantity();
        // 최소 주문 금액이 넘지 않는 경우 예외 처리
        if (totalPrice < menu.getStore().getMinOrderPrice()) {
            throw new MinimumOrderAmountException("최소 주문 금액 이상 주문해야 접수 가능합니다.");
        }

        Orders order = new Orders(customer, menu, request.getQuantity());
        Orders savedOrder = orderRepository.save(order);

        OrderMemberResponse orderMemberResponse = new OrderMemberResponse(savedOrder.getCustomer().getMemberName());

        return new CreateOrderResponse(
                savedOrder.getId(),
                savedOrder.getOrderStatus(),
                savedOrder.getQuantity(),
                orderMemberResponse
        );

    }

    // ::: 주문 상태 변경 서비스
    @Transactional
    public UpdateOrderResponse updateOrderStatus(Long orderId, UpdateOrderRequest request) {

        Orders foundOrder = orderRepository.findById(orderId).orElseThrow(() ->
                new NotFoundException("해당 id의 주문이 존재하지 않습니다."));

        // 배달 완료, 취소된 주문에 대해 상태 변경을 요청하는 경우 예외 처리
        if (foundOrder.getOrderStatus() == OrderStatus.COMPLETED || foundOrder.getOrderStatus() == OrderStatus.CANCELED) {
            throw new InvalidOrderStatusChangeException("주문 상태를 변경할 수 없습니다.");
        }

        // 주문 수락, 배달중인 상태에서 주문 취소 상태로 변경할 경우 예외 처리, ACCPETED, DELIVERY 중에서는 CANCLED로 못감
        if (foundOrder.getOrderStatus() == OrderStatus.ACCEPTED || foundOrder.getOrderStatus() == OrderStatus.DELIVERY) {
            if (request.getOrderStatus() == OrderStatus.CANCELED) {
                throw new InvalidOrderStatusChangeException("주문을 취소할 수 없습니다.");
            }
        }

        // 주문 상태를 전 단계로 되돌아갈 경우 예외 처리
        if (foundOrder.getOrderStatus().ordinal() > request.getOrderStatus().ordinal()) {
            throw new InvalidOrderStatusChangeException("주문 상태를 전 단계로 되돌아갈 수 없습니다.");
        }

        foundOrder.UpdateOrderStatus(request.getOrderStatus());

        return new UpdateOrderResponse(foundOrder.getId(), foundOrder.getOrderStatus());
    }
}
