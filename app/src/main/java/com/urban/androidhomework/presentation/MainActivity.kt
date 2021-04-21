package com.urban.androidhomework.presentation

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.urban.androidhomework.R
import com.urban.androidhomework.api.NetworkClient
import com.urban.androidhomework.domain.Character
import com.urban.androidhomework.presentation.character.CharacterFragment
import com.urban.androidhomework.presentation.utils.CharactersAdapter
import com.urban.androidhomework.presentation.utils.Utils.Companion.getName
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}