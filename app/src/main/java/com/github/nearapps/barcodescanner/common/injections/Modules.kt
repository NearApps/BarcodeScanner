package com.github.nearapps.barcodescanner.common.injections

import android.content.ClipboardManager
import android.content.Context
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import com.github.nearapps.barcodescanner.R
import com.github.nearapps.barcodescanner.common.utils.KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT
import com.github.nearapps.barcodescanner.common.utils.KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_STRING
import com.github.nearapps.barcodescanner.data.api.CoverArtArchiveService
import com.github.nearapps.barcodescanner.data.api.MusicBrainzService
import com.github.nearapps.barcodescanner.data.api.OpenBeautyFactsService
import com.github.nearapps.barcodescanner.data.api.OpenFoodFactsService
import com.github.nearapps.barcodescanner.data.api.OpenLibraryService
import com.github.nearapps.barcodescanner.data.api.OpenPetFoodFactsService
import com.github.nearapps.barcodescanner.data.database.AppDatabase
import com.github.nearapps.barcodescanner.data.database.BankDao
import com.github.nearapps.barcodescanner.data.database.BarcodeDao
import com.github.nearapps.barcodescanner.data.database.createBankDao
import com.github.nearapps.barcodescanner.data.database.createBarcodeDao
import com.github.nearapps.barcodescanner.data.database.createDatabase
import com.github.nearapps.barcodescanner.data.file.FileFetcher
import com.github.nearapps.barcodescanner.data.file.FileStream
import com.github.nearapps.barcodescanner.data.file.image.BarcodeBitmapGenerator
import com.github.nearapps.barcodescanner.data.file.image.BarcodeSvgGenerator
import com.github.nearapps.barcodescanner.data.file.image.BitmapSharer
import com.github.nearapps.barcodescanner.data.network.createApiClient
import com.github.nearapps.barcodescanner.data.repositories.AdditiveClassRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.AdditiveResponseRepository
import com.github.nearapps.barcodescanner.data.repositories.AdditivesRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.AllergensRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.BankRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.BarcodeRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.BeautyProductRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.BookProductRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.CountriesRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.FileStreamRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.FoodProductRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.ImageExportRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.ImageGeneratorRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.InstalledAppsRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.LabelsRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.MusicProductRepositoryImpl
import com.github.nearapps.barcodescanner.data.repositories.PetFoodProductRepositoryImpl
import com.github.nearapps.barcodescanner.domain.entity.barcode.Barcode
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeFormatDetails
import com.github.nearapps.barcodescanner.domain.entity.barcode.BarcodeType
import com.github.nearapps.barcodescanner.domain.entity.barcode.QrCodeErrorCorrectionLevel
import com.github.nearapps.barcodescanner.domain.library.BarcodeBitmapAnalyser
import com.github.nearapps.barcodescanner.domain.library.BarcodeFormatChecker
import com.github.nearapps.barcodescanner.domain.library.BeepManager
import com.github.nearapps.barcodescanner.domain.library.DateConverter
import com.github.nearapps.barcodescanner.domain.library.Iban
import com.github.nearapps.barcodescanner.domain.library.InternetChecker
import com.github.nearapps.barcodescanner.domain.library.SettingsManager
import com.github.nearapps.barcodescanner.domain.library.VCardReader
import com.github.nearapps.barcodescanner.domain.library.VibratorAppCompat
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.configuration.WifiSetupWithNewLibrary
import com.github.nearapps.barcodescanner.domain.library.wifiSetup.configuration.WifiSetupWithOldLibrary
import com.github.nearapps.barcodescanner.domain.repositories.AdditiveClassRepository
import com.github.nearapps.barcodescanner.domain.repositories.AdditivesRepository
import com.github.nearapps.barcodescanner.domain.repositories.AllergensRepository
import com.github.nearapps.barcodescanner.domain.repositories.BankRepository
import com.github.nearapps.barcodescanner.domain.repositories.BarcodeRepository
import com.github.nearapps.barcodescanner.domain.repositories.BeautyProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.BookProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.CountriesRepository
import com.github.nearapps.barcodescanner.domain.repositories.FileStreamRepository
import com.github.nearapps.barcodescanner.domain.repositories.FoodProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.ImageExportRepository
import com.github.nearapps.barcodescanner.domain.repositories.ImageGeneratorRepository
import com.github.nearapps.barcodescanner.domain.repositories.InstalledAppsRepository
import com.github.nearapps.barcodescanner.domain.repositories.LabelsRepository
import com.github.nearapps.barcodescanner.domain.repositories.MusicProductRepository
import com.github.nearapps.barcodescanner.domain.repositories.PetFoodProductRepository
import com.github.nearapps.barcodescanner.domain.usecases.DatabaseBankUseCase
import com.github.nearapps.barcodescanner.domain.usecases.DatabaseBarcodeUseCase
import com.github.nearapps.barcodescanner.domain.usecases.ExternalFoodProductDependencyUseCase
import com.github.nearapps.barcodescanner.domain.usecases.ImageManagerUseCase
import com.github.nearapps.barcodescanner.domain.usecases.ProductUseCase
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBankViewModel
import com.github.nearapps.barcodescanner.presentation.viewmodel.DatabaseBarcodeViewModel
import com.github.nearapps.barcodescanner.presentation.viewmodel.ExternalFileViewModel
import com.github.nearapps.barcodescanner.presentation.viewmodel.ImageManagerViewModel
import com.github.nearapps.barcodescanner.presentation.viewmodel.InstalledAppsViewModel
import com.github.nearapps.barcodescanner.presentation.viewmodel.ProductViewModel
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.AbstractActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.AgendaActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.BarcodeContentsModifierModalBottomSheetFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.BeautyActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.BookActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.ContactActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.DefaultActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.EmailActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.FoodActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.LocalizationActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.MusicActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.PetFoodActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.PhoneActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.ProductActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.SmsActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.UrlActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeAnalysis.actions.WifiActionsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.AbstractBarcodeFormCreatorFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorAztecFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorCodabarFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorCode128Fragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorCode39Fragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorCode93Fragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorDataMatrixFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorEAN13Fragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorEAN8Fragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorITFFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorPDF417Fragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrAgendaFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrApplicationFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrContactFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrEpcFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrLocalisationFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrMailFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrPhoneFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrSmsFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrTextFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrUrlFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorQrWifiFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorUPCAFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.barcodeFormCreator.BarcodeFormCreatorUPCEFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainBarcodeCreatorListFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainBarcodeHistoryFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainCameraXScannerFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainScannerFragment
import com.github.nearapps.barcodescanner.presentation.views.fragments.main.MainSettingsFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.Result
import com.google.zxing.ResultMetadataType
import com.google.zxing.client.result.ParsedResult
import com.google.zxing.client.result.ParsedResultType
import com.google.zxing.client.result.ResultParser
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.reflect.KClass

