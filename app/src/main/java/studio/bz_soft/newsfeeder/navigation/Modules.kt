package studio.bz_soft.newsfeeder.navigation

import android.content.Context
import kotlinx.coroutines.NonCancellable.start
import kotlinx.coroutines.channels.Channel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import studio.bz_soft.newsfeeder.data.Repository
import studio.bz_soft.newsfeeder.data.http.ApiClient
import studio.bz_soft.newsfeeder.root.App
import studio.bz_soft.newsfeeder.root.Constants
import studio.bz_soft.newsfeeder.root.MainRouter

val appModule = module {
    single { androidApplication() as App }
    factory { (name: String) -> get<App>().getSharedPreferences(name, Context.MODE_PRIVATE) }
    single { ApiClient(Constants.API_MAIN_URL, androidContext()) }
    single { Repository(get()) }
}

val navigatorModule = module {
    single { Cicerone.create() }
    single { get<Cicerone<Router>>().router as Router }
    single { get<Cicerone<Router>>().navigatorHolder as NavigatorHolder }
    single { MainRouter(get(), Channel(Channel.UNLIMITED)).apply { start() } }
}