package ru.netology.nmedia.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.auth.AuthState
import ru.netology.nmedia.repository.PostRepository
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: PostRepository,
    private val auth: AppAuth,
) : ViewModel() {
    val data: LiveData<AuthState> = auth.authStateFlow
        .asLiveData(Dispatchers.Default)
    val authenticated: Boolean
        get() = auth.authStateFlow.value.id != 0L

    fun authentication(login: String, password: String) {
        viewModelScope.launch {
            repository.authentication(login, password)
        }
    }
}