package com.devaemlak.advertisement_service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    // SUCCESS

    public static final String ADVERTISEMENT_CREATED = "İlan oluşturuldu.";
    public static final String ADVERTISEMENT_UPDATED = "İlan güncellendi.";
    public static final String ADVERTISEMENT_RETRIEVED = "DB' den ilanlar başarıyla getirildi.";
    public static final String FILE_ATTACHMENT_SAVED = "Dosya kaydetme işlemi başarılı.";
    public static final String FILE_ATTACHMENT_RETRIEVED = "DB' den dosyalar başarıyla getirildi";

    // FAILED
    public static final String ADVERTISEMENT_NOT_FOUND = "İlan bulunamadı.";
    public static final String ADVERTISEMENT_SAVE_ERROR = "İlan kaydedilemedi.";
    public static final String ADVERTISEMENT_RETRIEVE_ERROR = "DB' den ilanlar çekilirken hata oluştu";
    public static final String ADVERTISEMENT_UPDATE_STATUS_ERROR = "İlan durumu güncellenemedi.";
    public static final String ADVERTISEMENT_UPDATE_ERROR = "İlan güncellenirken bir hata oluştu.";
    public static final String ADVERTISEMENT_TYPE_ERROR = "İlan tipi Satılık ilan tipinde değil";
    public static final String FILE_PATH_ERROR = "Dosya geçersiz yol sırası içeriyor";
    public static final String FILE_NOT_FOUND = "Dosya bulunamadı!";
    public static final String FILE_ATTACHMENT_ERROR = "Dosya kaydedilemedi.";
    public static final String FILE_ATTACHMENT_RETRIEVE_ERROR = "DB' den dosyalar getirilirken hata oluştu.";
    // rabbitmq
    public static final String LOG_WRITE_QUEUE = "Log kuyruğa alındı.";

}
