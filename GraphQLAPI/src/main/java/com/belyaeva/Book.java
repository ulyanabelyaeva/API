package com.belyaeva;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {

    private int id;

    private String name;

    private String genre;

    private String author;
}
