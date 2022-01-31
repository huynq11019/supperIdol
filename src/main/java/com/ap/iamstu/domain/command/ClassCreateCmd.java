package com.ap.iamstu.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassCreateCmd {
    private String Code;
    private String name;
    private String description;
    private String fileId;

}
