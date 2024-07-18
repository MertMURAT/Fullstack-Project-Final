package com.devaemlak.advertisement_service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String ADVERTISEMENT_NOT_FOUND = "İlan bulunamadı.";
    public static final String ADVERTISEMENT_SAVE_ERROR = "İlan kaydedilemedi.";
    public static final String ADVERTISEMENT_RETRIEVE_ERROR = "İlanlar getirilemedi.";
    public static final String ADVERTISEMENT_UPDATE_STATUS_ERROR = "İlan durumu güncellenemedi.";
    public static final String ADVERTISEMENT_UPDATE_ERROR = "İlan güncellenirken bir hata oluştu.";
    public static final String FILE_PATH_ERROR = "Dosya geçersiz yol sırası içeriyor";
    public static final String FILE_SAVE_ERROR = "Dosya kaydedilemedi!";
    public static final String FILE_NOT_FOUND = "Dosya bulunamadı!";

}