val appModules by lazy {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf<Module>(
            androidModule,
            libraryModule,
            libraryApi29Module,
            viewModelModule,
            useCaseModule,
            repositoryModule,
            dataModule,
            fragmentsModule
        )
    } else {
        listOf<Module>(
            androidModule,
            libraryModule,
            viewModelModule,
            useCaseModule,
            repositoryModule,
            dataModule,
            fragmentsModule
        )
    }
}

val androidModule: Module = module {
    single<BeepManager> { BeepManager() }
    single<VibratorAppCompat> { VibratorAppCompat(androidApplication().applicationContext) }
    single<ConnectivityManager> { androidApplication().applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
    single<ClipboardManager> { androidApplication().applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager }
    single<InputMethodManager> { androidApplication().applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager }
    single<WifiManager> { androidApplication().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager }
    single<LocationManager> { androidApplication().applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager }

    factory<Barcode> { (contents: String, formatName: String, qrErrorCorrectionLevel: QrCodeErrorCorrectionLevel) ->
        Barcode(
            contents = contents,
            formatName = formatName,
            scanDate = System.currentTimeMillis(),
            errorCorrectionLevel = qrErrorCorrectionLevel.name
        ).apply {
            type = get<BarcodeType> {
                parametersOf(this)
            }.name
        }
    }

    factory { (contents: String, format: BarcodeFormat) ->
        val result = Result(contents, null, null, format)
        ResultParser.parseResult(result)
    }

    factory<BarcodeType> { (barcode: Barcode) ->
        when {
            barcode.is1DProductBarcodeFormat -> {
                if(barcode.isBookBarcode()) BarcodeType.BOOK else BarcodeType.UNKNOWN_PRODUCT
            }
            barcode.is1DIndustrialBarcodeFormat -> BarcodeType.INDUSTRIAL
            else -> {
                val parsedResult: ParsedResult = get {
                    parametersOf(barcode.contents, barcode.getBarcodeFormat())
                }
                when(parsedResult.type){
                    ParsedResultType.ADDRESSBOOK -> BarcodeType.CONTACT
                    ParsedResultType.EMAIL_ADDRESS -> BarcodeType.MAIL
                    ParsedResultType.PRODUCT -> BarcodeType.UNKNOWN_PRODUCT
                    ParsedResultType.URI -> BarcodeType.URL
                    ParsedResultType.TEXT -> BarcodeType.TEXT
                    ParsedResultType.GEO -> BarcodeType.LOCALISATION
                    ParsedResultType.TEL -> BarcodeType.PHONE
                    ParsedResultType.SMS -> BarcodeType.SMS
                    ParsedResultType.CALENDAR -> BarcodeType.AGENDA
                    ParsedResultType.WIFI -> BarcodeType.WIFI
                    ParsedResultType.ISBN -> BarcodeType.BOOK
                    ParsedResultType.VIN -> BarcodeType.TEXT
                    else -> BarcodeType.UNKNOWN
                }
            }
        }
    }

    factory<QrCodeErrorCorrectionLevel>(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_STRING)) { (errorCorrectionLevel: String?) ->
        when(errorCorrectionLevel){
            "L" -> QrCodeErrorCorrectionLevel.L
            "M" -> QrCodeErrorCorrectionLevel.M
            "Q" -> QrCodeErrorCorrectionLevel.Q
            "H" -> QrCodeErrorCorrectionLevel.H
            else -> QrCodeErrorCorrectionLevel.NONE
        }
    }

    factory<QrCodeErrorCorrectionLevel>(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_RESULT)) { (result: Result) ->
        var errorCorrectionLevel: QrCodeErrorCorrectionLevel = QrCodeErrorCorrectionLevel.NONE
        result.resultMetadata?.let { metadata ->
            val errorCorrectionLevelStr = metadata[ResultMetadataType.ERROR_CORRECTION_LEVEL] as? String
            errorCorrectionLevel = get(named(KOIN_NAMED_ERROR_CORRECTION_LEVEL_BY_STRING)) {
                parametersOf(errorCorrectionLevelStr)
            }
        }
        errorCorrectionLevel
    }

    factory { Bundle() }
}

