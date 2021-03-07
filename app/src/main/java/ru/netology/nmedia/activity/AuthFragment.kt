package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.netology.nmedia.R
import ru.netology.nmedia.api.PostsApiService
import ru.netology.nmedia.databinding.FragmentAuthBinding
import ru.netology.nmedia.databinding.FragmentPhotoBinding
import ru.netology.nmedia.utils.Utils
import ru.netology.nmedia.viewmodel.AuthViewModel
import ru.netology.nmedia.viewmodel.PostViewModel


class AuthFragment : Fragment() {
    private val viewModel: AuthViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val binding = FragmentAuthBinding.inflate(
            inflater,
            container,
            false
        )

        binding.enterBtnView.setOnClickListener {
            val login = binding.loginView.text.trim().toString()
            val password = binding.passwordView.text.trim().toString()
            viewModel.authentication(login, password)
            Utils.hideKeyboard(it)
            findNavController().navigateUp()
        }

        return binding.root
    }
}