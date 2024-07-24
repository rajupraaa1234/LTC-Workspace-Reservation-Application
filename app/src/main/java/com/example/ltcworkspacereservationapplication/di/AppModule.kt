package com.example.ltcworkspacereservationapplication.di

import com.example.ltcworkspacereservationapplication.data.Repository.DeskHistory.HistoryUseCaseImpl
import com.example.ltcworkspacereservationapplication.data.Repository.DeskReservation.DeskUseCaseImpl
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.DeskReservationRepository
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.HistoryRepository
import com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase.BookDeskUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase.DeskHistoryUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase.MeetingHistoryUseCase
import com.example.ltcworkspacereservationapplication.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.example.com/"

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDeskReservationRepository(apiService: ApiService): DeskReservationRepository {
        return DeskUseCaseImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideBookDeskUseCase(repository: DeskReservationRepository): BookDeskUseCase {
        return BookDeskUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideHistoryRepository(apiService: ApiService): HistoryRepository {
        return HistoryUseCaseImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideDeskHistoryUseCase(repository: HistoryRepository): DeskHistoryUseCase {
        return DeskHistoryUseCase(repository)
    }


    @Provides
    @Singleton
    fun provideMeetingHistoryUseCase(repository: HistoryRepository): MeetingHistoryUseCase {
        return MeetingHistoryUseCase(repository)
    }

}