val libraryModule: Module = module {
    single<SettingsManager> { SettingsManager(androidContext()) }
    single<BarcodeBitmapAnalyser>{ BarcodeBitmapAnalyser() }
    single<BarcodeFormatChecker> { BarcodeFormatChecker(androidContext()) }
    single<VCardReader> { VCardReader(androidContext()) }
    single<WifiSetupWithOldLibrary> { WifiSetupWithOldLibrary() }
    single<Iban> { Iban() }
    single<InternetChecker> { InternetChecker() }
    single<DateConverter> { DateConverter() }

    factory { Date() }
    factory { (pattern: String) -> SimpleDateFormat(pattern, Locale.getDefault()) }
}

@RequiresApi(Build.VERSION_CODES.Q)
val libraryApi29Module: Module = module {
    single<WifiSetupWithNewLibrary> { WifiSetupWithNewLibrary() }
}

val viewModelModule: Module = module {
    viewModel {
        ProductViewModel(get<ProductUseCase>())
    }

    viewModel {
        DatabaseBankViewModel(get<DatabaseBankUseCase>())
    }

    viewModel {
        DatabaseBarcodeViewModel(get<DatabaseBarcodeUseCase>())
    }

    viewModel {
        ExternalFileViewModel(get<ExternalFoodProductDependencyUseCase>())
    }

    viewModel {
        InstalledAppsViewModel(get<InstalledAppsRepository>())
    }

    viewModel {
        ImageManagerViewModel(get<ImageManagerUseCase>())
    }
}

