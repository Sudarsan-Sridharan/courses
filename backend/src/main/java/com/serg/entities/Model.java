package com.serg.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
@NoArgsConstructor
public abstract class Model implements Serializable {
    @Id
    private String id;

    public abstract String getUniqueField();
}
