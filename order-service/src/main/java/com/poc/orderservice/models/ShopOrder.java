package com.poc.orderservice.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Document
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopOrder {

    @Id
    private UUID orderId = UUID.randomUUID();

    @NotBlank(message = "The customer name cannot be blank.")
    private String customerName;

    @Size(min = 1, message = "Provide at least one item in the order.")
    @NotNull(message = "Items cannot be null.")
    @Valid
    private List<ShopOrderItem> items;
}
