package br.com.compassuol.sp.challenge.msorder.exception;

import br.com.compassuol.sp.challenge.msorder.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testConstructorWithFieldValue() {
        // Given
        String resourceName = "User";
        String fieldName = "id";
        Long fieldValue = 123L;

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName, fieldName, fieldValue);

        // Then
        String expectedMessage = "User Not Found with id : '123'";
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(resourceName, exception.getResourceName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(fieldValue, exception.getFieldValue());
    }

    @Test
    public void testConstructorWithoutFieldValue() {
        // Given
        String resourceName = "Product";
        String fieldName = "name";

        // When
        ResourceNotFoundException exception = new ResourceNotFoundException(resourceName, fieldName);

        // Then
        String expectedMessage = "Product Not found with name";
        assertEquals(expectedMessage, exception.getMessage());
        assertEquals(resourceName, exception.getResourceName());
        assertEquals(fieldName, exception.getFieldName());
        assertEquals(null, exception.getFieldValue());
    }
}
