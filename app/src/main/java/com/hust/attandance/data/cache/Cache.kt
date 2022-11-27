package net.citigo.kiotviet.pos.fb.data.cache

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hust.attandance.data.response.LoginResonse
import java.lang.reflect.Type


class Cache(private val cacheSource: CacheSource) {

    companion object {
        const val LOGIN_RESPONSE = "login_response"

    }

    //    fun saveCacheAppVersion() {
//        cacheSource.putString(KV_CACHE_APP_VERSION, BuildConfig.VERSION_NAME)
//    }
//
//    fun getCacheAppVersion(): String {
//        return cacheSource.getString(KV_CACHE_APP_VERSION, "")
//    }
//
//    fun saveLoginResponse(loginResonse: LoginResonse) {
//        cacheSource.putInt(LOGIN_RESPONSE, lo.value)
//    }
//
//    fun getOrderDeliveryType(): OrderDeliveryType {
//        return when (cacheSource.getInt(OMNI_ORDER_DELIVERY_TYPE, -1)) {
//            OrderDeliveryType.DROP_OFF.value -> OrderDeliveryType.DROP_OFF
//            else -> OrderDeliveryType.PICKUP // default is pickup
//        }
//    }
//
//    fun saveOrderProcessFilter(orderProcess: OrderProcess) {
//        cacheSource.putInt(OMNI_ORDER_PROCESS_FILTER, orderProcess.value)
//    }
//
//    fun getOrderProcessFilter(): OrderProcess {
//        return when (cacheSource.getInt(OMNI_ORDER_PROCESS_FILTER, -1)) {
//            OrderProcess.NEW.value -> OrderProcess.NEW
//            OrderProcess.READY_TO_SHIP.value -> OrderProcess.READY_TO_SHIP
//            else -> OrderProcess.NONE // default is ALL
//        }
//    }
//
//    fun saveOrderReturnsFilter(orderReturn: OrderReturn) {
//        cacheSource.putInt(OMNI_ORDER_RETURN_FILTER, orderReturn.value)
//    }
//
//    fun getOrderReturnFilter(): OrderReturn {
//        return when (cacheSource.getInt(
//            OMNI_ORDER_RETURN_FILTER,
//            OrderReturn.TO_CONFIRM_RETURN.value
//        )) {
//            OrderReturn.TO_CONFIRM_RETURN.value -> OrderReturn.TO_CONFIRM_RETURN
//            OrderReturn.CONFIRMED_RETURN.value -> OrderReturn.CONFIRMED_RETURN
//            else -> OrderReturn.NONE // default is ALL
//        }
//    }
//
//
//    fun savePaymentMethod(paymentMethod: PaymentMethodEnum) {
//        cacheSource.putString(KV_PAYMENT_METHOD, paymentMethod.value)
//    }
//
//    fun getPaymentMethod(): PaymentMethodEnum {
//        return when (cacheSource.getString(KV_PAYMENT_METHOD, PaymentMethodEnum.TRANSFER.value)) {
//            PaymentMethodEnum.TRANSFER.value -> PaymentMethodEnum.TRANSFER
//            PaymentMethodEnum.CARD.value -> PaymentMethodEnum.CARD
//            PaymentMethodEnum.COD.value -> PaymentMethodEnum.COD
//            else -> PaymentMethodEnum.CASH
//        }
//    }
//
//    fun saveCashFlowDateFilter(cashFlowDateFilter: CashFlowDateFilter) {
//        cacheSource.putInt(KV_CASH_FLOW_DATE_FILTER, cashFlowDateFilter.ordinal)
//    }
//
//    fun getCashFlowDateFilter(): CashFlowDateFilter {
//        return when (cacheSource.getInt(
//            KV_CASH_FLOW_DATE_FILTER,
//            CashFlowDateFilter.THIS_MONTH.ordinal
//        )) {
////            CashFlowDateFilter.ALL_TIME.ordinal -> CashFlowDateFilter.ALL_TIME
//            CashFlowDateFilter.TODAY.ordinal -> CashFlowDateFilter.TODAY
//            CashFlowDateFilter.YESTERDAY.ordinal -> CashFlowDateFilter.YESTERDAY
//            CashFlowDateFilter.THIS_WEEK.ordinal -> CashFlowDateFilter.THIS_WEEK
//            CashFlowDateFilter.THIS_MONTH.ordinal -> CashFlowDateFilter.THIS_MONTH
//            CashFlowDateFilter.LAST_MONTH.ordinal -> CashFlowDateFilter.LAST_MONTH
//            CashFlowDateFilter.OPTIONAL.ordinal -> CashFlowDateFilter.OPTIONAL
//            else -> CashFlowDateFilter.THIS_MONTH
//        }
//    }
//
//    fun saveCashFlowDateFilterOptional(startDate: Long, endDate: Long) {
//        cacheSource.putLong(KV_CASH_FLOW_DATE_FILTER_OPTIONAL_START_DATE, startDate)
//        cacheSource.putLong(KV_CASH_FLOW_DATE_FILTER_OPTIONAL_END_DATE, endDate)
//    }
//
//    fun getCashFlowDateFilterOptional(): Pair<Long, Long> {
//        return Pair(
//            cacheSource.getLong(KV_CASH_FLOW_DATE_FILTER_OPTIONAL_START_DATE, 0L),
//            cacheSource.getLong(KV_CASH_FLOW_DATE_FILTER_OPTIONAL_END_DATE, 0L)
//        )
//    }
//
//    fun saveUsageFeature(usageFeatureEnum: UsageFeatureEnum) {
//        cacheSource.putInt(USAGE_FEATURE, usageFeatureEnum.value)
//    }
//
//    fun getUsageFeature(): UsageFeatureEnum {
//        return when (cacheSource.getInt(USAGE_FEATURE, -1)) {
//            UsageFeatureEnum.FACEBOOK_ONLY.value -> UsageFeatureEnum.FACEBOOK_ONLY
//            UsageFeatureEnum.OMNI_ONLY.value -> UsageFeatureEnum.OMNI_ONLY
//            UsageFeatureEnum.BOTH.value -> UsageFeatureEnum.BOTH
//            //else -> UsageFeatureEnum.LOGOUT
//            else -> {
//                if (BuildConfig.APPLICATION_ID.endsWith("free")) {
//                    UsageFeatureEnum.OMNI_ONLY
//                } else {
//                    throw KvException(ExceptionEnum.SESSION_EXPIRE_KV.value) // force logout neu van dang o ban chi dung fb
//                }
//            }
//
//        }
//    }
//
//    fun saveLastPageIds(pages: List<String>) {
//        cacheSource.putString(KV_LAST_ACTIVE_PAGE_ID, pages.joinToString(","))
//    }
//
//    fun getLastPageIds(): List<String> {
//        val pageIds = cacheSource.getString(KV_LAST_ACTIVE_PAGE_ID, "")
//        if (pageIds.isEmpty()) {
//            return emptyList()
//        }
//        return cacheSource.getString(KV_LAST_ACTIVE_PAGE_ID, "").split(",")
//    }
//
//    fun saveSyncTimeStamp(key: String, timeStamp: Long) {
//        cacheSource.putLong(key + getRetailer() + getBranchId() + getUserName(), timeStamp)
//    }
//
//    fun getSyncTimeStamp(key: String): Long {
//        return cacheSource.getLong(key + getRetailer() + getBranchId() + getUserName(), 0)
//    }
//
//    fun saveRetailerId(retailerId: Long) {
//        cacheSource.putLong(KV_RETAILER_ID, retailerId)
//    }
//
//    fun getRetailerId(): Long {
//        return cacheSource.getLong(KV_RETAILER_ID, -1)
//    }
//
//    fun saveRetailer(retailer: String) {
//        cacheSource.putString(KV_RETAILER_NAME, retailer)
//    }
//
//    fun getRetailer(): String {
//        return cacheSource.getString(KV_RETAILER_NAME, "")
//    }
//
//    fun saveUserName(userName: String) {
//        cacheSource.putString(KV_USER_NAME, userName)
//    }
//
//    fun getUserName(): String {
//        return cacheSource.getString(KV_USER_NAME, "")
//    }
//
//    fun saveUserId(userId: Long) {
//        cacheSource.putLong(KV_USER_ID, userId)
//    }
//
//    fun getUserId(): Long {
//        return cacheSource.getLong(KV_USER_ID, -1)
//    }
//
//    fun saveZoneId(zoneId: Int) {
//        cacheSource.putInt(KV_ZONE_ID, zoneId)
//    }
//
//    fun getZoneId(): Int {
//        return cacheSource.getInt(KV_ZONE_ID, -1)
//    }
//
//    fun saveBranchId(branchId: Long) {
//        cacheSource.putLong(KV_BRANCH_ID, branchId)
//    }
//
//    fun getBranchId(): Long {
//        return cacheSource.getLong(KV_BRANCH_ID, 0)
//    }
//
//    fun getCookie(): String {
//        return cacheSource.getString(KV_COOKIE + getRetailer() + getUserName(), "")
//    }
//
//    fun saveCookie(retailerId: Long, userId: Long, branchId: Long) {
//        cacheSource.putString(
//            KV_COOKIE + getRetailer() + getUserName(),
//            "LatestBranch_${retailerId}_${userId}=${branchId}"
//        )
//    }
//
//    fun saveBearer(bearer: String) {
//        cacheSource.putString(KV_BEARER, bearer)
//    }
//
//    fun getBearer(): String {
//        return cacheSource.getString(KV_BEARER, "")
//    }
//
//    fun saveRegisterBearer(bearer: String) {
//        cacheSource.putString(KV_REGISTER_BEARER, bearer)
//    }
//
//    fun getRegisterBearer(): String {
//        return cacheSource.getString(KV_REGISTER_BEARER, "")
//    }
//
//    fun saveMobileApi(mobileApi: String) {
//        cacheSource.putString(KV_MOBILE_API, mobileApi)
//    }
//
//    fun getMobileApi(): String {
//        return cacheSource.getString(KV_MOBILE_API, BuildConfig.KV_URL)
//    }
//
//    fun removeMobileApi() {
//        cacheSource.remove(KV_MOBILE_API)
//    }
//
//    fun saveFreePremium(isFreePremium: Boolean) {
//        cacheSource.putBoolean(KV_FREE_PREMIUM, isFreePremium)
//    }
//
//    fun getFreePremium(): Boolean {
//        return BuildConfig.APPLICATION_ID.endsWith("free")
//    }
//
//    fun removeFreePremium() {
//        cacheSource.remove(KV_FREE_PREMIUM)
//    }
//
//
//    fun saveJwt(jwt: String) {
//        cacheSource.putString(KV_JWT, jwt)
//    }
//
//    fun getJwt(): String {
//        return cacheSource.getString(KV_JWT, "")
//    }
//
//    fun setPrinterEnable(isEnable: Boolean) {
//        return cacheSource.putBoolean(KV_PRINTER_ENABLE, isEnable)
//    }
//
//    fun isPrinterEnable(): Boolean {
//        return cacheSource.getBoolean(KV_PRINTER_ENABLE, false)
//    }
//
//    fun isCameraPermissionRequested(): Boolean {
//        return cacheSource.getBoolean(KV_REQUESTED_CAMERA_PERMISSION, false)
//    }
//
//    fun isStoragePermissionRequested(): Boolean {
//        return cacheSource.getBoolean(KV_REQUESTED_STORAGE_PERMISSION, false)
//    }
//
//    fun setCameraPermissionRequested() {
//        return cacheSource.putBoolean(KV_REQUESTED_CAMERA_PERMISSION, true)
//    }
//
//    fun setStoragePermissionRequested() {
//        return cacheSource.putBoolean(KV_REQUESTED_STORAGE_PERMISSION, true)
//    }
//
//    fun getPrinters(): List<Printer> {
//        val data = cacheSource.getString(KV_PRINTERS, "")
//        return if (data.isNullOrEmpty()) {
//            emptyList()
//        } else {
//            Gson().fromJson(data, Array<Printer>::class.java).toList()
//        }
//
//    }
//
//    fun savePopup(popups: List<KTPopup>) {
//        val gson = Gson()
//        val data = gson.toJson(popups)
//        cacheSource.putString(KV_POPUPS, data)
//    }
//
//    fun getPopup(): List<KTPopup> {
//        val data = cacheSource.getString(KV_POPUPS, "")
//        return if (data.isEmpty()) {
//            emptyList()
//        } else {
//            Gson().fromJson(data, Array<KTPopup>::class.java).toList()
//        }
//    }
//
//    fun addPrinter(printer: Printer) {
//        var data = cacheSource.getString(KV_PRINTERS, "")
//        val gson = Gson()
//        val printers = if (data.isNullOrEmpty()) mutableListOf() else gson.fromJson(
//            data,
//            Array<Printer>::class.java
//        ).toMutableList()
//        printers.add(0, printer)
//        data = gson.toJson(printers)
//        cacheSource.putString(KV_PRINTERS, data)
//    }
//
//    fun removePrinter(printer: Printer): Boolean {
//        var data = cacheSource.getString(KV_PRINTERS, "")
//        val gson = Gson()
//        val printers = gson.fromJson(data, Array<Printer>::class.java).toMutableList()
//        return if (printers.contains(printer)) {
//            printers.remove(printer)
//            data = gson.toJson(printers)
//            cacheSource.putString(KV_PRINTERS, data)
//            true
//        } else {
//            false
//        }
//    }
//
//    fun getCurrentPrinterId(): String {
//        return cacheSource.getString(KV_CURRENT_PRINTER, "")
//    }
//
//    fun setCurrentPrinter(printerId: String) {
//        return cacheSource.putString(KV_CURRENT_PRINTER, printerId)
//    }
//
//    fun setDeviceToken(token: String) {
//        return cacheSource.putString(FIREBASE_DEVICE_TOKEN, token)
//    }
//
//    fun getDeviceToken(): String {
//        return cacheSource.getString(FIREBASE_DEVICE_TOKEN, "")
//    }
//
//    fun getNotificationId(): Int {
//        var id = cacheSource.getInt(NOTIFICATION_ID, 1)
//        if (id != Int.MAX_VALUE) {
//            id++
//        } else {
//            id = 1
//        }
//        cacheSource.putInt(NOTIFICATION_ID, id)
//        return id
//    }
//
//    fun resetLastKvSyncTime() {
//        return cacheSource.putLong(LAST_KV_SYNC_TIME, -1L)
//    }
//
//    fun getLastKvSyncTime(): Long {
//        return cacheSource.getLong(LAST_KV_SYNC_TIME, -1)
//    }
//
//    fun setLastKvSyncTime() {
//        return cacheSource.putLong(LAST_KV_SYNC_TIME, System.currentTimeMillis())
//    }
//
//    fun isOnboardingShown() = cacheSource.getBoolean(KV_ONBOARDING_SHOWN, false)
//
//    fun setOnboardingShown() = cacheSource.putBoolean(KV_ONBOARDING_SHOWN, true)
//
//    fun isShowcaseCreateInvoceShown() = cacheSource.getBoolean(SHOWCASE_CREATE_INVOICE_SHOWN, false)
//
//    fun setShowcaseCreateInvoceShown() = cacheSource.putBoolean(SHOWCASE_CREATE_INVOICE_SHOWN, true)
//
//    fun isShowcaseSelectProductShown() =
//        cacheSource.getBoolean(SHOWCASE_SELECT_PRODUCT_SHOWN, false)
//
//    fun setShowcaseSelectProductShown() =
//        cacheSource.putBoolean(SHOWCASE_SELECT_PRODUCT_SHOWN, true)
//
//    fun isShowcaseCreateStockForInvoceShown() = cacheSource.getBoolean(
//        SHOWCASE_CREATE_STOCK_FOR_INVOICE_SHOWN, false
//    )
//
//    fun setShowcaseCreateStockForInvoceShown() =
//        cacheSource.putBoolean(SHOWCASE_CREATE_STOCK_FOR_INVOICE_SHOWN, true)
//
//    fun isShowcaseCreateInvoiceStep1Shown() = cacheSource.getBoolean(
//        SHOWCASE_CREATE_INVOICE_STEP_1_SHOWN, false
//    )
//
//    fun setShowcaseCreateInvoiceStep1Shown() =
//        cacheSource.putBoolean(SHOWCASE_CREATE_INVOICE_STEP_1_SHOWN, true)
//
//    fun isShowcaseCreateInvoiceStep2Shown() = cacheSource.getBoolean(
//        SHOWCASE_CREATE_INVOICE_STEP_2_SHOWN, false
//    )
//
//    fun setShowcaseCreateInvoiceStep2Shown() =
//        cacheSource.putBoolean(SHOWCASE_CREATE_INVOICE_STEP_2_SHOWN, true)
//
//    fun isShowcaseCashFlowStep1Shown() = cacheSource.getBoolean(
//        SHOWCASE_CREATE_CASH_FLOW_STEP_1_SHOWN, false
//    )
//
//    fun setShowcaseCashFlowStep1Shown() =
//        cacheSource.putBoolean(SHOWCASE_CREATE_CASH_FLOW_STEP_1_SHOWN, true)
//
//    fun isShowcaseCashFlowStep2Shown() = cacheSource.getBoolean(
//        SHOWCASE_CREATE_CASH_FLOW_STEP_2_SHOWN, false
//    )
//
//    fun setShowcaseCashFlowStep2Shown() =
//        cacheSource.putBoolean(SHOWCASE_CREATE_CASH_FLOW_STEP_2_SHOWN, true)
//
//    fun isShowcaseEcommerceShown() = cacheSource.getBoolean(
//        SHOWCASE_ECOMMERCE_SHOWN, false
//    )
//
//    fun setShowcaseEcommerceShown() =
//        cacheSource.putBoolean(SHOWCASE_ECOMMERCE_SHOWN, true)
//
//    fun clearCache() {
//        cacheSource.remove(KV_BEARER)
//        cacheSource.remove(KV_JWT)
//        cacheSource.remove(KV_BRANCH_ID)
//        cacheSource.remove(KV_FB_TOKEN)
//        cacheSource.remove(FIREBASE_DEVICE_TOKEN)
//        cacheSource.remove(OMNI_ORDER_DELIVERY_TYPE)
//        cacheSource.remove(OMNI_ORDER_PROCESS_FILTER)
//        cacheSource.remove(KV_PAYMENT_METHOD)
//        cacheSource.remove(KV_REGISTER_BEARER)
//        cacheSource.remove(KV_MOBILE_API)
//        cacheSource.remove(KV_FREE_PREMIUM)
//        cacheSource.remove(OMNI_ORDER_RETURN_FILTER)
//        /*cacheSource.remove(KV_CASH_FLOW_DATE_FILTER)
//        cacheSource.remove(KV_CASH_FLOW_DATE_FILTER_OPTIONAL_START_DATE)
//        cacheSource.remove(KV_CASH_FLOW_DATE_FILTER_OPTIONAL_END_DATE)*/
//        cacheSource.remove(KV_PRINT_PREVIEW)
//        cacheSource.remove(KV_SELL_OUT_OF_STOCK)
//        cacheSource.remove(KV_ORDER_OUT_OF_STOCK)
//        cacheSource.remove(KV_CURRENT_REATAIL)
//        cacheSource.remove(KV_GET_KEY_SETTINGS)
//        cacheSource.remove(KV_PRINT_WHEN_CREATE_INVOICE)
//        cacheSource.remove(KV_PRINTER_ENABLE)
//        cacheSource.remove(KV_GET_CURRENT_INDUSTRY)
//        cacheSource.remove(KV_CURRENT_USER)
//        cacheSource.remove(KSHIP_SETTING)
//        cacheSource.remove(USER_ONBOARDING_CONFIG)
//    }
//
//    fun getExtendDeliveryCod() = cacheSource.getInt(EXTEND_DELIVERY_COD_INVOICE, 0)
//
//    fun setPrintWhenCreateInvoice(isPrintCreateInvoice: Boolean) =
//        cacheSource.putBoolean(KV_PRINT_WHEN_CREATE_INVOICE, isPrintCreateInvoice)
//
//    fun isPrintWhenCreateInvoice(): Boolean =
//        cacheSource.getBoolean(KV_PRINT_WHEN_CREATE_INVOICE, true)
//
//    fun setPrintPreview(isPrintPreview: Boolean) =
//        cacheSource.putBoolean(KV_PRINT_PREVIEW, isPrintPreview)
//
//    fun isPrintPreview(): Boolean = cacheSource.getBoolean(KV_PRINT_PREVIEW, true)
//
//    fun setPrintCountInvoice(printCount: Int) =
//        cacheSource.putInt(KV_PRINT_COUNT_INVOICE, printCount)
//
//    fun getPrintCountInvoice(): Int = cacheSource.getInt(KV_PRINT_COUNT_INVOICE, 1)
//
//    fun saveCurrentUser(user: User) {
//        cacheSource.putString(KV_CURRENT_USER, Gson().toJson(user))
//    }
//
//    fun getCurrentUser(): User? {
//        val json = cacheSource.getString(KV_CURRENT_USER, "")
//        return if (json.isEmpty()) {
//            null
//        } else Gson().fromJson(json, User::class.java)
//    }
//
//    fun saveRestoreId(restoreId: String) {
//        Timber.d("QuangND-Fresh saveRestoreId: $restoreId")
//        cacheSource.putString(KV_RESTORE_ID, restoreId)
//    }
//
//    fun getRestoreId(): String {
//        return cacheSource.getString(KV_RESTORE_ID, "").also {
//            Timber.d("QuangND-Fresh getRestoreId: $it")
//        }
//    }
//
//    fun saveBankAccounts(accounts: List<ApiBankAccount>) =
//        cacheSource.putString(KV_BANK_ACCOUNTS, Gson().toJson(accounts))
//
//    fun getBankAccounts(): List<ApiBankAccount> {
//        val data = cacheSource.getString(KV_BANK_ACCOUNTS, "")
//        if (data.isEmpty()) {
//            return emptyList()
//        }
//        val listType: Type = object : TypeToken<ArrayList<ApiBankAccount>>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    fun saveListBank(banks: List<ApiBank>) = cacheSource.putString(KV_BANKS, Gson().toJson(banks))
//
//    fun getListBank(): List<ApiBank> {
//        val data = cacheSource.getString(KV_BANKS, "")
//        if (data.isEmpty()) {
//            return emptyList()
//        }
//        val listType: Type = object : TypeToken<ArrayList<ApiBank>>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    fun saveCurrentRetail(retail: CurrentRetail) =
//        cacheSource.putString(KV_CURRENT_REATAIL, Gson().toJson(retail))
//
//    fun getCurrentRetail(): CurrentRetail? {
//        val data = cacheSource.getString(KV_CURRENT_REATAIL, "")
//        if (data.isEmpty()) {
//            return null
//        }
//        val listType: Type = object : TypeToken<CurrentRetail>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    fun isOrderOutOfStock(): Boolean = cacheSource.getBoolean(KV_ORDER_OUT_OF_STOCK, true)
//
//    fun isSellOutOfStock(): Boolean = cacheSource.getBoolean(KV_SELL_OUT_OF_STOCK, true)
//
//    fun saveOrderOutOfStock(isSetting: Boolean) {
//        cacheSource.putBoolean(KV_ORDER_OUT_OF_STOCK, isSetting)
//    }
//
//    fun saveSellOutOfStock(isSetting: Boolean) {
//        cacheSource.putBoolean(KV_SELL_OUT_OF_STOCK, isSetting)
//    }
//
//    fun isGetKeysSetting(): Boolean = cacheSource.getBoolean(KV_GET_KEY_SETTINGS, false)
//
//    fun saveGetKeysSetting(isGetSettings: Boolean) {
//        cacheSource.putBoolean(KV_GET_KEY_SETTINGS, isGetSettings)
//    }
//
//    fun setPrintWhenCreateOrder(isPrintCreateInvoice: Boolean) =
//        cacheSource.putBoolean(KV_PRINT_WHEN_CREATE_ORDER, isPrintCreateInvoice)
//
//    fun isPrintWhenCreateOrder(): Boolean =
//        cacheSource.getBoolean(KV_PRINT_WHEN_CREATE_ORDER, true)
//
//    fun setPrintCountOrder(printCount: Int) =
//        cacheSource.putInt(KV_PRINT_COUNT_ORDER, printCount)
//
//    fun getPrintCountOrder(): Int = cacheSource.getInt(KV_PRINT_COUNT_ORDER, 1)
//
//    fun saveCurrentIndustry(industry: ProductIndustrySuggest) =
//        cacheSource.putString(KV_GET_CURRENT_INDUSTRY, Gson().toJson(industry))
//
//    fun getCurrentIndustry(): ProductIndustrySuggest? {
//        val data = cacheSource.getString(KV_GET_CURRENT_INDUSTRY, "")
//        if (data.isEmpty()) {
//            return null
//        }
//        val listType: Type = object : TypeToken<ProductIndustrySuggest>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    fun saveKshipSetting(setting: KeySetting) =
//        cacheSource.putString(KSHIP_SETTING, Gson().toJson(setting))
//
//    fun getKshipSetting(): KeySetting? {
//        val data = cacheSource.getString(KSHIP_SETTING, "")
//        if (data.isEmpty()) {
//            return null
//        }
//        val listType: Type = object : TypeToken<KeySetting>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    fun saveAllKeySettings(setting: List<KeySetting>) =
//        cacheSource.putString(ALL_KEY_SETTINGS, Gson().toJson(setting))
//
//    fun getAllKeySettings(): List<KeySetting>? {
//        val data = cacheSource.getString(ALL_KEY_SETTINGS, "")
//        if (data.isEmpty()) {
//            return null
//        }
//        val listType: Type = object : TypeToken<ArrayList<KeySetting>>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    fun isScanBarCodeOrderInvoice(): Boolean =
//        cacheSource.getBoolean(SCAN_BARCODE_ORDER_INVOICE, true)
//
//    fun saveScanBarCodeOrderInvoice(isScan: Boolean) {
//        cacheSource.putBoolean(SCAN_BARCODE_ORDER_INVOICE, isScan)
//    }
//
//    fun isAddProductsOrderInvoice(): Boolean =
//        cacheSource.getBoolean(ADD_PRODUCTS_ORDER_INVOICE_BARCODE, false)
//
//    fun saveAddProductsOrderInvoice(isScan: Boolean) {
//        cacheSource.putBoolean(ADD_PRODUCTS_ORDER_INVOICE_BARCODE, isScan)
//    }
//
    fun saveLoginResponse(response: LoginResonse) =
        cacheSource.putString(LOGIN_RESPONSE, Gson().toJson(response))

    fun getUserOnboardingConfig(): LoginResonse? {
        val data = cacheSource.getString(LOGIN_RESPONSE, "")
        if (data.isEmpty()) {
            return null
        }
        val listType: Type = object : TypeToken<LoginResonse>() {}.type
        return Gson().fromJson(data, listType)
    }


}