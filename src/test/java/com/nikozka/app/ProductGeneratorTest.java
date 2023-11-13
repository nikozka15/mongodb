package com.nikozka.app;

import com.nikozka.app.models.ProductDTO;
import com.nikozka.app.utilities.ProductGenerator;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductGeneratorTest {
    Validator validator = mock(Validator.class);
    ProductGenerator testObject = new ProductGenerator(validator);

    @Test
    void testGenerateProductsAllValid() {
        Set<ConstraintViolation<ProductDTO>> constraintViolations = new HashSet<>();
        when(validator.validate(any(ProductDTO.class))).thenReturn(constraintViolations);
        assertEquals(5, testObject.generateProducts(5).size());
    }

    @Test
    void testGenerateProductsIfOneInvalid() {
        ProductDTO invalidProduct = new ProductDTO("Invalid", 122);
        Set<ConstraintViolation<ProductDTO>> constraintViolations = new HashSet<>();
        constraintViolations.add(createConstraintViolation(invalidProduct));

        when(validator.validate(any(ProductDTO.class))).thenReturn(constraintViolations).thenReturn(new HashSet<>());
        assertEquals(1, testObject.generateProducts(1).size());
    }

    private ConstraintViolationImpl<ProductDTO> createConstraintViolation(ProductDTO productDTO) {
        return (ConstraintViolationImpl<ProductDTO>) ConstraintViolationImpl.forBeanValidation(null,
                null, null, "Error", ProductDTO.class, productDTO,
                null, null, null, null, null);
    }
}
