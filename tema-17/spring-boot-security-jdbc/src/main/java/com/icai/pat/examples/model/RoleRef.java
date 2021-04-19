package com.icai.pat.examples.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table("USER_ROLE")
@Data
@AllArgsConstructor
public class RoleRef {
  Long roleId;
}