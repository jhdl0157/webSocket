package com.example.scoket.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PathVariable {
    DEVICE_ID(2),DEVICE_TYPE(3),OPERATION_ID(4);
    private final int index;
}
