package com.hust.attandance.utils

object Constants {
    /**
     * URL
     */
    const val KV_REGISTER_URL: String =
        "https://www.kiotviet.vn/dang-ky?refcode=android-retailer-man"

    /**
     * Name
     */
    const val SP_NAME = "KV"

    /**
     * Number
     */
    const val FIRST_LOAD_CONVERSATION = 150
    const val LOAD_MORE_CONVERSATION = 30
    const val LOAD_MORE_VALUE = 20
    const val LOAD_MORE_POST = 20
    const val MINIMUM_CHAT = 30
    const val MIMIMUM_COMMENT = 150
    const val PAGING_SIZE = 20000
    const val FREQUENT_SYNC_DELAY = 15 * 60 * 1000
    const val SYNC_DELAY = 31536000000// 1 year
    const val MAX_FIRST_SEARCH_PRODUCT: Long = 2000
    const val DEFAULT_NUM_VISIBLE_THRESHOLD = 5

    /**
     * Fragment Result Key
     */
    const val PRICEBOOK = "PriceBook"
    const val PARTNER = "Partner"
    const val CUSTOMER = "Customer"
    const val SUPPLIER = "Supplier"
    const val CUSTOMER_SELECTOR = "Customer_Selector"
    const val ADDRESS_SELECTOR_KEY = "ADDRESS_SELECTOR_KEY"
    const val ADDRESS_SELECTOR_VALUE = "ADDRESS_SELECTOR_VALUE"

    const val CART_ITEM = "Cart_item"
    const val SURCHARGE_ITEM = "Surcharge_item"
    const val METHOD_PAYMENT = "Payment"
    const val METHOD_PAYMENT_BANK = "Payment_Bank"
    const val METHOD = "Method"

    const val RESULT_VALUE = "Result_Value"
    const val IS_MANUAL_LOGOUT = "IS_MANUAL_LOGOUT"
    const val IS_FB_REAUTHENTICATION_REQUIRED =
        "IS_FB_REAUTHENTICATION_REQUIRED" // Decide whether this is logout will result in logging out of facebook.

    /**
     * Exception
     */
    const val ERROR_MESSAGE_UNTRUSTED_DEVICE = "KvUserDeviceUnTrustException"
    const val ERROR_MESSAGE_USER_NEW_DEVICE_EXCEPTION = "KvUserNewDeviceExeption"
    const val KV_VALIDATE_EXCEPTION = "KvValidateException"
    /**
     * Customer tag
     */
    const val refuseContact = "defaultcustomertag_canhbao"
    const val attentionRequired = "defaultcustomertag_khongnghemay"
    const val refusedDelivery = "defaultcustomertag_bomhang"

    /**
     * Broadcast Action
     */
    const val ACTION_LOGOUT: String = "ACTION_LOGOUT"
    const val ACTION_DATABASE_CHANGE: String = "ACTION_NAME_CHANGE_DATABASE"
    const val ACTION_TABLE_CHANGE: String = "ACTION_CHANGE_TABLE"
    const val ACTION_CHANGE_TYPE: String = "ACTION_CHANGE_TYPE"
    const val ACTION_DATA_CHANGE: String = "data"
    const val ACTION_OMNI_NOTIFICATION_INCOME = "ACTION_OMNI_NOTIFICATION_INCOME"
    const val ACTION_MY_KIOT_NOTIFICATION_INCOME = "ACTION_MY_KIOT_NOTIFICATION_INCOME"
    const val ACTION_REQUEST_CASH_FLOW_RELOAD = "REQUEST_CASH_FLOW_RELOAD"
    const val ACTION_REQUEST_ORDERS_RELOAD = "REQUEST_ORDERS_RELOAD"
    const val ACTION_REQUEST_PRODUCT_RESYNC = "REQUEST_PRODUCT_RESYNC"
    const val ACTION_AIR_PRINT= "ACTION_AIR_PRINT_BITMAP"
    const val BITMAP_AIR_PRINT= "DATA_BITMAP_AIR_PRINT"
    const val HTML_AIR_PRINT= "DATA_HTML_AIR_PRINT"

    /**
     * Image token prefix
     * !Don't change live value!
     */
    const val IMAGE_PRODUCT: String = "product_"


    /**
     * Log prefix
     */
    const val ATTANDANCE_LOG: String = "ATTANDANCE_"
    const val FB_LOG = "KV_FB"

    /**
     * Template message tokens
     */

