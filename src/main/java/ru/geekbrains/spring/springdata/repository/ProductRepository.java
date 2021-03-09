package ru.geekbrains.spring.springdata.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.spring.springdata.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findProductByName(String name);

    @Query("select s from Product s where s.id = :id")

    List<Product> customProductQuery(int id);

    List<Product> findAllByPriceBetween(int min, int max);

    List<Product> findAllByPriceAfter(int from);

    List<Product> findAllByPriceBefore(int to);

}
