package com.example.mvvmnew.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.launch

inline fun <reified T : ViewModel> Fragment.assistedViewModels(
    owner: ViewModelStoreOwner = this,
    name: String? = null,
    crossinline body: () -> T
): T {
    val factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            @Suppress("UNCHECKED_CAST")
            return body() as T
        }
    }
    return if (name != null) {
        ViewModelProvider(owner, factory)[name, T::class.java]
    } else {
        ViewModelProvider(owner, factory)[T::class.java]
    }
}

fun Fragment.repeatOnStarted(function: suspend () -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            function()
        }
    }
}