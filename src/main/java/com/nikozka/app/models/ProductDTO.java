package com.nikozka.app.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public class ProductDTO {
    @NotNull
    @Size(min = 3, max = 25, message = "name must be between 3 and 25 characters")
    @Pattern(regexp = "[a-zA-Z]+", message = "name should contain only alphabetical characters")
    private final String name;
    @NotNull
    @Range(min= 1, max= 20, message = "categoryId must be between 1 and 20 (included)")
    private final int categoryId;

    public ProductDTO(String name, int categoryId) {
        this.name = name;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public int getCategoryId() {
        return categoryId;
    }
}
