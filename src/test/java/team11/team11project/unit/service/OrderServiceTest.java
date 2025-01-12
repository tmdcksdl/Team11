package team11.team11project.unit.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import team11.team11project.menu.repository.MenuRepository;
import team11.team11project.order.repository.OrderRepository;
import team11.team11project.order.service.OrderService;
import team11.team11project.user.repository.MemberRepository;

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


        // when


        // then

    }

    @Test
    void 주문_상태_변경_성공_테스트() {
        // given


        // when


        // then
    }
}
