package com.telcobright.accountservicev02.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class ResellerRelationBody {
    Integer parentId = null;
    List<Integer> childrenUserIdList= new ArrayList<>();
}
