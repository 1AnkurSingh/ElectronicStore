package com.electronicstore.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private String categoryId;

    @NotBlank
    @Size(min = 2, max = 20,message = "Title")
    private String title;

    @NotBlank(message = "Description is required")
    private  String description;

    private String coverImage;


}
