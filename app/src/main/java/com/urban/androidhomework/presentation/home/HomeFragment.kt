package com.urban.androidhomework.presentation.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.urban.androidhomework.R
import com.urban.androidhomework.api.NetworkClient
import com.urban.androidhomework.databinding.HomeFragmentBinding
import com.urban.androidhomework.domain.Character
import com.urban.androidhomework.presentation.character.CharacterFragment
import com.urban.androidhomework.presentation.utils.CharactersAdapter
import com.urban.androidhomework.presentation.utils.Utils
import com.zhuinden.fragmentviewbindingdelegatekt.viewBinding
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.Response

class HomeFragment : Fragment(R.layout.home_fragment) {
    private val viewModel by viewModels<HomeViewModel>()
    private val binding by viewBinding(HomeFragmentBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkClient.get()
                .service
                .allCharacters
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        object : SingleObserver<Response<Character>> {
                            override fun onSubscribe(d: Disposable) {}
                            override fun onSuccess(characterResponse: Response<Character>) {
                                onSuccessResult(characterResponse)
                            }

                            override fun onError(e: Throwable) {}
                        })
    }

    private fun onSuccessResult(characterResponse: Response<Character>) {
        val adapter = CharactersAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                Utils.getName(characterResponse).toTypedArray()
        )
        binding.charactersList.adapter = adapter
        binding.charactersList.setOnItemClickListener { parent, _, position, _ ->
            val name = parent.getItemAtPosition(position) as String
            findNavController().navigate(HomeFragmentDirections.actionCharacter(name))
        }
    }
}