val useCaseModule: Module = module {
    single<ProductUseCase> {
        ProductUseCase(
            foodProductRepository = get<FoodProductRepository>(),
            beautyProductRepository = get<BeautyProductRepository>(),
            petFoodProductRepository = get<PetFoodProductRepository>(),
            musicProductRepository = get<MusicProductRepository>(),
            bookProductRepository = get<BookProductRepository>()
        )
    }

    single<DatabaseBankUseCase> {
        DatabaseBankUseCase(get<BankRepository>())
    }

    single<DatabaseBarcodeUseCase> {
        DatabaseBarcodeUseCase(get<BarcodeRepository>(), get<FileStreamRepository>())
    }

    single<ExternalFoodProductDependencyUseCase> {
        ExternalFoodProductDependencyUseCase(
            labelsRepository = get<LabelsRepository>(),
            additivesRepository = get<AdditivesRepository>(),
            allergensRepository = get<AllergensRepository>(),
            countriesRepository = get<CountriesRepository>()
        )
    }

    single { ImageManagerUseCase(get<ImageGeneratorRepository>(), get<ImageExportRepository>()) }
}

val repositoryModule: Module = module {

    single<FoodProductRepository> {
        FoodProductRepositoryImpl(get<OpenFoodFactsService>())
    }

    single<BeautyProductRepository> {
        BeautyProductRepositoryImpl(get<OpenBeautyFactsService>())
    }

    single<PetFoodProductRepository> {
        PetFoodProductRepositoryImpl(get<OpenPetFoodFactsService>())
    }

    single<MusicProductRepository> {
        MusicProductRepositoryImpl(get<MusicBrainzService>(), get<CoverArtArchiveService>())
    }

    single<BookProductRepository> {
        BookProductRepositoryImpl(get<OpenLibraryService>())
    }

    single<BankRepository> {
        BankRepositoryImpl(get<BankDao>())
    }

    single<BarcodeRepository> {
        BarcodeRepositoryImpl(get<BarcodeDao>())
    }

    single<LabelsRepository> {
        LabelsRepositoryImpl(get<FileFetcher>())
    }

    single<AdditiveResponseRepository> {
        AdditiveResponseRepository(get<FileFetcher>())
    }

    single<AdditiveClassRepository> {
        AdditiveClassRepositoryImpl(androidContext(), get<FileFetcher>())
    }

    single<AdditivesRepository> {
        AdditivesRepositoryImpl(get<AdditiveResponseRepository>(), get<AdditiveClassRepository>())
    }

    single<AllergensRepository> {
        AllergensRepositoryImpl(get<FileFetcher>())
    }

    single<CountriesRepository> {
        CountriesRepositoryImpl(get<FileFetcher>())
    }

    single<InstalledAppsRepository> {
        InstalledAppsRepositoryImpl(androidContext())
    }

    single<ImageGeneratorRepository> {
        ImageGeneratorRepositoryImpl(get<BarcodeBitmapGenerator>(), get<BarcodeSvgGenerator>())
    }

    single<ImageExportRepository> {
        ImageExportRepositoryImpl(get<FileStream>(), get<BitmapSharer>())
    }

    single<FileStreamRepository> { FileStreamRepositoryImpl(get<FileStream>()) }
}

