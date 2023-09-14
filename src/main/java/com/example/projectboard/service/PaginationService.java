package com.example.projectboard.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class PaginationService {
    private static final int BAR_LENGTH = 5;

    public List<Integer> getPaginationBarNumbers(int currentPageNumber, int totalPages){
        int startNumber = Math.max(currentPageNumber - (BAR_LENGTH / 2), 0);//중앙 값을 찾는다, 양수가 될 수 있게 0보다 큰 값을 찾는다.
        int endNumber = Math.min(startNumber + BAR_LENGTH, totalPages);//총페이지 보다 큰 값이 나오지 않게 하기 위해서 min값을 구한다.

        return IntStream.range(startNumber, endNumber).boxed().toList();
    }

    public int currentBarLength(){
        return BAR_LENGTH;
    }

}
