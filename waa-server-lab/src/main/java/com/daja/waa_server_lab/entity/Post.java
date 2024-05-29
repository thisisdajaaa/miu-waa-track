package com.daja.waa_server_lab.entity;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class Post {
    private Long id;
    private String title;
    private String content;
    private String author;
}