val dataModule: Module = module {

    single<OpenFoodFactsService> {
        val baseUrl = androidContext().getString(R.string.base_api_open_food_facts_url)
        createApiClient(androidContext(), baseUrl).create(OpenFoodFactsService::class.java)
    }

    single<OpenBeautyFactsService> {
        val baseUrl = androidContext().getString(R.string.base_api_open_beauty_facts_url)
        createApiClient(androidContext(), baseUrl).create(OpenBeautyFactsService::class.java)
    }

    single<OpenPetFoodFactsService> {
        val baseUrl = androidContext().getString(R.string.base_api_open_pet_food_facts_url)
        createApiClient(androidContext(), baseUrl).create(OpenPetFoodFactsService::class.java)
    }

    single<MusicBrainzService> {
        val baseUrl = androidContext().getString(R.string.base_api_musicbrainz_url)
        createApiClient(androidContext(), baseUrl).create(MusicBrainzService::class.java)
    }

    single<CoverArtArchiveService> {
        val baseUrl = androidContext().getString(R.string.base_api_cover_art_archive_url)
        createApiClient(androidContext(), baseUrl).create(CoverArtArchiveService::class.java)
    }

    single<OpenLibraryService> {
        val baseUrl = androidContext().getString(R.string.base_api_open_library_url)
        createApiClient(androidContext(), baseUrl).create(OpenLibraryService::class.java)
    }

    single<AppDatabase> {
        createDatabase(androidContext())
    }

    single<BarcodeDao> {
        createBarcodeDao(get<AppDatabase>())
    }

    single<BankDao> {
        createBankDao(get<AppDatabase>())
    }

    single<FileFetcher> { FileFetcher(androidContext()) }

    single<MultiFormatWriter> { MultiFormatWriter() }
    single<BarcodeBitmapGenerator> { BarcodeBitmapGenerator(get<MultiFormatWriter>()) }
    single<BarcodeSvgGenerator> { BarcodeSvgGenerator(get<MultiFormatWriter>()) }

    single { BitmapSharer(androidContext()) }

    single { FileStream(androidContext()) }
}

