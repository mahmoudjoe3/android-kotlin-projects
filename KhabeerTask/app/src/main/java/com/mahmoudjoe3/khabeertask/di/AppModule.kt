package com.mahmoudjoe3.khabeertask.di


import android.content.Context
import com.mahmoudjoe3.khabeertask.data.DataStoreManager
import com.mahmoudjoe3.khabeertask.data.api.dao.LoginDao
import com.mahmoudjoe3.khabeertask.data.api.dao.PayrollDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule () {
    companion object {
        const val BASE_URL = "http://40.127.194.127:5656/Salamtak/"
    }

    @Provides
    @Singleton
    fun provideRetrofit(authToken: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(
                OkHttpClient.Builder().addInterceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .also { requestBuilder ->
                                if(authToken==null)
                                    requestBuilder.addHeader("Authorization", "Bearer VLiNsoSkokeHK1uUj2T4NuRKE0carrOQsFECTU2sBg6UEhDaxYxlvcXb6TrmN2cXRnjbm_Ojwv83LD4hvfECf2IlPN_4WrGNOx22mwvadyxfjLFmW0GEu9Vbi1xf8nYJMQp8Yl9hS2YsHF1vUa6KlWCel3Ux0WbZLt-AB9ZOuq8Mq71DckWbaVNSMb6TQIpepSdF545VqNl26YQkhleBfznvB-IB6rifbxKwj_zUxPQTv7hXk35mGoZqI6HA5qqWDHKLWgiQ1JKvixLcCErzUY0gPppUE6ejHqAeB2LNhHQ-1M6AvYRAQjhZ4rSeJ-ZMgCdZqBRBQkzBFn9F-1U_bBS1ELf1YuKNQxd6EvjPqOQfShVTMru7Upu9VdV2bl5gKjHaJTW8EAWzap0XkDgFUzKaxZF1LtEIwmQcrLgv9v0ak3t7e018Ns3LRqi3pvlQBJ7x_ec9ze-LukHi5HFUajRGwi0qu3QIg3vaPd7OrkL02FKQJJ7TYBmwN_G7_OAg")
                                requestBuilder.addHeader("Authorization", "Bearer $authToken")
                            }.build()
                    )
                }
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun providePayrollDao(retrofit: Retrofit): PayrollDao =
        retrofit.create(PayrollDao::class.java)

    @Provides
    @Singleton
    fun provideLoginDao(retrofit: Retrofit): LoginDao =
        retrofit.create(LoginDao::class.java)


    @Provides
    fun getAuthToken(dataStoreManager: DataStoreManager): String {
       return runBlocking {  dataStoreManager.authToken.first() }
    }


}