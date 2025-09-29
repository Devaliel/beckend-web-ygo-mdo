package com.example.beckend_website_ygo_mdo.duelist.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DuelistMatchesResponse {
    private DuelistDetailDTO duelist;
    private List<DuelistMatchDTO> matches;
}
