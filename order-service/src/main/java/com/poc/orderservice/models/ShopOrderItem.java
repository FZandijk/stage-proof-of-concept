package com.poc.orderservice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopOrderItem {

    @NotNull(message = "The productId cannot be null.")
    private Long productId;

    @Size(max = 45, message = "The maximum length of a short description is 45 characters.")
    @NotBlank(message = "The short description cannot be blank.")
    private String shortDescription;

    @Positive(message = "The count cannot be 0 or negative.")
    @NotNull(message = "The count cannot be null.")
    private Integer count;

    @Positive(message = "The price cannot be 0 or negative.")
    @NotNull(message = "The price cannot be null.")
    private Double price;
}
