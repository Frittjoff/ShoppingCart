package com.example.ShoppingCart;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AdminRepository extends CrudRepository<Admin, Long> {

    @Query(value = "SELECT * FROM ADMIN WHERE username=?1 AND password=?2",nativeQuery = true)
    Admin getAdmin(String username, String password);
}
