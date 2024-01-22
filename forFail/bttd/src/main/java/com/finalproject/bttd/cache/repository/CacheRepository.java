package com.finalproject.bttd.cache.repository;

import com.finalproject.bttd.cache.cacheDto.cacheDto;
import com.finalproject.bttd.cache.cacheDto.cacheDtos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
@CacheConfig(cacheNames = "cacheDtos")
public class CacheRepository {

    private final Map<String, cacheDto> store = new LinkedHashMap<>();

//    @Cacheable(key = "'all'")
    public cacheDtos findAll() {
        List<cacheDto> cacheDtos = store.values().stream().toList();
        log.info("Repository findAll {}", cacheDtos);
        return new cacheDtos(cacheDtos);
    }

 //   @Cacheable(key = "#email", unless = "#result == null")
    public cacheDto findById(String email) {

        cacheDto member = store.get(email);
        log.info("Repository find {}", member);
        return member;
    }


  //  @CacheEvict(key = "'all'")
    public cacheDto save(cacheDto memberDto) {
        log.info("save : " + memberDto);
        String newId = memberDto.getEmail();
        memberDto.setEmail(newId);

        log.info("Repository save {}", memberDto);

        store.put(memberDto.getEmail(), memberDto);
        return memberDto;
    }




 //   @CacheEvict(key = "'all'")
    public cacheDto update(cacheDto cachedto) {
        log.info("Repository update {}", cachedto);
        cachedto.setEnable(true);
        store.put(cachedto.getEmail(), cachedto);
        return cachedto;
    }

//    @Caching(evict = {
 //           @CacheEvict(key = "'all'"),
  //          @CacheEvict(key = "#email")
   // })
    public void delete(cacheDto cachedto) {
        log.info("Repository delete {}", cachedto);
        store.remove(cachedto.getEmail());
    }


 //   @Cacheable(key = "#email", unless = "#result == null")
    public cacheDto findByVerificationToken(String privateToken) {
        log.info("verify 3 :" + privateToken);
        List<cacheDto> cacheDtos = new ArrayList<>(store.values()); // store.values()를 List로 변환

        log.info("verify 3.1 : " + cacheDtos);

        for (int i = 0; i < cacheDtos.size(); i++) {
            cacheDto dto = cacheDtos.get(i);
            if (dto.getPrivateToken().equals(privateToken)) {
                String email = dto.getEmail();
                cacheDto newMember = findById(email);
                return newMember;

            }
        }

        return null;


    }


    //
}

