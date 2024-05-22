package com.project.contactMessage.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data // Bunun  kullanilmasi pek tavsiye edilmez equals() and hashcode() kullanmayacaksak buna gerek yok. pek
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)

@Entity
public class ContactMessage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String subject;
    @NotNull
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = " yyyy-MM-dd HH:mm" , timezone = "US" )
    private LocalDateTime dateTime;



}


