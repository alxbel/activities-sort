package com.merann.actsort.service;

public interface Converter<From, To> {
    To convert(From from);
}
