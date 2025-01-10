package team11.team11project.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team11.team11project.common.entity.Member;
import team11.team11project.common.entity.Menu;
import team11.team11project.common.entity.Orders;
import team11.team11project.common.entity.Store;
import team11.team11project.common.enums.OrderStatus;
import team11.team11project.common.enums.UserRole;
import team11.team11project.menu.repository.MenuRepository;
import team11.team11project.order.dto.CreateOrderRequest;
import team11.team11project.order.dto.CreateOrderResponse;
import team11.team11project.order.repository.OrderRepository;
import team11.team11project.order.service.OrderService;
import team11.team11project.user.repository.MemberRepository;

import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;

    @Mock
    OrderRepository orderRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    MenuRepository menuRepository;

    @Test
    void 주문_생성_성공_테스트() {
        // given
        Long customerId = 1L;
        Long ownerId = 2L;
        Long menuId = 1L;
        Long storeId = 1L;
        int quantity = 3;

        Member customer = new Member(customerId, "고객", "customer@gmail.com", "password", UserRole.CUSTOMER, false);
        Member owner = new Member(ownerId, "사장님", "owner@gmail.com", "password", UserRole.OWNER, false);

        Menu menu = new Menu(menuId, "치킨", 15000, "후라이드 치킨입니다.", new Store(storeId, "치킨가게", owner, 5000, LocalTime.of(9, 0), LocalTime.of(21, 0),false), owner.getId(),false);

        when(memberRepository.findById(customerId)).thenReturn(Optional.of(customer));
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(orderRepository.save(any(Orders.class))).thenAnswer(invocation -> {
            Orders order = invocation.getArgument(0);
            // 생성자를 사용해 새로운 Orders 객체 생성
            return new Orders(1L, order.getCustomer(), order.getMenu(), order.getOrderStatus(),order.getQuantity());
        });

        CreateOrderRequest request = new CreateOrderRequest(quantity, customer.getId(), menu.getId());

        // when
        CreateOrderResponse response = orderService.save(request);

        // then
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getOrderStatus()).isEqualTo(OrderStatus.REQUESTED);
        assertThat(response.getQuantity()).isEqualTo(quantity);
        assertThat(response.getCustomer().getCustomerName()).isEqualTo("고객");
    }

    @Test
    void 주문_상태_변경_성공_테스트() {
        // given


        // when


        // then
    }
}
