package com.devaemlak.order_service.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessages {

    public static final String ORDER_FOUNDED = "Sipariş bulundu..";
    public static final String ORDER_SAVED = "Sipariş kaydedilme işlemi başarılı.";
    public static final String ORDER_RETRIEVED = "Siparişler DB' den getirildi.";
    public static final String AD_PACKAGE_RETRIEVED = "Paketler DB' den getirildi.";

    public static final String AD_PACKAGE_SAVED = "Paket kaydetme işlemi başarılı.";
    public static final String AD_PACKAGE_UPDATED = "Paket güncelleme işlemi başarılı.";
    public static final String AD_PACKAGE_DELETED = "Paket silme işlemi başarılı.";

    public static final String ORDER_NOT_FOUND = "Sipariş bulunamadı.";
    public static final String ORDER_SAVE_ERROR = "Sipariş kaydedilemedi.";
    public static final String ORDER_RETRIEVE_ERROR = "Siparişler getirilemedi.";

    public static final String AD_PACKAGE_SAVE_ERROR = "Paket kaydetme işlemi sırasında bir hata oluştu.";
    public static final String AD_PACKAGE_UPDATE_ERROR = "Paket kaydetme işlemi sırasında bir hata oluştu.";
    public static final String AD_PACKAGE_DELETE_ERROR = "Paket silme işlemi sırasında bir hata oluştu.";
    public static final String AD_PACKAGE_RETRIEVE_ERROR = "Paketler getirilemedi.";

    // rabbitmq
    public static final String LOG_WRITE_QUEUE = "Log kuyruğa alındı.";

}
