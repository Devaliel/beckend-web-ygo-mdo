package com.example.beckend_website_ygo_mdo.duelist.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class DuelistDetailDTO {
    private Long id;
    private String name;
    private Integer participation;
    private Integer topping;
    private Integer gold;
    private Integer silver;
    private Integer bronze;
    private Integer win;
    private Integer draw;
    private Integer lose;
    private Integer point;
    private String avatarUrl;
}
