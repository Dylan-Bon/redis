package com.example.redis.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PersonDTO implements Serializable {
    private String id;
    private String name;
    private String age;
}
