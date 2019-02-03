package com.merann.actsort.service;

public interface Sorter<Response, Unsorted> {
    Response sort(Unsorted unsorted);
}
