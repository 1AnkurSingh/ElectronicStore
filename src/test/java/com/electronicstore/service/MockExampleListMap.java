package com.electronicstore.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class MockExampleListMap {
    @Mock
    List<Integer> list;
    @Mock
    Map<String,Integer> map;

    @Test
    public  void runTest(){

        int listSize=5;
        int valueAtZerothPosition=34;

        Mockito.when(list.size()).thenReturn(5);
        Mockito.when(map.get("first")).thenReturn(34);
//        Assertions.assertEquals(listSize,list.size());

        Assertions.assertEquals(valueAtZerothPosition,map.get("first"));

    }

}
