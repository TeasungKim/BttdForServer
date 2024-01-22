package com.finalproject.bttd.cache.cacheDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class cacheDto {

    private String email;
    private String privateToken;
    private boolean enable;

    public cacheDto(String email) {
       this.email = email;
    }
}