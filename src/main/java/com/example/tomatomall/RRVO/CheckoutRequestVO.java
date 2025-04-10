package com.example.tomatomall.RRVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CheckoutRequestVO {
    private List<String> cartItemIds;
    private ShippingAddress shippingAddress;
    private String paymentMethod;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class ShippingAddress {
        private String name;
        private String phone;
        private String postalCode;
        private String address;
    }
}