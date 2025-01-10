package team11.team11project.store.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import team11.team11project.common.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select count(s) from Store s where s.owner.id = :ownerId")
    int findStoresByMemberId(Long ownerId);

    @Query("select s from Store s WHERE s.name like %:name%")
    Page<Store> findStoresByName(String name, Pageable pageable);
}
