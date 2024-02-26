package com.vinibarros.sicredievents.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.vinibarros.sicredievents.domain.interactors.GetCurrentUser
import com.vinibarros.sicredievents.domain.interactors.InsertUser
import com.vinibarros.sicredievents.domain.model.User
import com.vinibarros.sicredievents.domain.util.provider.SchedulerProvider
import com.vinibarros.sicredievents.util.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginMainViewModel @Inject constructor(
    private val schedulerProvider: SchedulerProvider,
    private val getCurrentUser: GetCurrentUser,
    private val insertUser: InsertUser
) : BaseViewModel() {

    val user: LiveData<User?> get() = _user
    val insertedUserId: LiveData<Long?> get() = _insertedUserId


    private val _user by lazy { MutableLiveData<User?>() }
    private val _insertedUserId by lazy { MutableLiveData<Long?>() }

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        _user.postValue(getCurrentUser.execute())
    }

    fun saveUser(user: User) = viewModelScope.launch {
        _insertedUserId.postValue(insertUser.execute(user))
    }
}