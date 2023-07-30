package br.com.compassuol.sp.challenge.msorder.entities;

import br.com.compassuol.sp.challenge.msorder.entities.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private List<Long> products;

    @Embedded
    private DeliveryAddress deliveryAddress;

    @Enumerated(EnumType.STRING)
    private Status status;
}
