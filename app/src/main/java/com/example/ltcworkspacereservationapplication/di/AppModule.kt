package com.example.ltcworkspacereservationapplication.di

import com.example.ltcworkspacereservationapplication.data.Repository.History.HistoryUseCaseImpl
import com.example.ltcworkspacereservationapplication.data.Repository.DeskReservation.DeskUseCaseImpl
import com.example.ltcworkspacereservationapplication.data.Repository.DeskReservation.InstantDeskBookUseCseImpl
import com.example.ltcworkspacereservationapplication.data.Repository.History.MeetingHistoryUseCaseImpl
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.InstantBookingResponse
import com.example.ltcworkspacereservationapplication.data.Repository.MeetingReservation.MeetingUseCaseImpl
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.DeskReservationRepository
import com.example.ltcworkspacereservationapplication.domain.repository.DeskReservation.InstantDeskBookingRepository
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.HistoryRepository
import com.example.ltcworkspacereservationapplication.domain.repository.HistoryRepository.MeetingHistoryRepository
import com.example.ltcworkspacereservationapplication.domain.repository.MeetingRoomRepository.MeetingRoomReservationRepository
import com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase.BookDeskUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase.DeskHistoryUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase.MeetingHistoryUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.MeetingRoomReservationUseCase.GetMeetingListUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.MeetingRoomReservationUseCase.MeetingRoomReservationUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase.InstantDeskBookUseCase
import com.example.ltcworkspacereservationapplication.network.ApiService
import com.example.ltcworkspacereservationapplication.network.MeetingApiService
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

    private const val BASE_URL = "https://student-management-system1-li3krvyppa-uc.a.run.app/workspot/"

    private const val BASE_URL_MEETING = "https://meetingroombooking-0-0-1-li3krvyppa-uc.a.run.app/workspot/"

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
    fun provideMeetingApiService(): MeetingApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_MEETING)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MeetingApiService::class.java)
    }

    // Desk Room Related provider

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
    fun provideMeetingHistoryRepository(apiService: MeetingApiService): MeetingHistoryRepository {
        return MeetingHistoryUseCaseImpl(apiService)
    }


    @Provides
    @Singleton
    fun provideMeetingHistoryUseCase(repository: MeetingHistoryRepository): MeetingHistoryUseCase {
        return MeetingHistoryUseCase(repository)
    }


    // Meeting Room Related provider

    @Provides
    @Singleton
    fun provideMeetingRoomReservationRepository(apiService: MeetingApiService): MeetingRoomReservationRepository {
        return MeetingUseCaseImpl(apiService)
    }


    @Provides
    @Singleton
    fun provideMeetingRoomReservationUseCase(repository: MeetingRoomReservationRepository): MeetingRoomReservationUseCase {
        return MeetingRoomReservationUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideInstantDeskBookingRepository(apiService: ApiService):InstantDeskBookingRepository {
        return InstantDeskBookUseCseImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideInstantBookingUseCase(instantDeskBookingRepository: InstantDeskBookingRepository) : InstantDeskBookUseCase {
        return InstantDeskBookUseCase(instantDeskBookingRepository)
    }

    @Provides
    @Singleton
    fun provideGetMeetingListUseCase(repository: MeetingRoomReservationRepository): GetMeetingListUseCase {
        return GetMeetingListUseCase(repository)
    }

}