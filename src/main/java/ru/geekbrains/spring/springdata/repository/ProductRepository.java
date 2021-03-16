package ru.geekbrains.spring.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.springdata.model.Product;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findProductByName(String name);

    @Query("select s from Product s where s.id = :id")
    Optional<Product> customProductQuery(int id);

    Optional<Product> findAllByPriceBetween(int min, int max);

}
