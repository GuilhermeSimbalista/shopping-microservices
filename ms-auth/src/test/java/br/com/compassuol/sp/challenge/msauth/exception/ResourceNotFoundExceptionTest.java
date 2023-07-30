package br.com.compassuol.sp.challenge.msauth.exception;

import br.com.compassuol.sp.challenge.msauth.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResourceNotFoundExceptionTest {

    @Test
    public void testResourceNotFoundException() {
    String resourceName = "Product";
    String fieldName = "id";
    String fieldValue = "123";

    ResourceNotFoundException exception = new ResourceNotFoundException(resourceName, fieldName, fieldValue);

    String expectedMessage = String.format("%s Not Found with %s : '%s'", resourceName, fieldName, fieldValue);
    assertEquals(expectedMessage, exception.getMessage());
    assertEquals(resourceName, exception.getResourceName());
    assertEquals(fieldName, exception.getFieldName());
    assertEquals(fieldValue, exception.getFieldValue());
}
}

