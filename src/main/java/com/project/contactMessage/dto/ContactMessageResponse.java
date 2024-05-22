package com.project.contactMessage.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

public class ContactMessageResponse {
    // C'est pas necessaire de metre  @NotNull , Blank annotations car, ceux sont viennent de la DB et ils sont déja validés
    private String name;
    private String email;
    private String subject;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyy-MM-dd HH:mm")
    private LocalDateTime dateTime;
}
