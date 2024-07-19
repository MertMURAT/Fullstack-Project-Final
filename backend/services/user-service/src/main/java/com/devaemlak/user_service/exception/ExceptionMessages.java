package com.devaemlak.user_service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String USER_FOUNDED = "Kullanıcı bulundu..";
    public static final String USER_SAVED = "Kullanıcı kaydedilme işlemi başarılı.";
    public static final String USER_RETRIEVED = "Kullanıcılar DB' den getirildi.";

    public static final String USER_NOT_FOUND = "Kullanıcı bulunamadı.";
    public static final String USER_SAVE_ERROR = "Kullanıcı kaydedilemedi.";
    public static final String USER_RETRIEVE_ERROR = "Kullanıcılar getirilemedi.";

    // rabbitmq
    public static final String LOG_WRITE_QUEUE = "Log kuyruğa alındı.";

}
