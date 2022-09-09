package com.example.redis.controller;

import com.example.redis.dto.PersonDTO;
import com.example.redis.dto.RangeDTO;
import com.example.redis.service.RedisListCache;
import com.example.redis.service.RedisValueCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/person")
public class PersonController {
    private RedisValueCache valueCache;
    private RedisListCache listCache;

    @Autowired
    public PersonController(final RedisValueCache valueCache, RedisListCache listCache) {
        this.valueCache = valueCache;
        this.listCache = listCache;
    }

    @PostMapping
    public void cachePerson(@RequestBody final PersonDTO dto) {
        valueCache.cacheValue(dto.getId(), dto);
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable final String id) {
        return (PersonDTO) valueCache.retrieveValue(id);
    }

    @DeleteMapping("/{id}")
    public void evictPerson(@PathVariable final String id) {
        valueCache.deleteCachedValue(id);
    }

    @PostMapping("/list/{key}")
    public void cachePersons(@PathVariable final String key, @RequestBody final List<PersonDTO> persons) {
        listCache.cachePersons(key, persons);
    }

    @GetMapping("/list/{key}")
    public List<PersonDTO> getPersonsInRange(@PathVariable final String key, @RequestBody final RangeDTO rangeDTO) {
        return listCache.getPersonsInRange(key, rangeDTO);
    }

    @GetMapping("/list/last/{key}")
    public PersonDTO getFinalElement(@PathVariable final String key) {
        return listCache.getLastElement(key);
    }

    @DeleteMapping("/list/{key}")
    public void trim(@PathVariable final String key,@RequestBody final RangeDTO range) {
        listCache.trim(key, range);
    }
}
