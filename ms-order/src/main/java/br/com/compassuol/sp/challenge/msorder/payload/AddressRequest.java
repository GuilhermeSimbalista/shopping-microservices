package br.com.compassuol.sp.challenge.msorder.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressRequest {

    private String zipCode;
    private String street;
    private String complement;
    private String district;

}