    const val TOKEN_CUSTOMER_FIRST_NAME = "{{user_first_name}}"
    const val TOKEN_CUSTOMER_LAST_NAME = "{{user_last_name}}"
    const val TOKEN_CUSTOMER_FULL_NAME = "{{user_full_name}}"
    const val TOKEN_FANPAGE_URL = "{{page_url}}"
    val TAG_COLORS = listOf(
        "#41AC49",
        "#FE7A00",
        "#FF0000",
        "#B031DD",
        "#7362D4",
        "#238FED",
        "#3E3AE1",
        "#FF0099",
        "#856893",
        "#B89766",
        "#786C6C",
        "#8A9B3D",
        "#B06F7E",
        "#4A9B96",
        "#3D4E59",
        "#20B685",
        "#D8BC0A",
        "#EB9E9E"
    )

    const val TOKEN_RETAILER_NAME = "{Ten_Cua_Hang}"
    const val TOKEN_BRANCH_NAME = "{Chi_Nhanh_Ban_Hang}"
    const val TOKEN_BRANCH_ADDRESS = "{Dia_Chi_Chi_Nhanh}"
    const val TOKEN_BRANCH_PHONE_NUMBER = "{Dien_Thoai_Chi_Nhanh}"
    const val TOKEN_INVOICE_TIME = "{Ngay_Thang_Nam}"
    const val TOKEN_INVOICE_CODE = "{Ma_Dat_Hang}"
    const val TOKEN_CUSTOMER_NAME = "{Khach_Hang}"
    const val TOKEN_CUSTOMER_ADDRESS = "{Dia_Chi_Khach_Hang}"
    const val TOKEN_CUSTOMER_PHONE_NUMBER = "{So_Dien_Thoai}"
    const val TOKEN_DELIVERY_PARTNER = "{Doi_Tac_Giao_Hang}"
    const val TOKEN_DELIVERY_FEE = "{Phi_Giao_Hang}"
    const val TOKEN_COD_VALUE = "{Tien_Thu_Ho}"
    const val TOKEN_SELLER_NAME = "{Nhan_Vien_Ban_Hang}"
    const val TOKEN_PRODUCT_NAME = "{Ten_Hang_Hoa}"
    const val TOKEN_PRODUCT_PRICE = "{Don_Gia_Chiet_Khau}"
    const val TOKEN_PRODUCT_QUANTITY = "{So_Luong}"
    const val TOKEN_PRODUCT_TOTAL = "{Thanh_Tien}"
    const val TOKEN_ALL_PRODUCT_TOTAL = "{Tong_Tien_Hang}"
    const val TOKEN_INVOICE_DISCOUNT = "{Chiet_Khau_Hoa_Don}"
    const val TOKEN_SURCHARGE_NAME = "{Ten_Loai_Thu_Khac}"
    const val TOKEN_SURCHARGE_VALUE = "{Muc_Thu_Khac}"
    const val TOKEN_INVOICE_FINAL = "{Tong_Cong}"
    const val TOKEN_CUSTOMER_PAID = "{Tong_Thanh_Toan}"

    const val TOKEN_TOGGLE_VISIBILITY = "{TOGGLE_VISIBILITY}"
    const val HIDE_INFO = "class=\"hide-info\""

    // Notification Channel IDs
    const val CHANNEL_MESSAGE_ID = "CHANNEL_MESSAGE_ID"
    const val CHANNEL_COMMENT_ID = "CHANNEL_COMMENT_ID"
    const val CHANNEL_REACTION_ID = "CHANNEL_REACTION_ID"
    const val CHANNEL_OTHER_ID = "CHANNEL_OTHER_ID"
    const val CHANNEL_OMNI_SHOPEE_NOTIFICATION_ID = "CHANNEL_OMNI_SHOPEE_NOTIFICATION_ID"

    // FCM key
    const val KEY_OMNI_FCM_TYPE = "Type"
    const val KEY_OMNI_FCM_ORDER_IDS = "OrderIds"
    const val KEY_OMNI_FCM_ORDER_ID = "OrderId"
    const val KEY_OMNI_FCM_NOTIFICATION_ID = "_id"

    // const
    const val OMNI_NOTIFICATION_TYPE = "OrderNotification"
    const val MY_KIOT_NOTIFICATION_TYPE = "KvNotification"

    /**
     * Qualifier
     */
    const val QUALIFIER_REALM_CONFIGURATION_RMM = "qualifier_rmmigration"
    const val QUALIFIER_REALM_CONFIGURATION_NEED_DELETE = "qualifier_need_delete"
    const val FACE_ID_DATA = "FACE_ID_DATA"
    const val FACE_ID_KEY = "FACE_ID_KEY"
}
