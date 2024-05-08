package com.spotify.oauth2.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;


@Setter
@Getter
@Jacksonized
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorInner {
        @JsonProperty("status")
        private Integer status;
        @JsonProperty("message")
        private String message;
}
