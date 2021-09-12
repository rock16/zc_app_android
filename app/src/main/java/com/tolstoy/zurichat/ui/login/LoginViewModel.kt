package com.tolstoy.zurichat.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tolstoy.zurichat.R
import com.tolstoy.zurichat.data.repository.authrepository.LoginRepository
import com.tolstoy.zurichat.models.loginmodel.LoginForm
import com.tolstoy.zurichat.models.loginmodel.LoginState
import com.tolstoy.zurichat.models.loginmodel.UserX
import com.tolstoy.zurichat.util.networkutils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<LoginState>()
    val loginResult: LiveData<LoginState> = _loginResult

    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO){
            val loginForm = LoginForm(email, password)
            val result = loginRepository.login(loginForm)
            withContext(Dispatchers.Main) {
                if (result is State.LoadingState) {
                    _loginResult.value = LoginState(loading = R.string.loading)
                } else if (result is State.DataState) {
                    _loginResult.value =
                        LoginState(success = LoggedInUserView(displayName = result.data.data.user.display_name))
                } else {
                    _loginResult.value = LoginState(error = R.string.login_failed)
                }
            }
        }
    }
}