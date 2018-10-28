package com.todarch.td.application.tag;

import lombok.Data;

@Data
public class TagDto {
  private Long tagId;
  private Long userId;
  private String name;
}
