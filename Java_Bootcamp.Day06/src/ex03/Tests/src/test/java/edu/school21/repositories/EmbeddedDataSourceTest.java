package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmbeddedDataSourceTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS;

    {
        EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>();
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(0L, "Winston Blue", 200));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(1L, "Winston Classic", 178));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(2L, "Winston Compact Shiny Mix", 133));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(3L, "Kent Nano Mix Bali", 199));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(4L, "Kent White", 179));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(5L, "Bond Street Premium", 139));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(6L, "Captain Black Classic", 250));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(7L, "Marlboro Gold Original", 219));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(8L, "L&M COMPACT", 127));
        EXPECTED_FIND_ALL_PRODUCTS.add(new Product(9L, "Sobranie Blacks", 228));
    }

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(3L, "Kent Nano Mix Bali", 199);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(9L, "Niagara", 420);
    final Product EXPECTED_SAVED_PRODUCT = new Product(10L, "Camel", 300);
    private EmbeddedDatabase db;
    private ProductsRepositoryJdbcImpl productsRepositoryJdbc;

    @BeforeEach
    void init() {
        db = new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(db);
    }

    @Test
    void checkConnection() {
        try {
            assertNotNull(db.getConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testFindById() {
        ProductsRepositoryJdbcImpl productsRepositoryJdbc = new ProductsRepositoryJdbcImpl(db);
        Product product = productsRepositoryJdbc.findById(3L).get();
        assertNotNull(product);
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product);
    }

    @Test
    void testFindAll() {
        List<Product> products = productsRepositoryJdbc.findAll();
        assertNotNull(products);
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, products);
    }

    @Test
    void testUpdate() {
        productsRepositoryJdbc.update(EXPECTED_UPDATED_PRODUCT);
        Product product = productsRepositoryJdbc.findById(9L).get();
        assertNotNull(product);
        assertEquals(EXPECTED_UPDATED_PRODUCT, product);
    }

    @Test
    void testSave() {
        productsRepositoryJdbc.save(EXPECTED_SAVED_PRODUCT);
        Product savedProduct = productsRepositoryJdbc.findById(10L).get();
        assertNotNull(savedProduct);
        assertEquals(EXPECTED_SAVED_PRODUCT, savedProduct);
    }

    @Test
    void testDelete() {
        productsRepositoryJdbc.delete(0L);
        List<Product> products = productsRepositoryJdbc.findAll();
        Product product = productsRepositoryJdbc.findById(0L).orElse(null);
        assertEquals(9, products.size());
        assertNull(product);
    }


    @AfterEach
    void shutdown() {
        db.shutdown();
    }
}
