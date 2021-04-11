package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.auth.AuthState
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import javax.inject.Inject

//class AuthViewModel(application: Application) : AndroidViewModel(application) {
//    private val repository: PostRepository = PostRepositoryImpl(
//        AppDb.getInstance(context = application).postDao(),
//        AppDb.getInstance(context = application).postWorkerDao()
//    )
//    val data: LiveData<AuthState> = AppAuth.getInstance()
//        .authStateFlow
//        .asLiveData(Dispatchers.Default)
//    val authenticated: Boolean
//        get() = AppAuth.getInstance().authStateFlow.value.id != 0L
//
//
//    fun authentication(login: String, password: String) {
//        viewModelScope.launch {
//            repository.authentication(login, password)
//        }
//    }
//}

@HiltViewModel
class AuthViewModel @Inject constructor(private val auth: AppAuth) : ViewModel() {
    val data: LiveData<AuthState> = auth.authStateFlow
        .asLiveData(Dispatchers.Default)
    val authenticated: Boolean
        get() = auth.authStateFlow.value.id != 0L
}