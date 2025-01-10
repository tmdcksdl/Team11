package team11.team11project.menu.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team11.team11project.common.aspect.AuthCheck;
import team11.team11project.menu.model.dto.Dto;
import team11.team11project.menu.model.response.MenuResponse;
import team11.team11project.menu.service.MenuService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class MenuController {

    private final MenuService menuService;

    //메뉴 생성
    @PostMapping("/{storeId}/menus")
    @AuthCheck("OWNER")
    public ResponseEntity<MenuResponse> createMenu(@Valid @PathVariable Long storeId,
                                                   @RequestBody Dto menu,
                                                   HttpServletRequest servletRequest) {
        Long ownerId = (Long) servletRequest.getAttribute("memberId");

        MenuResponse menuResponse = menuService.createMenu(storeId, ownerId, menu.getName(), menu.getPrice(), menu.getDescription());

        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    //메뉴 수정
    @PatchMapping("/{storeId}/menus/{id}")
    @AuthCheck("OWNER")
    public ResponseEntity<MenuResponse> updateMenu(@Valid @PathVariable Long storeId,
                                                   @PathVariable Long id,
                                                   @RequestBody Dto menu,
                                                   HttpServletRequest servletRequest) {
        Long ownerId = (Long) servletRequest.getAttribute("memberId");
        MenuResponse menuResponse = menuService.updateMenu(storeId, ownerId, id, menu.getName(), menu.getPrice(), menu.getDescription());
        return new ResponseEntity<>(menuResponse, HttpStatus.OK);
    }

    //메뉴 삭제
    @DeleteMapping("/{storeId}/menus/{id}")
    @AuthCheck("OWNER")
    public ResponseEntity<MenuResponse> deleteMenu(@Valid @PathVariable Long storeId,
                                                   @PathVariable Long id,
                                                   HttpServletRequest servletRequest) {
        Long ownerId = (Long) servletRequest.getAttribute("memberId");
        menuService.deleteMenu(storeId, ownerId, id);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
