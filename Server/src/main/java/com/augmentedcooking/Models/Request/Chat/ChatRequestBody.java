package com.augmentedcooking.Models.Request.Chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestBody {

    private String model;
    private String prompt;

    @JsonProperty(defaultValue = "false")
    private boolean streamed;

    @JsonProperty(defaultValue = "")
    private String imageb64;
}
