package com.hey.request.system.utils;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RecordData {

  private static final long serialVersionUID = 1L;

  private String name;
  private String author;
  private Integer score;
  private String introduction;
}
