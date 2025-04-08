package com.example.tomatomall.RRVO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ReviseCartRequestVO {
    private Integer quantity;
    private String cartItemId;
}
