package com.pwc.testtask.repository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class CachedCountriesDataRetriever implements CountriesRepository {

    private final CountriesRepository delegate;

    public CachedCountriesDataRetriever(final CountriesRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    @Cacheable(cacheNames = "countries", unless = "#result == null")
    public List<Country> getCountries() {
        return delegate.getCountries();
    }
}
