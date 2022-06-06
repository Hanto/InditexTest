package com.inditex.test.product.adapter.persistence;// Created by jhant on 06/06/2022.

import org.springframework.data.keyvalue.repository.KeyValueRepository;

interface SequenceRepository extends KeyValueRepository<SequenceEntity, String>
{
}