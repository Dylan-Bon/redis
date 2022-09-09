package com.example.redis.service;

import com.example.redis.dto.PersonDTO;
import com.example.redis.dto.RangeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RedisListCache {
    private RedisTemplate<String, Object> redisTemplate;
    private ListOperations<String, Object> listOperations;

    public RedisListCache(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        listOperations = redisTemplate.opsForList();
    }

    public void cachePersons(final String key, final List<PersonDTO> persons) {
        for (final PersonDTO person : persons) {
            listOperations.leftPush(key, person);
        }
    }

    public List<PersonDTO> getPersonsInRange(final String key, final RangeDTO range) { //0, -1 for full list
        final List<Object> objects = listOperations.range(key, range.getFrom(), range.getTo());

        if (CollectionUtils.isEmpty(objects))
            return Collections.emptyList();

        return objects.stream().map(PersonDTO.class::cast).collect(Collectors.toList());
    }

    public PersonDTO getLastElement(final String key) {
        final Object o = listOperations.rightPop(key);
        if (o == null)
            return null;
        return (PersonDTO) o;
    }

    public void trim(final String key, final RangeDTO range) {
        listOperations.trim(key, range.getFrom(), range.getTo());
    }

}
