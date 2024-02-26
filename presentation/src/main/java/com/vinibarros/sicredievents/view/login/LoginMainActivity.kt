package com.vinibarros.sicredievents.view.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vinibarros.sicredievents.R
import com.vinibarros.sicredievents.databinding.ActivityLoginBinding
import com.vinibarros.sicredievents.domain.model.User
import com.vinibarros.sicredievents.graph.scope.ViewModelProviderFactory
import com.vinibarros.sicredievents.util.extensions.isValidEmail
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class LoginMainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    @Inject
    protected lateinit var viewModelProviderFactory: ViewModelProviderFactory<LoginMainViewModel>
    private val viewModel: LoginMainViewModel by viewModels(factoryProducer = ::viewModelProviderFactory)

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.LoginTheme)
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeUi()
        setupUi()
    }

    private fun subscribeUi() {
        viewModel.user.observe(this, ::onUser)
        viewModel.insertedUserId.observe(this, ::onInsertedUserId)
    }

    private fun onInsertedUserId(id: Long?) {
        id?.let {
            startActivity(
                Intent("action.eventList")
                    .setPackage(baseContext.packageName)
                    .addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_NEW_TASK or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK
                    )
            )
        }
    }

    private fun setupUi() {
        with(binding) {
            buttonSave.setOnClickListener {
                if (editTextEmail.text.toString().isValidEmail()) {
                    val user = viewModel.user.value.apply {
                        this?.email = editTextEmail.text.toString()
                        this?.name = editTextName.text.toString()
                    } ?: User(
                        email = editTextEmail.text.toString(),
                        name = editTextName.text.toString()
                    )
                    viewModel.saveUser(user)
                } else {
                    editTextEmail.error = getString(R.string.error_invalid_email)
                }
            }
        }
    }

    private fun onUser(user: User?) {
        user?.let {
            with(binding) {
                editTextName.setText(it.name)
                editTextEmail.setText(it.email)
            }
        }
    }
}