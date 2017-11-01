package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BadgesDto {
    public String votes;
    public AttachmentsByTypeDto attachmentsByType;
}
