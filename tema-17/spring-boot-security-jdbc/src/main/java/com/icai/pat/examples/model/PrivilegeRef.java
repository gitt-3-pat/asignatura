package com.icai.pat.examples.model;

import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;

@Table("ROLE_PRIVILEGE")
@Data
@AllArgsConstructor
public class PrivilegeRef {
  Long privilegeId;
}