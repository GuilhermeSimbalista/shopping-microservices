package br.com.compassuol.sp.challenge.msproduct.exception;

import br.com.compassuol.sp.challenge.msproduct.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testResourceNotFoundExceptionWithField() {
        // Given
        String resourceName = "Product";
        String fieldName = "id";
        Long fieldValue = 123L;

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName, fieldName, fieldValue);

        // Then
        assertEquals("Product Not Found with id : '123'", exception.getMessage());
        assertEquals(resourceName, exception.getResourceName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(fieldValue, exception.getFieldValue());
    }

    @Test
    public void testResourceNotFoundExceptionWithoutField() {
        String resourceName = "Product";
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName);
        assertEquals(resourceName, exception.getResourceName());
        assertNull(exception.getFieldName());
        assertNull(exception.getFieldValue());
    }

    @Test
    public void testResponseStatus() {
        String resourceName = "Product";
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName);
    }
}