val fragmentsModule = module {

    factory { MainCameraXScannerFragment() }
    factory { MainScannerFragment() }
    factory { MainBarcodeHistoryFragment() }
    factory { MainBarcodeCreatorListFragment() }
    factory { MainSettingsFragment() }

    factory { BarcodeFormCreatorAztecFragment() }
    factory { BarcodeFormCreatorCodabarFragment() }
    factory { BarcodeFormCreatorCode39Fragment() }
    factory { BarcodeFormCreatorCode93Fragment() }
    factory { BarcodeFormCreatorCode128Fragment() }
    factory { BarcodeFormCreatorDataMatrixFragment() }
    factory { BarcodeFormCreatorEAN8Fragment() }
    factory { BarcodeFormCreatorEAN13Fragment() }
    factory { BarcodeFormCreatorITFFragment() }
    factory { BarcodeFormCreatorPDF417Fragment() }
    factory { BarcodeFormCreatorQrAgendaFragment() }
    factory { BarcodeFormCreatorQrApplicationFragment() }
    factory { BarcodeFormCreatorQrContactFragment() }
    factory { BarcodeFormCreatorQrEpcFragment() }
    factory { BarcodeFormCreatorQrLocalisationFragment() }
    factory { BarcodeFormCreatorQrMailFragment() }
    factory { BarcodeFormCreatorQrPhoneFragment() }
    factory { BarcodeFormCreatorQrSmsFragment() }
    factory { BarcodeFormCreatorQrTextFragment() }
    factory { BarcodeFormCreatorQrUrlFragment() }
    factory { BarcodeFormCreatorQrWifiFragment() }
    factory { BarcodeFormCreatorUPCAFragment() }
    factory { BarcodeFormCreatorUPCEFragment() }

    factory<AbstractBarcodeFormCreatorFragment> { (barcodeFormatDetails: BarcodeFormatDetails) ->
        when(barcodeFormatDetails){
            BarcodeFormatDetails.AZTEC -> get<BarcodeFormCreatorAztecFragment>()
            BarcodeFormatDetails.CODABAR -> get<BarcodeFormCreatorCodabarFragment>()
            BarcodeFormatDetails.CODE_39 -> get<BarcodeFormCreatorCode39Fragment>()
            BarcodeFormatDetails.CODE_93 -> get<BarcodeFormCreatorCode93Fragment>()
            BarcodeFormatDetails.CODE_128 -> get<BarcodeFormCreatorCode128Fragment>()
            BarcodeFormatDetails.DATA_MATRIX -> get<BarcodeFormCreatorDataMatrixFragment>()
            BarcodeFormatDetails.EAN_8 -> get<BarcodeFormCreatorEAN8Fragment>()
            BarcodeFormatDetails.EAN_13 -> get<BarcodeFormCreatorEAN13Fragment>()
            BarcodeFormatDetails.ITF -> get<BarcodeFormCreatorITFFragment>()
            BarcodeFormatDetails.PDF_417 -> get<BarcodeFormCreatorPDF417Fragment>()
            BarcodeFormatDetails.QR_AGENDA -> get<BarcodeFormCreatorQrAgendaFragment>()
            BarcodeFormatDetails.QR_APPLICATION -> get<BarcodeFormCreatorQrApplicationFragment>()
            BarcodeFormatDetails.QR_CONTACT -> get<BarcodeFormCreatorQrContactFragment>()
            BarcodeFormatDetails.QR_EPC -> get<BarcodeFormCreatorQrEpcFragment>()
            BarcodeFormatDetails.QR_LOCALISATION -> get<BarcodeFormCreatorQrLocalisationFragment>()
            BarcodeFormatDetails.QR_MAIL -> get<BarcodeFormCreatorQrMailFragment>()
            BarcodeFormatDetails.QR_PHONE -> get<BarcodeFormCreatorQrPhoneFragment>()
            BarcodeFormatDetails.QR_SMS -> get<BarcodeFormCreatorQrSmsFragment>()
            BarcodeFormatDetails.QR_TEXT -> get<BarcodeFormCreatorQrTextFragment>()
            BarcodeFormatDetails.QR_URL -> get<BarcodeFormCreatorQrUrlFragment>()
            BarcodeFormatDetails.QR_WIFI -> get<BarcodeFormCreatorQrWifiFragment>()
            BarcodeFormatDetails.UPC_A -> get<BarcodeFormCreatorUPCAFragment>()
            BarcodeFormatDetails.UPC_E -> get<BarcodeFormCreatorUPCEFragment>()
        }
    }

    factory<KClass<out AbstractActionsFragment>> { (barcodeType: BarcodeType) ->
        when(barcodeType){
            BarcodeType.AGENDA -> AgendaActionsFragment::class
            BarcodeType.CONTACT -> ContactActionsFragment::class
            BarcodeType.LOCALISATION -> LocalizationActionsFragment::class
            BarcodeType.MAIL -> EmailActionsFragment::class
            BarcodeType.PHONE -> PhoneActionsFragment::class
            BarcodeType.SMS -> SmsActionsFragment::class
            BarcodeType.TEXT -> DefaultActionsFragment::class
            BarcodeType.URL -> UrlActionsFragment::class
            BarcodeType.WIFI -> WifiActionsFragment::class
            BarcodeType.FOOD -> FoodActionsFragment::class
            BarcodeType.PET_FOOD -> PetFoodActionsFragment::class
            BarcodeType.BEAUTY -> BeautyActionsFragment::class
            BarcodeType.MUSIC -> MusicActionsFragment::class
            BarcodeType.BOOK -> BookActionsFragment::class
            BarcodeType.INDUSTRIAL -> DefaultActionsFragment::class
            BarcodeType.MATRIX -> DefaultActionsFragment::class
            BarcodeType.UNKNOWN -> DefaultActionsFragment::class
            BarcodeType.UNKNOWN_PRODUCT -> ProductActionsFragment::class
        }
    }

    factory { (barcode: Barcode) ->
        BarcodeContentsModifierModalBottomSheetFragment.newInstance(barcode)
    }
}