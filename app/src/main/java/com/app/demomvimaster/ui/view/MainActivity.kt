package com.app.demomvimaster.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.demomvimaster.R
import com.app.demomvimaster.data.api.ApiHelperImpl
import com.app.demomvimaster.data.api.RetrofitBuilder
import com.app.demomvimaster.data.model.User
import com.app.demomvimaster.databinding.ActivityMainBinding
import com.app.demomvimaster.ui.adapter.UserAdapter
import com.app.demomvimaster.ui.intent.MainIntent
import com.app.demomvimaster.ui.viewmodel.MainViewModel
import com.app.demomvimaster.ui.viewstate.MainState
import com.app.demomvimaster.util.ViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter:UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )
        binding.lifecycleOwner = this

        setupUI()
        setupViewModel()
        observeViewModel()
        setupClicks()
    }

    private fun setupClicks() {
        binding.buttonFetchUser.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.userIntent.send(MainIntent.FetchUser)
            }
        }

    }


    private fun setupUI() {
//        binding.recyclerView.layoutManager = LinearLayoutManager(this)
//        binding.recyclerView.run {
//            addItemDecoration(
//                DividerItemDecoration(
//                    binding.recyclerView.context,
//                    ( binding.recyclerView.layoutManager as LinearLayoutManager).orientation
//                )
//            )
//        }
      //  adapter = UserAdapter()

     //   binding.adapter = adapter
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                ApiHelperImpl(
                    RetrofitBuilder.apiService
                )
            )
        ).get(MainViewModel::class.java)

        binding.viewModel = mainViewModel

        adapter = UserAdapter()

        binding.adapter = adapter
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is MainState.Idle -> {

                    }
                    is MainState.Loading -> {
                        binding.buttonFetchUser.visibility = View.GONE
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is MainState.Users -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.GONE
                        renderList(it.user)
                        Toast.makeText(this@MainActivity, it.user[1].name, Toast.LENGTH_LONG).show()
                    }
                    is MainState.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.buttonFetchUser.visibility = View.VISIBLE
                        Toast.makeText(this@MainActivity, it.error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun renderList(users: List<User>) {
        binding.recyclerView.visibility = View.VISIBLE
        Log.e("MainActivity", users.toString())
        users.let { listOfUsers -> listOfUsers.let { adapter.submitList(it)
            adapter.notifyDataSetChanged()
        } }
    }
}