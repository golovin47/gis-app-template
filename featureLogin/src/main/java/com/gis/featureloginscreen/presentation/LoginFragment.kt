package com.gis.featureloginscreen.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.gis.featureloginscreen.R
import com.gis.featureloginscreen.databinding.FragmentLoginBinding
import com.gis.featureloginscreen.presentation.LoginIntent.NameChanged
import com.gis.featureloginscreen.presentation.LoginIntent.StartLogin
import com.gis.utils.BaseView
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment(), BaseView<LoginState> {

  private lateinit var viewSubscriptions: Disposable
  private var binding: FragmentLoginBinding? = null

  private val vmLogin: LoginViewModel by viewModel()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    handleStates()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    initBinding(inflater, container)
    initIntents()

    return binding!!.root
  }

  override fun onDestroyView() {
    binding = null
    viewSubscriptions.dispose()
    super.onDestroyView()
  }

  private fun initBinding(inflater: LayoutInflater, container: ViewGroup?) {
    binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
  }

  @SuppressLint("CheckResult")
  override fun initIntents() {
    viewSubscriptions = Observable.merge(
      listOf(
        RxTextView.textChanges(binding!!.etName)
          .debounce(300, TimeUnit.MILLISECONDS)
          .map { NameChanged(it.toString()) },

        RxView.clicks(binding!!.btnLogin)
          .skip(300, TimeUnit.MILLISECONDS)
          .map { StartLogin(binding!!.etName.text.toString()) }
      )
    ).subscribe(vmLogin.viewIntentsConsumer())
  }

  override fun handleStates() {
    vmLogin.stateReceived().observe(this, Observer { state -> render(state) })
  }

  override fun render(state: LoginState) {
    binding!!.loading = state.loading

    if (state.loading) binding!!.loginRoot.transitionToEnd()
    else binding!!.loginRoot.transitionToStart()

    if (state.error != null)
      Snackbar.make(view!!, state.error.message!!, Snackbar.LENGTH_SHORT).show()
  }
}