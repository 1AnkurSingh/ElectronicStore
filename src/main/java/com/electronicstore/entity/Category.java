package com.electronicstore.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.swing.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category {

    @Id
    private String categoryId;

    private String title;

    private  String description;

    private String coverImage;
}
