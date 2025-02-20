package com.example.ltcworkspacereservationapplication.presentation.mvvm

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.example.ltcworkspacereservationapplication.R
import com.example.ltcworkspacereservationapplication.domain.model.AvailabilityType
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.DeskReservationRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Request.InstantBookingRequest
import com.example.ltcworkspacereservationapplication.domain.model.DeskReservation.Response.DeskResponseItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingItemModel
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Request.BookMeetingRoomRequest
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.BookedRoomInfo
import com.example.ltcworkspacereservationapplication.domain.model.MeetingReservation.Response.ReservedTimeSlot
import com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase.BookDeskUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase.GetDeskListUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.DeskReservationUsecase.InstantDeskBookUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase.DeskHistoryUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.HistoryUseCase.MeetingHistoryUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.MeetingRoomReservationUseCase.GetMeetingListUseCase
import com.example.ltcworkspacereservationapplication.domain.usecase.MeetingRoomReservationUseCase.MeetingRoomReservationUseCase
import com.example.ltcworkspacereservationapplication.presentation.state.AppState
import com.example.ltcworkspacereservationapplication.presentation.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class ReservationViewModel @Inject constructor(
    private val getDeskListUseCase: GetDeskListUseCase,
    private val bookDeskUseCase: BookDeskUseCase,
    private val deskHistoryUseCase: DeskHistoryUseCase,
    private val meetingHistoryUseCase: MeetingHistoryUseCase,
    private val meetingRoomReservationUseCase: MeetingRoomReservationUseCase,
    private val getMeetingRoomListUseCase: GetMeetingListUseCase,
    private val instantDeskBookUseCase: InstantDeskBookUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(AppState())

    private val _effects = MutableSharedFlow<ReservationViewModelEffects>()


    //Toast Message State
    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()


    val uiState: StateFlow<AppState>
        get() = _uiState

    val effects: Flow<ReservationViewModelEffects>
        get() = _effects


    var isLoading = mutableStateOf(false)
        protected set

    val TAG = "ReservationViewModel"

    private val selectedFloor = mutableIntStateOf(-1)

    private var selectedDesk = mutableStateOf<DeskResponseItemModel?>(null)


    suspend fun sendIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.OnFilterButtonClicked -> onFilterButtonClicked()
            AppIntent.OnDeskBookButtonClicked -> onDeskBookButtonClicked()
            is AppIntent.OnDatePickerClick -> onDatePickerClicked(intent.dateSelected)
            is AppIntent.OnFloorSelect -> onFloorSelected(intent.selectedFloor)
            is AppIntent.OnMeetingItemClick -> OnMeetingItemClicked(intent.item, intent.index)
            is AppIntent.OnDeskItemClick -> onDeskItemClicked(intent.item, intent.index)
            is AppIntent.OnMeetingRoomBooking -> onMeetingBooking(
                intent.startTime,
                intent.endTime,
                intent.capacity,
                intent.meetingId
            )

            is AppIntent.onLoginClick -> onLoginClicked(intent.employeeId,intent.employeeName)
            is AppIntent.OnDeskListFilter -> OnDeskListFilterUpdate(intent.listItem)
            is AppIntent.OnMeetingListFilterUpdate -> onMeetingListFilterUpdate(intent.listItem)
            is AppIntent.OnQRCodeScanned -> onQRCodeScanned(intent.seatId)
            is AppIntent.AddBanner -> addBanner(intent.showBanner)
        }
    }


    suspend fun saveEmployeeId(employeeId: String) {
        if (employeeId.isNotEmpty() && employeeId.length > 0) {
            saveEmployeeIdInAppState(employeeId)
            callAllApi(employeeId)
        }
    }

    fun saveEmployeeName(employeeName: String) {
        if (employeeName.isNotEmpty() && employeeName.length > 0) {
           _uiState.update {
               it.copy(employeeName = employeeName)
           }
        }
    }

    private fun saveEmployeeIdInAppState(employeeId: String) {
        _uiState.update {
            it.copy(employeeId = employeeId)
        }
    }

    private fun addBanner(showBanner: Boolean) {
        _uiState.update {
            it.copy(showBanner = showBanner)
        }
    }

    private fun OnDeskListFilterUpdate(listItem: List<DeskResponseItemModel>) {
        _uiState.update { it.copy(currentFilteredList = listItem) }
    }

    private fun onMeetingListFilterUpdate(listItem: List<MeetingItemModel>) {
        _uiState.update { it.copy(currentMeetingRoomFilteredList = listItem) }
    }

    private fun onQRCodeScanned(seatId: String) {
        val seatString = seatId.split("-")
        val floorNo = seatString[0]
        val seatNumber = seatString[1].toInt()
        val todayDAte = Utils.getCurrentDate()
        viewModelScope.launch {
            isLoading.value = true
            deskReserveApiCall(seatNumber, floorNo.toInt(), todayDAte)
            isLoading.value = false
        }
    }

    private suspend fun deskReserveApiCall(seatId: Int, floorNumber: Int, date: String) {
        try {
            val request = InstantBookingRequest(
                uiState.value.employeeId.toInt(),
                date,
                seatId,
                floorNumber,
                AvailabilityType.RESERVED.type
            )
            val response = instantDeskBookUseCase(request)
            if (response.status == AvailabilityType.RESERVED.type) {
                setToastMessage("Your desk has been reserved")
                deskHistoryAPICall(_uiState.value.employeeId)
                updateInstantBookingDeskList(seatId = seatId)
                updateInstantBookingList(seatId)
                _uiState.update {
                    it.copy(
                        bookingId = 0,
                        floorNumber = 0,
                        seatId = 0,
                        showBanner = false
                    )
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "deskReserveApiCall: ${e}")
        }
    }

    private suspend fun getDeskListApiCall() {
        viewModelScope.launch {
            try {
                isLoading.value = true
                val response = getDeskListUseCase(uiState.value.selectedDate)
                if (response != null && response.size > 0) {
                    _uiState.value.deskList = response
                    _uiState.value.currentFilteredList = response
                    updateDeskListUI()
                }
                isLoading.value = false
            } catch (e: Exception) {
                isLoading.value = false
                setToastMessage(e.toString())
                e.printStackTrace()
            }
        }
    }

    // Updating DeskList UI After Calling DeskList UI
    private fun updateDeskListUI() {
        _uiState.update { state ->
            val updatedItems = state.currentFilteredList.mapIndexed { i, item ->
                if (item.reservationStatus == AvailabilityType.RESERVED.type) {
                    item.copy(imageId = R.drawable.reserveddesk)
                } else if (item.reservationStatus == AvailabilityType.BOOKED.type) {
                    item.copy(imageId = R.drawable.deskbooked)
                } else {
                    item.copy(imageId = R.drawable.availabledesk)
                }
            }
            state.copy(currentFilteredList = updatedItems)
        }
        _uiState.update { state ->
            val updatedItems = state.deskList.mapIndexed { i, item ->
                if (item.reservationStatus == AvailabilityType.RESERVED.type) {
                    item.copy(imageId = R.drawable.reserveddesk)
                } else if (item.reservationStatus == AvailabilityType.BOOKED.type) {
                    item.copy(imageId = R.drawable.deskbooked)
                } else {
                    item.copy(imageId = R.drawable.availabledesk)
                }
            }
            state.copy(deskList = updatedItems)
        }
        ReservationViewModelEffects.Composable.deskListUpdated.send()
    }


    // Api Calls for Booking the Desk
    private suspend fun callBookDeskApi() {
        selectedDesk.value.let {
            val response = true
            if (response) {
                viewModelScope.launch {
                    isLoading.value = true
                    val deskObject = selectedDesk.value?.let { it1 ->
                        DeskReservationRequest(
                            employId = _uiState.value.employeeId.toInt(),
                            name = _uiState.value.employeeName,
                            date = _uiState.value.selectedDate,
                            seatNumber = it1.seatNumber,
                            floorNumber = selectedDesk.value!!.floorNumber,
                            reservationStatus = AvailabilityType.BOOKED.type
                        )
                    }

                    val response = deskObject?.let { it1 -> bookDeskUseCase(it1) }
                    if (response!!.status == "Already Reserved") {
                        setToastMessage("You have already booked one desk for this date")
                    } else {
                        setToastMessage("Your desk has been booked for  ${_uiState.value.selectedDate} date")
                        updateCurrentDeskList()
                        updateDeskList()
                        deskHistoryAPICall(_uiState.value.employeeId)
                        checkForBanner()
                    }
                    isLoading.value = false
                }
            }
        }
    }

    // Will Call Meeting Booking Api
    private fun callBookMeetingApi(
        startTime: String,
        endTime: String,
        meetingId: String,
        item: MeetingItemModel
    ) {
        val response = true
        if (response) {
            viewModelScope.launch {
               try{
                   isLoading.value = true
                   val request = BookMeetingRoomRequest(
                       _uiState.value.employeeId.toInt(),
                       _uiState.value.employeeName,
                       meetingId.toInt(),
                       selectedFloor.value,
                       _uiState.value.selectedDate,
                       startTime,
                       endTime,
                   )

                   val response = meetingRoomReservationUseCase(request)

                   if(response.status == "reserved") {
                       setToastMessage("Your meeting room has been booked...")
                       updateCurrentMeetingRoomList(item, startTime, endTime)
                       updateCabinMeetingRoomList(item, startTime, endTime)
                       meetingHistoryAPICall(_uiState.value.employeeId)
                   }
                   isLoading.value = false
               }catch (e : Exception){
                   Log.d(TAG, "callBookMeetingApi: ${e}")
                   isLoading.value = false
               }
            }
        }
    }


    private suspend fun onDeskBookButtonClicked() {
        if (selectedFloor.value == -1) {
            setToastMessage("Please select floor to book")
        } else {
            callBookDeskApi()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onMeetingBooking(
        startTime: String,
        endTime: String,
        capacity: String,
        meetingId: String
    ) {
        if (selectedFloor.value == -1) {
            setToastMessage("Please select floor to book")
        } else {
            val item: MeetingItemModel = getItemByMeetingRoom(meetingId.toInt())!!;
            Log.d(TAG, "onMeetingBooking 1: ${item}")
            if (Utils.isTimeSlotOverlapping(item.reservedSlot, startTime, endTime)) {
                setToastMessage("Please select other slot this slot getting conflict with other slot")
            } else {
                callBookMeetingApi(startTime, endTime, meetingId, item)
            }
        }
    }


    fun updateStartDestination(startDestination: String) {
        _uiState.update { it.copy(startDestination = startDestination) }
    }


    //Call All Api from here
    private suspend fun callAllApi(employeeId: String) {
        deskHistoryAPICall(employeeId)
        meetingHistoryAPICall(employeeId)
        getDeskListApiCall()
        getMeetingRoomList()
        checkForBanner()
    }


    fun convertToStringList(reservedTimeSlots: List<ReservedTimeSlot>): List<String> {
        return reservedTimeSlots.map { "${it.startTime}-${it.endTime}" }
    }

    private fun getAllocatedSlot(
        floorNumber: Int,
        roomNumber: Int,
        bookedRoomInfoList1: List<BookedRoomInfo>
    ): List<String>? {
        val index =
            bookedRoomInfoList1.indexOfFirst { it.roomNumber == roomNumber && it.floorNumber == floorNumber }
        if (index != -1) {
            val updatedItems = bookedRoomInfoList1.toMutableList()
            val selectedItem = updatedItems[index]
            return convertToStringList(selectedItem.reservedTimeSlots)
        }
        return null
    }

    private fun getMeetingRoomList() {
        viewModelScope.launch {
            try {
                val response = getMeetingRoomListUseCase(_uiState.value.selectedDate)
                val newList: MutableList<MeetingItemModel> = mutableListOf()
                if (response != null) {
                    if (response.roomInfo.size > 0) {
                        response.roomInfo.map {
                            val getTimeSlot = getAllocatedSlot(
                                it.floorNumber,
                                it.roomNumber,
                                response.bookedRoomInfoList
                            )
                            Log.d(TAG, "getMeetingRoomList 10: ${getTimeSlot}")
                            if (getTimeSlot != null) {
                                newList.add(
                                    MeetingItemModel(
                                        it.roomNumber, it.floorNumber, it.roomNumber,
                                        getTimeSlot, it.capacity, R.drawable.reservedcabin
                                    )
                                )
                            } else {
                                newList.add(
                                    MeetingItemModel(
                                        it.roomNumber, it.floorNumber, it.roomNumber,
                                        listOf(), it.capacity, R.drawable.availablecabin
                                    )
                                )
                            }
                        }
                    }
                }
                _uiState.update {
                    it.copy(cabinList = newList)
                }
                _uiState.update {
                    it.copy(currentMeetingRoomFilteredList = newList)
                }
                ReservationViewModelEffects.Composable.meetingListUpdated.send()
            } catch (e: Exception) {
                Log.d(TAG, "getMeetingRoomList: ${e}")
            }
        }
    }

    private fun meetingHistoryAPICall(employeeId: String) {
        viewModelScope.launch {
            try {
                val response = meetingHistoryUseCase(employeeId.toInt())
                _uiState.update { it.copy(cabinHistoryList = response) }
            } catch (e: Exception) {
                Log.d(TAG, "meetingHistoryAPICall: ${e}")
            }
        }
    }

    private fun checkForBanner() {
        val todayDAte = Utils.getCurrentDate()
        val index =
            _uiState.value.deskHistoryList.indexOfFirst { it.date == todayDAte && it.reservationStatus == AvailabilityType.BOOKED.type }
        if (index != -1) {
            val updatedItems = uiState.value.deskHistoryList.toMutableList()
            val selectedItem = updatedItems[index]
            _uiState.update {
                it.copy(
                    bookingId = selectedItem.bookingId,
                    floorNumber = selectedItem.floorNumber,
                    seatId = selectedItem.seatNumber,
                )
            }
            addBanner(true)
        }
    }

    private suspend fun deskHistoryAPICall(employeeId: String) {
        try {
            val response = deskHistoryUseCase(employeeId.toInt())
            Log.d(TAG, "deskHistoryAPICall 2: ${response}")
            _uiState.update { it.copy(deskHistoryList = response) }
        } catch (e: Exception) {
            Log.d("Exception", "Desk history api call failed")
        }
    }

    private fun onDeskItemClicked(itm: DeskResponseItemModel, index: Int) {
        selectedDesk.value = itm
        _uiState.update { state ->
            val updatedItems = state.currentFilteredList.mapIndexed { i, item ->
                if (item.seatId == itm.seatId) {
                    item.copy(imageId = R.drawable.selecteddesk) // Change to desired color
                } else {
                    if (item.reservationStatus == AvailabilityType.RESERVED.type) {
                        item.copy(imageId = R.drawable.reserveddesk)
                    } else if (item.reservationStatus == AvailabilityType.BOOKED.type) {
                        item.copy(imageId = R.drawable.deskbooked)
                    } else {
                        item.copy(imageId = R.drawable.availabledesk)
                    }
                }
            }
            state.copy(currentFilteredList = updatedItems)
        }
        ReservationViewModelEffects.Composable.deskListUpdated.send()
    }


    private fun OnMeetingItemClicked(itm: MeetingItemModel, index: Int) {
        _uiState.update { state ->
            val updatedItems = state.currentMeetingRoomFilteredList.mapIndexed { i, item ->
                if (itm.meetingRoomId == item.meetingRoomId) {
                    item.copy(imageId = R.drawable.selectedcabin) // Change to desired color
                } else {
                    if (item.reservedSlot.size == 0) {
                        item.copy(imageId = R.drawable.availablecabin)
                    } else {
                        item.copy(imageId = R.drawable.reservedcabin)
                    }
                }
            }
            state.copy(currentMeetingRoomFilteredList = updatedItems)
        }
        ReservationViewModelEffects.Composable.meetingListUpdated.send()
    }

    suspend private fun onLoginClicked(employeeId: String, employeeName: String) {
        _uiState.update { it.copy(
            employeeId = employeeId,
            employeeName = employeeName)
        }
        callAllApi(employeeId)
    }

    private fun onFloorSelected(floor: Int) {
        selectedFloor.intValue = floor
        ReservationViewModelEffects.Composable.SelectedFloor(filterByFloor(floor)).send()
        ReservationViewModelEffects.Composable.MeetingRoomListByFilter(
            meetingRoomFilterByFloor(
                floor
            )
        ).send()
    }

    private fun onFilterButtonClicked() {
        TODO("Not yet implemented")
    }

    private fun onDatePickerClicked(dateSelected: String) {
        _uiState.update { it.copy(selectedDate = dateSelected) }
        viewModelScope.launch {
            getDeskListApiCall()
            getMeetingRoomList()
        }
    }

    private fun ReservationViewModelEffects.send() {
        viewModelScope.launch { _effects.emit(this@send) }
    }


    fun filterByFloor(floorNumber: Int): List<DeskResponseItemModel> {
        return _uiState.value.deskList.filter { it.floorNumber == floorNumber }
    }

    fun meetingRoomFilterByFloor(floorNumber: Int): List<MeetingItemModel> {
        return _uiState.value.cabinList.filter { it.floor == floorNumber }
    }

    fun updateCurrentDeskList() {
        _uiState.update { state ->
            val updatedItems = state.currentFilteredList.mapIndexed { i, item ->
                if (item.seatId == selectedDesk.value?.seatId) {
                    item.copy(
                        imageId = R.drawable.deskbooked,
                        reservationStatus = AvailabilityType.BOOKED.type
                    )// Change to desired color
                } else {
                    if (item.reservationStatus == AvailabilityType.RESERVED.type) {
                        item.copy(imageId = R.drawable.reserveddesk)
                    } else if (item.reservationStatus == AvailabilityType.BOOKED.type) {
                        item.copy(imageId = R.drawable.deskbooked)
                    } else {
                        item.copy(imageId = R.drawable.availabledesk)
                    }
                }
            }
            state.copy(currentFilteredList = updatedItems)
        }
        ReservationViewModelEffects.Composable.deskListUpdated.send()
    }

    fun updateInstantBookingDeskList(seatId: Int) {
        _uiState.update { state ->
            val index = state.deskList.indexOfFirst { it.seatNumber == seatId }
            if (index != -1) {
                val updatedItems = state.deskList.toMutableList()
                val selectedItem = updatedItems[index]
                updatedItems[index] = selectedItem.copy(
                    imageId = R.drawable.reserveddesk,
                    reservationStatus = AvailabilityType.RESERVED.type
                )
                state.copy(deskList = updatedItems)
            } else {
                state
            }
        }
        ReservationViewModelEffects.Composable.deskListUpdated.send()
    }

    fun updateInstantBookingList(seatId: Int) {
        _uiState.update { state ->
            val index = state.currentFilteredList.indexOfFirst { it.seatNumber == seatId }
            if (index != -1) {
                val updatedItems = state.currentFilteredList.toMutableList()
                val selectedItem = updatedItems[index]
                updatedItems[index] = selectedItem.copy(
                    imageId = R.drawable.reserveddesk,
                    reservationStatus = AvailabilityType.RESERVED.type
                )
                state.copy(currentFilteredList = updatedItems)
            } else {
                state
            }
        }
        ReservationViewModelEffects.Composable.deskListUpdated.send()
    }

    fun updateDeskList() {
        _uiState.update { state ->
            val selectedDeskId = selectedDesk.value?.seatId
            val index = state.deskList.indexOfFirst { it.seatId == selectedDeskId }
            if (index != -1) {
                val updatedItems = state.deskList.toMutableList()
                val selectedItem = updatedItems[index]
                updatedItems[index] = selectedItem.copy(
                    imageId = R.drawable.deskbooked,
                    reservationStatus = AvailabilityType.BOOKED.type
                )
                state.copy(deskList = updatedItems)
            } else {
                state
            }
        }
    }


    fun clearToastMessage() {
        _toastMessage.value = null
    }

    fun setToastMessage(text: String) {
        _toastMessage.value = text
    }

    private fun getItemByMeetingRoom(meetingId: Int): MeetingItemModel? {
        val index = uiState.value.cabinList.indexOfFirst { it.meetingRoomId == meetingId && it.floor == selectedFloor.value }
        if (index != -1) {
            val updatedItems = uiState.value.cabinList.toMutableList()
            val selectedItem = updatedItems[index]
            return selectedItem
        }
        return null;
    }


    fun updateCurrentMeetingRoomList(
        meetingItem: MeetingItemModel,
        startTime: String,
        endTime: String
    ) {
        _uiState.update { state ->
            val index =
                state.currentMeetingRoomFilteredList.indexOfFirst { it.meetingRoomId == meetingItem.meetingRoomId && it.floor == selectedFloor.value}
            if (index != -1) {
                val mutableList = meetingItem.reservedSlot.toMutableList()
                mutableList.add("${startTime}-${endTime}")
                val newList = mutableList.toList()
                val updatedItems = state.currentMeetingRoomFilteredList.toMutableList()
                val selectedItem = updatedItems[index]
                updatedItems[index] = selectedItem.copy(
                    imageId = R.drawable.reservedcabin,
                    reservedSlot = newList
                )
                state.copy(currentMeetingRoomFilteredList = updatedItems)
            } else {
                state
            }
        }
        ReservationViewModelEffects.Composable.meetingListUpdated.send()
    }


    fun updateCabinMeetingRoomList(
        meetingItem: MeetingItemModel,
        startTime: String,
        endTime: String
    ) {
        _uiState.update { state ->
            val index =
                state.cabinList.indexOfFirst { it.meetingRoomId == meetingItem.meetingRoomId }
            if (index != -1) {
                val mutableList = meetingItem.reservedSlot.toMutableList()
                mutableList.add("${startTime}-${endTime}")
                val newList = mutableList.toList()
                val updatedItems = state.cabinList.toMutableList()
                val selectedItem = updatedItems[index]
                updatedItems[index] = selectedItem.copy(
                    imageId = R.drawable.reservedcabin,
                    reservedSlot = newList
                )
                state.copy(cabinList = updatedItems)
            } else {
                state
            }
        }
        ReservationViewModelEffects.Composable.meetingListUpdated.send()
    }

    fun clearAllAppStateWhileLogout() {
        _uiState.value.copy(
            selectedDate = Utils.getCurrentDate(),
            deskList = listOf(),
            cabinList = listOf(),
            currentFilteredList = listOf(),
            currentMeetingRoomFilteredList = listOf(),
            deskHistoryList = listOf(),
            cabinHistoryList = listOf(),
            showBanner = false,
            seatId = 0,
            floorNumber = 0,
            bookingId = 0,
        )
    }
}