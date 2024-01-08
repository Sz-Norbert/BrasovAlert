package com.nika.brasovalert.di

import android.content.Context
import androidx.room.Room
import com.nika.brasovalert.api.AlertsApi
import com.nika.brasovalert.db.UserDao
import com.nika.brasovalert.db.UserDataBase
import com.nika.brasovalert.repoitory.Repositroy
import com.nika.brasovalert.util.Util.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun  provideApi():AlertsApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(AlertsApi::class.java)
    }
    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context : Context)=Room.databaseBuilder(
        context,UserDataBase::class.java, "user.db").build()


    @Provides
    @Singleton
    fun providePlayerDao(database:UserDataBase)=database.userDao()

    @Singleton
    @Provides
    fun provideRepository(api : AlertsApi, userDao: UserDao)=Repositroy(api,userDao)

}