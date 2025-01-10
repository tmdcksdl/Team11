package team11.team11project.store.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team11.team11project.common.aspect.AuthCheck;
import team11.team11project.store.model.request.CreateStoreRequest;
import team11.team11project.store.model.request.UpdateStoreRequest;
import team11.team11project.store.model.response.OneStoreResponse;
import team11.team11project.store.model.response.StoreResponse;
import team11.team11project.store.service.StoreService;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    /**
     * 가게 생성
     * 권한 : OWNER
     * @return
     */
    @PostMapping
    @AuthCheck("OWNER")
    public ResponseEntity<StoreResponse> createStore(
            @Valid @RequestBody CreateStoreRequest storeRequest,
            HttpServletRequest request
    ) {

        StoreResponse storeResponse = storeService.createStore(storeRequest, request);

        return new ResponseEntity<>(storeResponse,HttpStatus.CREATED);
    }    

    // ::: 가게 조회(다건) API
    @GetMapping
    @AuthCheck({"OWNER", "CUSTOMER"})
    public ResponseEntity<Page<StoreResponse>> findAllStore(
            @PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam String storeName
    ) {

        Page<StoreResponse> allStore = storeService.findAllStore(pageable, storeName);

        return new ResponseEntity<>(allStore, HttpStatus.OK);
    }

    // ::: 가게 조회(단건) API - merge 후 메뉴 확인하면서 생성 예정
    @GetMapping("/{storeId}")
    @AuthCheck({"OWNER", "CUSTOMER"})
    public ResponseEntity<OneStoreResponse> findOneStore(
            @PathVariable Long storeId
    ) {

        OneStoreResponse oneStoreResponse = storeService.findOneStore(storeId);

        return new ResponseEntity<>(oneStoreResponse, HttpStatus.OK);

    }

    // ::: 가게 수정 API
    @PutMapping("/{storeId}")
    @AuthCheck("OWNER")
    public ResponseEntity<StoreResponse> updateStore(
            @Valid @PathVariable Long storeId,
            @RequestBody UpdateStoreRequest storeRequest,
            HttpServletRequest request
    ) {

        StoreResponse storeResponse = storeService.updateStore(storeId, storeRequest, request);

        return new ResponseEntity<>(storeResponse, HttpStatus.OK);
    }

    // ::: 가게 페업 API
    @DeleteMapping("/{storeId}")
    @AuthCheck("OWNER")
    public ResponseEntity<Void> deleteStore(
            @Valid @PathVariable Long storeId,
            HttpServletRequest request
    ) {

        storeService.deleteStore(storeId, request);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
