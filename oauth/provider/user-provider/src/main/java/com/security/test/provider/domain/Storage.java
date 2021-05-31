package com.security.test.provider.domain;

import com.security.test.provider.utils.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Data
public class Storage extends Convert<User> {
    private String id;
    private String startTime;
    private String endTime;
    @Email
    private int status;


}
