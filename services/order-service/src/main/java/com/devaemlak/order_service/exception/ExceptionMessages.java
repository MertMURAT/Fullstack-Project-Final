package com.devaemlak.order_service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String ORDER_NOT_FOUND = "Sipariş bulunamadı.";
    public static final String ORDER_SAVE_ERROR = "Sipariş kaydedilemedi.";
    public static final String ORDER_RETRIEVE_ERROR = "Siparişler getirilemedi.";

}
