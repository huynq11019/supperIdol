package com.ap.iamstu.infrastructure.support.query.request;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class FindByIdsRequest implements Serializable {

    @NotEmpty(message = "IDS_REQUIRED")
    private List<String> ids;
}
