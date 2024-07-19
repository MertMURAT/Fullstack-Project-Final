package com.devaemlak.log_service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String LOG_CREATED = "Log veritabanına kaydedildi.";
    public static final String LOG_UPDATED = "Log veritabanında güncellendi";
    public static final String LOG_FOUND = "Log veritabanından getirildi.";
    public static final String LOG_NOT_FOUND = "Log veritabanında bulunamadı.";
    public static final String LOGS_LISTED = "Log' lar veritabanından getirildi.";
    public static final String LOG_DELETED = "Log veritabanından silindi.";
    public static final String LOG_READ_QUEUE = "Log kuyruktan okundu.";

}
