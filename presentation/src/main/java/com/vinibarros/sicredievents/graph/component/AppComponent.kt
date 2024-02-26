package com.vinibarros.sicredievents.graph.component

import android.content.Context
import com.vinibarros.sicredievents.BaseApplication
import com.vinibarros.sicredievents.graph.module.ActivityBindingModule
import com.vinibarros.sicredievents.graph.module.ApiProviderModule
import com.vinibarros.sicredievents.graph.module.ApplicationBindingModule
import com.vinibarros.sicredievents.graph.module.ApplicationProviderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ApplicationBindingModule::class,
        ApplicationProviderModule::class,
        ApiProviderModule::class,
        ActivityBindingModule::class
    ]
)

interface AppComponent {

    fun inject(eventsApplication: BaseApplication): BaseApplication

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}