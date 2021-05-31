package com.security.test.provider.domain;

import com.security.test.provider.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//@TableName("user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Data
public class User extends Convert<Storage> {

    private String id;

    private String email;

    private String name;
}
