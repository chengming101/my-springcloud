package com.security.test.provider.domain;

import com.security.test.provider.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Data
public class UserOrder extends Convert<Storage> {

    private String orderId;

    private String name;

    private String address;
}
