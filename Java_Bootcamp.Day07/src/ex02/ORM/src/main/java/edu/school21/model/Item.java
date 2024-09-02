package edu.school21.model;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;

@OrmEntity(table = "simple_items")
public class Item {
    @OrmColumnId
    private Long id;

    @OrmColumn(name = "name", length = 32)
    private String name;

    @OrmColumn(name = "price")
    private Double price;

    @OrmColumn(name = "age_limit")
    private Boolean age_limit;

}
